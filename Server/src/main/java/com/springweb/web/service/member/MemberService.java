package com.springweb.web.service.member;

import com.springweb.web.aop.annotation.Trace;
import com.springweb.web.dto.admin.SignUpAdminDto;
import com.springweb.web.dto.member.*;
import com.springweb.web.domain.alarm.AlarmType;
import com.springweb.web.domain.evaluation.Evaluation;
import com.springweb.web.domain.lesson.AppliedLesson;
import com.springweb.web.domain.lesson.GroupLesson;
import com.springweb.web.domain.lesson.Lesson;
import com.springweb.web.domain.lesson.TakingLesson;
import com.springweb.web.domain.member.Member;
import com.springweb.web.domain.member.Student;
import com.springweb.web.domain.member.Teacher;
import com.springweb.web.dto.signup.BasicSignUpStudentDto;
import com.springweb.web.dto.signup.BasicSignUpTeacherDto;
import com.springweb.web.dto.signup.KakaoSignUpStudentDto;
import com.springweb.web.dto.signup.KakaoSignUpTeacherDto;
import com.springweb.web.exception.BaseException;
import com.springweb.web.exception.file.UploadFileException;
import com.springweb.web.exception.member.MemberException;
import com.springweb.web.exception.member.MemberExceptionType;
import com.springweb.web.repository.evaluation.EvaluationRepository;
import com.springweb.web.repository.lesson.AppliedLessonRepository;
import com.springweb.web.repository.lesson.LessonRepository;
import com.springweb.web.repository.lesson.TakingLessonRepository;
import com.springweb.web.repository.member.MemberRepository;
import com.springweb.web.service.alarm.AlarmService;
import com.springweb.web.service.file.FileService;
import com.springweb.web.service.member.search.TeacherSearchCond;
import com.springweb.web.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final LessonRepository lessonRepository;
    private final PasswordEncoder passwordEncoder;
    private final FileService fileService;
    private final AppliedLessonRepository appliedLessonRepository;
    private final AlarmService alarmService;
    private final TakingLessonRepository takingLessonRepository;


    //@Trace
    public void save(BasicSignUpStudentDto basicSignUpStudentDto) throws MemberException, UploadFileException, IOException {

        Member member = basicSignUpStudentDto.toEntity();
        //== 중복 아이디 체크 로직 ==//
        checkDuplicateMember(member.getUsername());

        //패스워드 인코딩 해야 함, 중요!!
        member.passwordEncode(passwordEncoder);

        if(basicSignUpStudentDto.getProfileImg() !=null) {
            if(!basicSignUpStudentDto.getProfileImg().isEmpty()) {
                String uploadedFilePath = fileService.saveFile(basicSignUpStudentDto.getProfileImg().get(0));//파일 서버에 저장
                member.changeProfileImgPath(uploadedFilePath);
            }
        }
        //== 중복 아이디 체크 로직 종료 ==//
        memberRepository.save(member);
    }
    public void save(KakaoSignUpStudentDto kakaoSignUpStudentDto) throws MemberException, UploadFileException, IOException {

        Member member = kakaoSignUpStudentDto.toEntity();
        //== 중복 아이디 체크 로직 ==//
        checkDuplicateMember(member.getUsername());

        //패스워드 인코딩 해야 함, 중요!!, 카카오톡은 안 해!!!!!!!!!!!
        //member.passwordEncode(passwordEncoder);

        if(kakaoSignUpStudentDto.getProfileImg() !=null) {
            if(!kakaoSignUpStudentDto.getProfileImg().isEmpty()) {
                String uploadedFilePath = fileService.saveFile(kakaoSignUpStudentDto.getProfileImg().get(0));//파일 서버에 저장
                member.changeProfileImgPath(uploadedFilePath);
            }
        }
        //== 중복 아이디 체크 로직 종료 ==//
        memberRepository.save(member);
    }
    public void save(KakaoSignUpTeacherDto kakaoSignUpTeacherDto) throws MemberException, UploadFileException, IOException {

        Member member = kakaoSignUpTeacherDto.toEntity();
        //== 중복 아이디 체크 로직 ==//
        checkDuplicateMember(member.getUsername());

        //패스워드 인코딩 해야 함, 중요!!, 카카오톡은 안 해!!!!!!!!!!!
        //member.passwordEncode(passwordEncoder);

        if(kakaoSignUpTeacherDto.getProfileImg() !=null) {
            if(!kakaoSignUpTeacherDto.getProfileImg().isEmpty()) {
                String uploadedFilePath = fileService.saveFile(kakaoSignUpTeacherDto.getProfileImg().get(0));//파일 서버에 저장
                member.changeProfileImgPath(uploadedFilePath);
            }
        }
        //== 중복 아이디 체크 로직 종료 ==//
        memberRepository.save(member);
    }
    public void save(BasicSignUpTeacherDto basicSignUpTeacherDto) throws MemberException, UploadFileException, IOException {

        Member member = basicSignUpTeacherDto.toEntity();
        //== 중복 아이디 체크 로직 ==//
        checkDuplicateMember(member.getUsername());

        //패스워드 인코딩 해야 함, 중요!!
        member.passwordEncode(passwordEncoder);

        if(basicSignUpTeacherDto.getProfileImg() !=null) {
            if(!basicSignUpTeacherDto.getProfileImg().isEmpty()) {
                String uploadedFilePath = fileService.saveFile(basicSignUpTeacherDto.getProfileImg().get(0));//파일 서버에 저장
                member.changeProfileImgPath(uploadedFilePath);
            }
        }
        //== 중복 아이디 체크 로직 종료 ==//
        memberRepository.save(member);
    }



    public void save(SignUpAdminDto signUpAdminDto) throws MemberException, UploadFileException, IOException {

        Member member = signUpAdminDto.toEntity();
        //== 중복 아이디 체크 로직 ==//
        checkDuplicateMember(member.getUsername());

        //패스워드 인코딩 해야 함, 중요!!
        member.passwordEncode(passwordEncoder);

        //== 중복 아이디 체크 로직 종료 ==//
        memberRepository.save(member);
    }









    //@Trace
    private void checkDuplicateMember(String username) throws MemberException {
        if (memberRepository.findByUsername(username).orElse(null) != null) {
            throw new MemberException(MemberExceptionType.ALREADY_EXIST_USERNAME);
        }
    }


    //@Trace
    @Transactional(readOnly = true)
    public Object getMyInfo() throws MemberException {
        Member member = memberRepository.findByUsername(getMyUsername()).orElse(null);

        if(member instanceof Student student){
            return new MyInfoStudentDto(student);
        }
        else if(member instanceof  Teacher teacher){
            return new MyInfoTeacherDto(teacher);
        }

        Student findMember = (Student)memberRepository.findByUsername(getMyUsername()).orElse(null);
        if(findMember == null){
            throw new MemberException(MemberExceptionType.NOT_FOUND_MEMBER);
        }
        return new MyInfoStudentDto(findMember);
    }




    //@Trace
    public void update(UpdateStudentDto updateStudentDto) throws BaseException, IOException  {

        Student student = (Student)memberRepository.findByUsername(getMyUsername()).orElse(null);

        changeName(updateStudentDto, student);
        if (!student.isKakaoMember()) {//카카오 멤버가 아닐때만 진행
            if (updateStudentDto.getOldPassword() != null) {

                if (!updateStudentDto.getOldPassword().equals(student.getPassword())) {
                    changePassword(updateStudentDto, student);
                }
            }
        }
        changeAge(updateStudentDto, student);

        changeProfileImg(updateStudentDto, student);

    }


    //@Trace
    public void update(UpdateTeacherDto updateTeacherDto) throws BaseException, IOException {

        Teacher teacher = (Teacher)memberRepository.findByUsername(getMyUsername()).orElse(null);

        changeName(updateTeacherDto, teacher);
        if (!teacher.isKakaoMember()) {//카카오 멤버가 아닐때만 진행
            if(!updateTeacherDto.getOldPassword().equals(teacher.getPassword())) {
                changePassword(updateTeacherDto, teacher);
            }
        }
        changeAge(updateTeacherDto, teacher);

        changeProfileImg(updateTeacherDto, teacher);
        changeCareer(updateTeacherDto, teacher);

    }


    /**
     * 선생님 회원의 경우
     * 선생님이 하는 수업 중, 그룹레슨이 있고, 모집완료되지 않았다면 회원탈퇴를 막자.
     * 선생님 회원의 경우 등록한 강의를 연관관계로 가지고 있는데, 이때 등록한 강의의 첨부파일을 서버에서 지워주어야 한다.
     * 또한 선생님 회원을 지울 경우, 등록된 강의도 같이 지워지는데, 이때 이 강의의 연관관계의 주인인 AppliedLesson에 대해서 처리를 해주어야 한다.
     * AppliedLesson을 찾아와서 삭제해주도록 하자 + 거절되었다는 메세지도 날라가도록 하자.
     *
     * 학생회원의 경우
     * 학생회원의 경우 TakingLessonList중 groupLesson가 있으면 탈퇴할 때 nowStudent를 -1 해주자, 모집완료된 경우라도 그냥 탈퇴시킨다!(상태는 바꾸지 않는다)
     *
     * 어차피 강의정보를 눌렀을때 회원정보는 안나오므로, 그냥 다 지워버리면 된다!
     *
     */
    //@Trace
    public void delete(String password) throws BaseException {
        Member findMember = memberRepository.findByUsername(getMyUsername()).orElse(null);
        checkPasswordEq(password, findMember);

        //== 여기부터 카카오와 동일 ==//
        /**
         * 선생님 회원의 경우
         * 선생님이 하는 수업 중, 그룹레슨이 있고, 모집완료되지 않았다면 회원탈퇴를 막자.
         * 선생님 회원의 경우 등록한 강의를 연관관계로 가지고 있는데, 이때 등록한 강의의 첨부파일을 서버에서 지워주어야 한다.
         * 또한 선생님 회원을 지울 경우, 등록된 강의도 같이 지워지는데, 이때 이 강의의 연관관계의 주인인 AppliedLesson에 대해서 처리를 해주어야 한다.
         * AppliedLesson을 찾아와서 삭제해주도록 하자 + 거절되었다는 메세지도 날라가도록 하자.
         *
         * 학생회원의 경우
         * 학생회원의 경우 TakingLessonList중 groupLesson가 있으면 탈퇴할 때 nowStudent를 -1 해주자, 모집완료된 경우라도 그냥 탈퇴시킨다!(상태는 바꾸지 않는다)
         *  또한 학생이 한 평가들에 대해 연관관계를 끊어주어야 한다.
         *
         *
         * 어차피 강의정보를 눌렀을때 회원정보는 안나오므로, 그냥 다 지워버리면 된다!
         *
         */
        if(findMember instanceof Teacher teacher){//선생님 탈퇴 시
            for (Lesson lesson : teacher.getLessonList()) {   //선생님이 등록한 모든 강의들에 대해
                if(lesson instanceof GroupLesson groupLesson){//그룹레슨이 있고,
                    // 그룹레슨을 신청한 학생이 있으며,&& 그것이 모집완료되지 않았다면 삭제할 수 없음
                    if(!groupLesson.getTakingLessonList().isEmpty() && !groupLesson.isCompleted() ){
                        throw new MemberException(MemberExceptionType.CANNOT_LEAVE_BSC_GROUP_LESSON_HAS_STUDENT);
                    }
                }
                fileService.deleteFiles(lesson.getUploadFiles());//선생님이 등록한 강의의 첨부파일 모두 삭제
                List<AppliedLesson> appliedLessonList = appliedLessonRepository.findAllWithStudentByLessonId(lesson.getId());//강의를 신청한 모든 학생을 가져와서
                for (AppliedLesson appliedLesson : appliedLessonList) {
                    alarmService.sendAlarm(AlarmType.REFUSED, appliedLesson.getStudent(), lesson);//신청한 학생에게 거절되었다고 알림
                    appliedLessonRepository.delete(appliedLesson);//가입신청한 강의 모두 삭제
                }
                lessonRepository.delete(lesson);//영속성 전이가 있지만 그래도 확실하게 지워주자.
            }
        }
        if(findMember instanceof Student student){
            List<TakingLesson> takingLessonList = takingLessonRepository.findAllWithLessonByStudentId(student.getId());//Lesson을 페치조인으로 가져와서
            for (TakingLesson takingLesson : takingLessonList) {
                if(takingLesson.getLesson() instanceof  GroupLesson groupLesson){//가입된 수업이 그룹과외라면
                    groupLesson.removeTakingLesson(takingLesson);//영속성 전이로 삭제
                }
            }
            student.getEvaluationList().forEach(Evaluation::deleteStudent);//회원이 작성한 평가의 회원부분 연관관계를 끊어줌.

        }

        fileService.deleteFile(findMember.getProfileImgPath());//원래 저장한 프사 삭제
        memberRepository.delete(findMember);
    }

    //@Trace
    public void kakaoDelete(Long kakaoId) throws BaseException {
        Member findMember = memberRepository.findByKakaoId(kakaoId).orElse(null);
        //== 여기부터 일반회원과 동일==//


        //== 여기부터 일반회원탈퇴와 동일 ==//
        /**
         * TODO
         * 선생님 회원의 경우
         * 선생님이 하는 수업 중, 그룹레슨이 있고, 모집완료되지 않았다면 회원탈퇴를 막자.
         * 선생님 회원의 경우 등록한 강의를 연관관계로 가지고 있는데, 이때 등록한 강의의 첨부파일을 서버에서 지워주어야 한다.
         * 또한 선생님 회원을 지울 경우, 등록된 강의도 같이 지워지는데, 이때 이 강의의 연관관계의 주인인 AppliedLesson에 대해서 처리를 해주어야 한다.
         * AppliedLesson을 찾아와서 삭제해주도록 하자 + 거절되었다는 메세지도 날라가도록 하자.
         *
         * 학생회원의 경우
         * 학생회원의 경우 TakingLessonList중 groupLesson가 있으면 탈퇴할 때 nowStudent를 -1 해주자, 모집완료된 경우라도 그냥 탈퇴시킨다!(상태는 바꾸지 않는다)
         *
         * 어차피 강의정보를 눌렀을때 회원정보는 안나오므로, 그냥 다 지워버리면 된다!
         *
         */
        if(findMember instanceof Teacher teacher){//선생님 탈퇴 시
            for (Lesson lesson : teacher.getLessonList()) {   //선생님이 등록한 모든 강의들에 대해

                if(lesson instanceof GroupLesson groupLesson){//그룹레슨이 있고,
                    // 그룹레슨을 신청한 학생이 있으며,&& 그것이 모집완료되지 않았다면 예외 발생
                    if(!groupLesson.getTakingLessonList().isEmpty() && !groupLesson.isCompleted() ) throw new MemberException(MemberExceptionType.CANNOT_LEAVE_BSC_GROUP_LESSON_HAS_STUDENT);
                }

                fileService.deleteFiles(lesson.getUploadFiles());//선생님이 등록한 강의의 첨부파일 모두 삭제

                List<AppliedLesson> appliedLessonList = appliedLessonRepository.findAllWithStudentByLessonId(lesson.getId());//강의를 신청한 모든 학생을 가져와서
                for (AppliedLesson appliedLesson : appliedLessonList) {
                    alarmService.sendAlarm(AlarmType.REFUSED, appliedLesson.getStudent(), lesson);//신청한 학생에게 거절되었다고 알림
                    appliedLessonRepository.delete(appliedLesson);//가입신청한 강의 모두 삭제
                }
                lessonRepository.delete(lesson);//영속성 전이가 있지만 그래도 확실하게 지워주자.
            }
        }


        if(findMember instanceof Student student){//학생 탈퇴 시
            List<TakingLesson> takingLessonList = takingLessonRepository.findAllWithLessonByStudentId(student.getId());//학생이 가입한 수업과, Lesson을 페치조인으로 가져와서
            for (TakingLesson takingLesson : takingLessonList) {//가입한 과외들에 되에
                if(takingLesson.getLesson() instanceof  GroupLesson groupLesson){//가입된 수업이 그룹과외라면(개인과외라면, 이미 모집완료된 상태이므로 그냥 지워주지 않는다, 어차피 강의중에서 학생정보는 안나오므로 신경 안써도 돼
                    groupLesson.removeTakingLesson(takingLesson);//영속성 전이로 삭제
                }
            }
        }

        fileService.deleteFile(findMember.getProfileImgPath());//원래 저장한 프사 삭제
        memberRepository.delete(findMember);
    }




    //@Trace
    @Transactional(readOnly = true)
    public SearchTeacherDto searchTeacher(TeacherSearchCond cond, Pageable pageable) throws BaseException{

        Page<Teacher> search = memberRepository.search(cond, pageable);

        return new SearchTeacherDto(search);

    }
























    private void checkPasswordEq(String password, Member findMember) throws MemberException {

        if (!passwordEncoder.matches(password,findMember.getPassword())) {//equals 하면 안된다!

            throw new MemberException(MemberExceptionType.PASSWORDS_DOES_NOT_MATCH);
        }
    }




    //@Trace
    private String getMyUsername() throws MemberException {
        String username = SecurityUtil.getCurrentUsername().orElse(null);
        if(username == null){
            throw new MemberException(MemberExceptionType.PLEASE_LOGIN_AGAIN);
        }
        return username;
    }




    //@Trace
    private void changeProfileImg(UpdateStudentDto updateStudentDto, Member member) throws IOException, UploadFileException {
        if(updateStudentDto.getProfileImg() ==null) return;
        if(!updateStudentDto.getProfileImg().isEmpty()){//비어있지 않은 경우
            fileService.deleteFile(member.getProfileImgPath());//원래 저장한 프사 삭제
            String saveFilePath = fileService.saveFile(updateStudentDto.getProfileImg());//새로운 프사 저장
            member.changeProfileImgPath(saveFilePath);
        }else{//비어있을 때, 프로필 사진을 바꾸려 한다 => 기존에 사진 지우고 기본 이미지로 변경

            if(updateStudentDto.isDoProfileImgChange()){//기본 이미지로 변경
                System.out.println("!!!"+member.getProfileImgPath());
                fileService.deleteFile(member.getProfileImgPath());//원래 저장한 프사 삭제
            }

        }
    }//상속받아 구현
    //@Trace
    private void changeAge(UpdateStudentDto updateStudentDto,  Member member) {
        if(updateStudentDto.getAge() != 0){
            member.changeAge(updateStudentDto.getAge());
        }
    }
    //@Trace
    private void changePassword(UpdateStudentDto updateStudentDto,  Member member) {
        if(StringUtils.hasLength(updateStudentDto.getNewPassword())){
            member.changePassword(updateStudentDto.getNewPassword(), passwordEncoder);
        }
    }
    //@Trace
    private void changeName(UpdateStudentDto updateStudentDto,  Member member) {
        if(StringUtils.hasLength(updateStudentDto.getName())){
            member.changeName(updateStudentDto.getName());
        }
    }

    //@Trace
    private void changeCareer(UpdateTeacherDto updateTeacherDto, Teacher teacher) {
        if(StringUtils.hasLength(updateTeacherDto.getCareer())){
            teacher.changeCareer(updateTeacherDto.getCareer());
        }
    }


}
