package com.springweb.web.service.lesson;

import com.springweb.web.aop.annotation.Trace;
import com.springweb.web.controller.dto.lesson.CreateLessonDto;
import com.springweb.web.controller.dto.lesson.LessonDetailDto;
import com.springweb.web.controller.dto.lesson.SearchLessonDto;
import com.springweb.web.controller.dto.lesson.UpdateLessonDto;
import com.springweb.web.domain.file.UploadFile;
import com.springweb.web.domain.lesson.GroupLesson;
import com.springweb.web.domain.lesson.Lesson;
import com.springweb.web.domain.lesson.PersonalLesson;
import com.springweb.web.domain.member.Member;
import com.springweb.web.domain.member.Student;
import com.springweb.web.domain.member.Teacher;
import com.springweb.web.exception.BaseException;
import com.springweb.web.exception.lesson.LessonException;
import com.springweb.web.exception.lesson.LessonExceptionType;
import com.springweb.web.exception.member.MemberException;
import com.springweb.web.exception.member.MemberExceptionType;
import com.springweb.web.repository.lesson.LessonRepository;
import com.springweb.web.repository.member.MemberRepository;
import com.springweb.web.service.file.FileService;
import com.springweb.web.service.lesson.search.LessonSearchCond;
import com.springweb.web.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService{

    private final LessonRepository lessonRepository;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final FileService fileService;


    @Trace
    private String getMyUsername() throws MemberException {
        String username = SecurityUtil.getCurrentUsername().orElse(null);
        if(username == null){
            log.error("SecurityContextHolder에 있는 username을 가져오던 중 오류 발생");
            throw new MemberException(MemberExceptionType.PLEASE_LOGIN_AGAIN);
        }
        return username;
    }




    //TODO : 개인과외라면 생성 시 MaxStudent가 1로 설정되었는지 확인해야 함, 그룹과외라면 MaxStudent가 최소 2 이상이어야 함!
    @Trace
    @Override
    public void create(CreateLessonDto createLessonDto) throws BaseException, IOException {
        Member me = memberRepository.findByUsername(getMyUsername()).orElse(null);
        //학생은 강의 등록 불가능
        if(me instanceof Student){
            throw new LessonException(LessonExceptionType.NO_AUTHORITY_CREATE_LESSON);
        }

        Lesson lesson = createLessonDto.toEntity();

        List<UploadFile> uploadedFiles = fileService.saveFiles(createLessonDto.getUploadFiles());//파일 서버에 저장

        lesson.changeUploadFiles(uploadedFiles);


        lesson.confirmTeacher((Teacher)me);

        lessonRepository.save(lesson);
    }




    @Trace
    @Override
    @Transactional(readOnly = true)
    public SearchLessonDto search(LessonSearchCond cond, Pageable pageable) {
        return new SearchLessonDto(lessonRepository.search(cond, pageable));
    }



    @Trace
    @Override
    public LessonDetailDto getLessonInfo(Long lessonId) throws BaseException {
        Lesson findLesson = lessonRepository.findWithTeacherById(lessonId).orElse(null);
        if(findLesson == null){
            throw new LessonException(LessonExceptionType.NOT_FOUND_LESSON);
        }

        findLesson.upView();//조회수 1 증가

        return new LessonDetailDto(findLesson);
    }




    //TODO 이거 되는지 확인해야 함, 메소드에 Lesson을 넘겼는데 영속성 컨텍스트에서 계속 관리되려나.,.?
    @Trace
    @Override
    public void update(Long lessonId, UpdateLessonDto updateLessonDto) throws BaseException, IOException {
        Lesson findLesson = lessonRepository.findWithTeacherById(lessonId).orElse(null);
        //== 수정할 강의가 없는경우 -> 먼가 문제;;==//
        if(findLesson == null){
            throw new LessonException(LessonExceptionType.NOT_FOUND_LESSON);
        }

        //== 내 username과 강의 작성자의 username이 다르다면 예외==//
        if(!findLesson.getTeacher().getUsername().equals(getMyUsername())){
            throw new LessonException(LessonExceptionType.NO_AUTHORITY_UPDATE_LESSON);
        }
        //== 그룹과외 => 개인과외 불가능 ==//
        if(findLesson instanceof GroupLesson groupLesson){
            if(updateLessonDto.getLessonType() != LessonType.GROUP){
                throw new LessonException(LessonExceptionType.CAN_NOT_CHANGE_LESSON_TYPE);
            }
        }
        //== 개인과외 => 그룹과외 불가능 ==//
        if(findLesson instanceof PersonalLesson personalLesson){
            if(updateLessonDto.getLessonType() != LessonType.PERSONAL){
                throw new LessonException(LessonExceptionType.CAN_NOT_CHANGE_LESSON_TYPE);
            }
        }


        changeTitle(updateLessonDto, findLesson);
        changeContent(updateLessonDto, findLesson);
        changeUploadFile(updateLessonDto, findLesson);

        if(updateLessonDto.getLessonType() == LessonType.GROUP){//그룹 과외일경우 추가
            GroupLesson groupLesson = (GroupLesson) findLesson;
            changeMaxStudentCount(updateLessonDto, groupLesson);//TODO: 현재 모집된 학생 수보다 적게 설정한다면 오류 발생
            changePeriod(updateLessonDto, groupLesson); //TODO : 현재보다 과거로 모집기간을 정한다면 오류 발생
        }

    }


    /**
     * 수강중인 학생이 한명이라도 있으면 예외
     */
    @Trace
    @Override
    public void delete(Long lessonId, String password) throws BaseException {
        Lesson findLesson = lessonRepository.findWithTeacherById(lessonId).orElse(null);

        //== 삭제 가능여부 체크 로직 ==//
        if(findLesson == null){
            throw new LessonException(LessonExceptionType.NOT_FOUND_LESSON);
        }//== 삭제할 강의가 없는경우 -> 먼가 문제;;==//

        if(!findLesson.getTeacher().getUsername().equals(getMyUsername())){
            throw new LessonException(LessonExceptionType.NO_AUTHORITY_DELETE_LESSON);
        }//내 username과 강의 작성자의 username이 다르다면 예외

        if(!passwordEncoder.matches(password,findLesson.getTeacher().getPassword())){
            throw new LessonException(MemberExceptionType.PASSWORDS_DOES_NOT_MATCH);
        }//패스워드를 잘못 입력한경우 예외

        if(findLesson.isCompleted()){
            throw new LessonException(LessonExceptionType.CAN_NOT_DELETE_LESSON_COMPLETED);
        }//모집완료된 강의는 삭제할 수 없음, 예외

        if(findLesson instanceof GroupLesson groupLesson){
            if(groupLesson.getNowStudentCount() > 0 ){
                throw new LessonException(LessonExceptionType.CAN_NOT_DELETE_LESSON_EXIST_STUDENT);
            }
        }//그룹과외인 경우 =>모집한 학생이 있는 경우 삭제 불가능
        //== 삭제 가능여부 체크 로직 종료 ==//

        log.info("업로드한 파일을 삭제합니다");
        fileService.deleteFiles(findLesson.getUploadFiles());//컴퓨터에서 삭제 후
        log.info("업로드한 파일을 삭제했습니다");

        lessonRepository.delete(findLesson);
    }





















    //== 내부적으로 간편하게 사용하는 메소드 ==//

    private void changeUploadFile(UpdateLessonDto updateLessonDto, Lesson findLesson) throws IOException, BaseException {
        if(updateLessonDto.getUploadFiles() != null || updateLessonDto.getUploadFiles().size()!=0 ||  !updateLessonDto.getUploadFiles().get(0).isEmpty()){
            System.out.println(findLesson.getUploadFiles().size());
            System.out.println(findLesson.getUploadFiles().size());
            fileService.deleteFiles(findLesson.getUploadFiles());//컴퓨터에서 삭제 후
            log.info("업로드한 파일을 삭제했습니다");
            List<UploadFile> uploadedFiles = fileService.saveFiles(updateLessonDto.getUploadFiles());//새로 업데이트할 파일 서버에 저장
            findLesson.changeUploadFiles(uploadedFiles);
        }
    }
    private void changeContent(UpdateLessonDto updateLessonDto, Lesson findLesson) {
        if(StringUtils.hasLength(updateLessonDto.getContent())){
            findLesson.changeContent(updateLessonDto.getContent());
        }
    }
    private void changeTitle(UpdateLessonDto updateLessonDto, Lesson findLesson) {
        if(StringUtils.hasLength(updateLessonDto.getTitle())){
            findLesson.changeTitle(updateLessonDto.getTitle());
        }
    }

    private void changePeriod(UpdateLessonDto updateLessonDto, GroupLesson groupLesson) {
        if(updateLessonDto.getStartPeriod() != null || updateLessonDto.getEndPeriod() != null){//TODO : 현재 시간보다 과거인지 검증로직 추가해야 함 + 값이 없을떄 null이 들어가는지 확인해야 함

            groupLesson.changePeriod(
                    updateLessonDto.getStartPeriodToLocalDateTime(),
                    updateLessonDto.getEndPeriodToLocalDateTime()
            );
        }
    }
    private void changeMaxStudentCount(UpdateLessonDto updateLessonDto, GroupLesson groupLesson) {
        if(updateLessonDto.getMaxStudentCount() != 0){
            groupLesson.changeMaxStudentCount(updateLessonDto.getMaxStudentCount());
        }
    }
}
