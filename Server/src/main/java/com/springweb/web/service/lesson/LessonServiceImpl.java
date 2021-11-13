package com.springweb.web.service.lesson;

import com.springweb.web.aop.annotation.Trace;
import com.springweb.web.dto.lesson.CreateLessonDto;
import com.springweb.web.dto.lesson.LessonDetailDto;
import com.springweb.web.dto.lesson.SearchLessonDto;
import com.springweb.web.dto.lesson.UpdateLessonDto;
import com.springweb.web.domain.alarm.AlarmType;
import com.springweb.web.domain.file.UploadFile;
import com.springweb.web.domain.lesson.AppliedLesson;
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
import com.springweb.web.repository.lesson.AppliedLessonRepository;
import com.springweb.web.repository.lesson.LessonRepository;
import com.springweb.web.repository.member.MemberRepository;
import com.springweb.web.service.alarm.AlarmService;
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
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
//@Slf4j
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService{

    private final LessonRepository lessonRepository;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final FileService fileService;
    private final AppliedLessonRepository appliedLessonRepository;
    private final AlarmService alarmService;


    //@Trace
    private String getMyUsername() throws MemberException {
        String username = SecurityUtil.getCurrentUsername().orElse(null);
        if(username == null){
            throw new MemberException(MemberExceptionType.PLEASE_LOGIN_AGAIN);
        }
        return username;
    }




    //TODO : 개인과외라면 생성 시 MaxStudent가 1로 설정되었는지 확인해야 함, 그룹과외라면 MaxStudent가 최소 2 이상이어야 함!
    //@Trace
    @Override
    public void create(CreateLessonDto createLessonDto) throws BaseException, IOException {
        Member me = memberRepository.findByUsername(getMyUsername()).orElse(null);
        if(me == null){
            throw new MemberException(MemberExceptionType.NOT_FOUND_MEMBER);
        }

        //학생은 강의 등록 불가능
        if(me instanceof Student) throw new LessonException(LessonExceptionType.NO_AUTHORITY_CREATE_LESSON);
        Teacher teacher =(Teacher) me;

        //블랙리스트이면 불가능
        if(teacher.isBlack()) throw new LessonException(LessonExceptionType.NO_AUTHORITY_CREATE_LESSON);//블랙리스트인경우

        //기간이 과거면 안됨, 시작기간이 끝기간보다 늦으면 안됨, 그룹과외의 경우 학생수가 2보다 작은면 안됨
        if(createLessonDto.getLessonType() == LessonType.GROUP) {
            if (!createLessonDto.isPeriodOk()) throw new LessonException(LessonExceptionType.PERIOD_ERROR);
            if (!createLessonDto.isMaxStudentCountOk()) throw new LessonException(LessonExceptionType.MAX_STUDENT_MUST_LARGER_THAN_TWO);
        }


        Lesson lesson = createLessonDto.toEntity();

        List<UploadFile> uploadedFiles = new ArrayList<>();
        if(createLessonDto.getUploadFiles() != null) {
             uploadedFiles = fileService.saveFiles(createLessonDto.getUploadFiles());//파일 서버에 저장
        }
        lesson.changeUploadFiles(uploadedFiles);


        lesson.confirmTeacher(teacher);

        lessonRepository.save(lesson);
    }



    //@Trace
    @Override
    @Transactional(readOnly = true)
    public SearchLessonDto search(LessonSearchCond cond, Pageable pageable) {
        return new SearchLessonDto(lessonRepository.search(cond, pageable));
    }



    //@Trace
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
    //@Trace
    @Override
    public void update(Long lessonId, UpdateLessonDto updateLessonDto) throws BaseException, IOException {
        Lesson findLesson = lessonRepository.findWithTeacherById(lessonId).orElse(null);
        //== 수정할 강의가 없는경우 -> 먼가 문제;;==//
        if(findLesson == null) throw new LessonException(LessonExceptionType.NOT_FOUND_LESSON);

        Member me = memberRepository.findByUsername(getMyUsername()).orElse(null);

        //학생은 강의 수정 불가능
        if(me instanceof Student) throw new LessonException(LessonExceptionType.NO_AUTHORITY_UPDATE_LESSON);

        //블랙리스트인경우 수정 불가능
        Teacher teacher =(Teacher) me;
        if(teacher.isBlack()) throw new LessonException(LessonExceptionType.NO_AUTHORITY_UPDATE_LESSON);

        //== 내 username과 강의 작성자의 username이 다르다면 예외==//
        if(!findLesson.getTeacher().getUsername().equals(getMyUsername())) throw new LessonException(LessonExceptionType.NO_AUTHORITY_UPDATE_LESSON);

        //== 그룹과외 => 개인과외 불가능 ==//
        if(findLesson instanceof GroupLesson groupLesson){
            if(updateLessonDto.getLessonType() != LessonType.GROUP) throw new LessonException(LessonExceptionType.CAN_NOT_CHANGE_LESSON_TYPE);
        }

        //== 개인과외 => 그룹과외 불가능 ==//
        if(findLesson instanceof PersonalLesson personalLesson){
            if(updateLessonDto.getLessonType() != LessonType.PERSONAL) throw new LessonException(LessonExceptionType.CAN_NOT_CHANGE_LESSON_TYPE);
        }

        if(updateLessonDto.getLessonType() == LessonType.GROUP) {
            //기간이 과거면 안됨, 시작기간이 끝기간보다 늦으면 안됨, 그룹과외의 경우 학생수가 2보다 작은면 안됨
            if (!updateLessonDto.isPeriodOk()) throw new LessonException(LessonExceptionType.PERIOD_ERROR);
            if (!updateLessonDto.isMaxStudentCountOk())
                throw new LessonException(LessonExceptionType.MAX_STUDENT_MUST_LARGER_THAN_TWO);
        }


        changeTitle(updateLessonDto, findLesson);

        changeContent(updateLessonDto, findLesson);

        changeUploadFile(updateLessonDto, findLesson);


        if(updateLessonDto.getLessonType() == LessonType.GROUP){//그룹 과외일경우 추가
            GroupLesson groupLesson = (GroupLesson) findLesson;
            changeMaxStudentCount(updateLessonDto, groupLesson);
            changePeriod(updateLessonDto, groupLesson);
        }

    }




    /**
     * 수강중인 학생이 한명이라도 있으면 예외
     *
     * AppliedLessonList를 삭제해 주어야 한다! + 신청한 학생들에게는 거절햇다는 알람 전송
     */
    //@Trace
    @Override
    public void delete(Long lessonId, String password) throws BaseException {
        Lesson findLesson = lessonRepository.findWithTeacherById(lessonId).orElse(null);

        //== 삭제 가능여부 체크 로직 ==//

        if(findLesson == null) throw new LessonException(LessonExceptionType.NOT_FOUND_LESSON);
        //삭제할 강의가 없는경우 -> 먼가 문제;;==//

        if(!findLesson.getTeacher().getUsername().equals(getMyUsername())) throw new LessonException(LessonExceptionType.NO_AUTHORITY_DELETE_LESSON);
        //내 username과 강의 작성자의 username이 다르다면 예외

        if(!passwordEncoder.matches(password,findLesson.getTeacher().getPassword()))throw new LessonException(MemberExceptionType.PASSWORDS_DOES_NOT_MATCH);
        //패스워드를 잘못 입력한경우 예외

        if(findLesson.isCompleted()) throw new LessonException(LessonExceptionType.CAN_NOT_DELETE_LESSON_COMPLETED);
        //모집완료된 강의는 삭제할 수 없음, 예외

        if(findLesson instanceof GroupLesson groupLesson){
            if(groupLesson.getNowStudentCount() > 0 ) throw new LessonException(LessonExceptionType.CAN_NOT_DELETE_LESSON_EXIST_STUDENT);
        }//그룹과외인 경우 =>모집한 학생이 있는 경우 삭제 불가능, 학생을 삭제할 때 nowStudentCount를 -1 해줌으로 해당 로직이 가능함]

        //== 삭제 가능여부 체크 로직 종료 ==//





        fileService.deleteFiles(findLesson.getUploadFiles());//컴퓨터에서 삭제 후




        //모집한 학생은 없으나, 신청한 학생들이 있는 경우 ,가입이 거절되었다는 메세지가 전달됨.
        //==AppliedLessonList를 삭제==//
        List<AppliedLesson> appliedLessonList = appliedLessonRepository.findAllWithStudentByLessonId(findLesson.getId());

        for (AppliedLesson appliedLesson : appliedLessonList) {
            Student student = appliedLesson.getStudent();
            alarmService.sendAlarm(AlarmType.REFUSED, student, findLesson);//신청한 학생에게 거절되었다고 알림
            appliedLessonRepository.delete(appliedLesson);//DB에서 지운다!
        }


        lessonRepository.delete(findLesson);


    }





















    //== 내부적으로 간편하게 사용하는 메소드 ==//


    //@Trace
    private void changeUploadFile(UpdateLessonDto updateLessonDto, Lesson findLesson) throws IOException, BaseException {

        if(updateLessonDto.getUploadFiles() ==null){
            if(updateLessonDto.isFileRemove()){
                fileService.deleteFiles(findLesson.getUploadFiles());//컴퓨터에서 삭제 후
            }
        }
        else if(updateLessonDto.getUploadFiles() != null || updateLessonDto.getUploadFiles().size()!=0 ||  !updateLessonDto.getUploadFiles().get(0).isEmpty()){
            fileService.deleteFiles(findLesson.getUploadFiles());//컴퓨터에서 삭제 후
            List<UploadFile> uploadedFiles = fileService.saveFiles(updateLessonDto.getUploadFiles());//새로 업데이트할 파일 서버에 저장
            findLesson.changeUploadFiles(uploadedFiles);
        }
        else{//파일을 안 보냈을 때, 파일을 지우려 한다면 다 지워라!
            if(updateLessonDto.isFileRemove()){
                fileService.deleteFiles(findLesson.getUploadFiles());//컴퓨터에서 삭제 후
            }
        }
    }
    //@Trace
    private void changeContent(UpdateLessonDto updateLessonDto, Lesson findLesson) {
        if(StringUtils.hasLength(updateLessonDto.getContent())){
            findLesson.changeContent(updateLessonDto.getContent());
        }
    }
    //@Trace
    private void changeTitle(UpdateLessonDto updateLessonDto, Lesson findLesson) {
        if(StringUtils.hasLength(updateLessonDto.getTitle())){
            findLesson.changeTitle(updateLessonDto.getTitle());
        }
    }
    //@Trace
    private void changePeriod(UpdateLessonDto updateLessonDto, GroupLesson groupLesson) throws LessonException {
        if(updateLessonDto.getStartPeriod() != null || updateLessonDto.getEndPeriod() != null){
            groupLesson.changePeriod(
                    updateLessonDto.getStartPeriodToLocalDateTime(),
                    updateLessonDto.getEndPeriodToLocalDateTime()
            );
        }
    }

    //@Trace
    private void changeMaxStudentCount(UpdateLessonDto updateLessonDto, GroupLesson groupLesson) {
        if(updateLessonDto.getMaxStudentCount() != 0){
            groupLesson.changeMaxStudentCount(updateLessonDto.getMaxStudentCount());
        }
    }
}
