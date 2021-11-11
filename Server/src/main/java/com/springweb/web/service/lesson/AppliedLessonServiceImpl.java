package com.springweb.web.service.lesson;

import com.springweb.web.aop.annotation.Trace;
import com.springweb.web.domain.alarm.Alarm;
import com.springweb.web.domain.alarm.AlarmType;
import com.springweb.web.domain.lesson.*;
import com.springweb.web.domain.member.Member;
import com.springweb.web.domain.member.Student;
import com.springweb.web.domain.member.Teacher;
import com.springweb.web.exception.BaseException;
import com.springweb.web.exception.alarm.AlarmException;
import com.springweb.web.exception.lesson.LessonException;
import com.springweb.web.exception.lesson.LessonExceptionType;
import com.springweb.web.exception.member.MemberException;
import com.springweb.web.exception.member.MemberExceptionType;
import com.springweb.web.repository.alarm.AlarmRepository;
import com.springweb.web.repository.lesson.AppliedLessonRepository;
import com.springweb.web.repository.lesson.LessonRepository;
import com.springweb.web.repository.lesson.TakingLessonRepository;
import com.springweb.web.repository.member.MemberRepository;
import com.springweb.web.service.alarm.AlarmService;
import com.springweb.web.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AppliedLessonServiceImpl implements AppliedLessonService {

    private final LessonRepository lessonRepository;
    private final MemberRepository memberRepository;
    private final AlarmService alarmService;
    private final AlarmRepository alarmRepository;
    private final TakingLessonRepository takingLessonRepository;
    private final AppliedLessonRepository appliedLessonRepository;

    @Trace
    private String getMyUsername() throws MemberException {
        String username = SecurityUtil.getCurrentUsername().orElse(null);
        if(username == null){
            log.error("SecurityContextHolder에 있는 username을 가져오던 중 오류 발생");
            throw new MemberException(MemberExceptionType.PLEASE_LOGIN_AGAIN);
        }
        return username;
    }




    //TODO :알람 읽음표시 애햐 함

    /**
     * 가입신청
     * 이미 가입이 승인된 강의인 경우 => 또다시 신청하면 예외
     * 가입요청을 보내기만 한 경우 => 똑같이 예외
     */
    @Trace
    @Override
    public void apply(Long lessonId) throws BaseException {
        Lesson findLesson = lessonRepository.findWithTeacherById(lessonId).orElse(null);

        //== 강의 신청 가능여부 체크 로직 ==//

        if(findLesson == null){
            throw new LessonException(LessonExceptionType.NOT_FOUND_LESSON);
        }//== 신청할 강의가 없는경우 -> 먼가 문제;;==//
        if(findLesson.isCompleted()){
            throw new LessonException(LessonExceptionType.CAN_NOT_APPLY_LESSON_COMPLETED);
        }//모집완료된 경우

        Member me = memberRepository.findByUsername(getMyUsername()).orElse(null);
        if(me instanceof Teacher){
            throw new LessonException(LessonExceptionType.NO_AUTHORITY_APPLY_LESSON);//프론트에서 다 처리할거지만 그래도 혹시 몰라서 함
        }//선생님은 과외 신청 불가능


        //== 중복 가입신청 여부 확인 ==//
        Student student = (Student) me;
        //이미 가입이 승인된 강의인 경우 => 예외
        TakingLesson takingLesson = takingLessonRepository.findByLessonIdAndStudentId(lessonId, student.getId()).orElse(null);
        if(takingLesson != null){
            throw new LessonException(LessonExceptionType.ALREADY_APPLIED);
        }
        //이미 가입요청을 보낸 경우 => 예외
        AppliedLesson appliedLesson = appliedLessonRepository.findByLessonIdAndStudentId(lessonId, student.getId()).orElse(null);
        if(appliedLesson != null){
            throw new LessonException(LessonExceptionType.ALREADY_APPLIED);
        }
        //== 중복 가입신청 여부 확인 종료 ==//

        //== 강의 신청 가능여부 체크 로직 종료 ==//


        appliedLesson = AppliedLesson.builder()
                .teacherName(findLesson.getTeacher().getName())
                .lesson(findLesson)
                .student(student)
                .title(findLesson.getTitle())
                .teacherId(findLesson.getTeacher().getId())
                .build();


        student.addAppliedLesson(appliedLesson);
        //TODO: appliedLessonRepository.save(appliedLesson); 코드가 없어도 영속성 전이로 인해 저장될 거 같은데 확인해보기!
        appliedLessonRepository.save(appliedLesson);

        alarmService.sendAlarm(AlarmType.SEND_APPLY, findLesson.getTeacher(), findLesson ,student);//내가 선생님꼐 신청한거니까
    }



    /**
     * 신청 취소, 이미 수락된 경우와, 아직 수락 전인 경우
     *
     * 만약 과외가 이미 모집완료된 상태라면 예외
     *
     * 만약 이미 수락되었다면, takingLesson 제거하고, 그룹과외의 현재 학생 수 -1
     *
     * 신청만 한 상태라면 그냥 appliedLesson에서 제거함, 나머지는 accept에서 알아서 처리함
     * +신청이 취소되었다고 선생님꼐 알림
     *
     * 쿼리가 4번이나 나감...
     *
     */
    @Trace
    @Override
    public void cancel(Long lessonId) throws BaseException{
        Member me = memberRepository.findByUsername(getMyUsername()).orElse(null);
        if(me instanceof Teacher){
            throw new LessonException(LessonExceptionType.ETC_EXCEPTION);
        }//애당초 선생님한테는 강의 취소 버튼이 들어오지도 않음

        Student s_me = (Student)me;

        Lesson lesson = lessonRepository.findById(lessonId).orElse(null);//수강중인 학생이 있는경우 삭제 못하게 만들어둠=> 딱히 예외처리 할 필요 없음
        if(lesson.isCompleted()){
            throw new LessonException(LessonExceptionType.CAN_NOT_APPLY_CANCEL);
        }//모집완료된 강의의 경우 취소 불가능

        //이미 신청요청을 보낸 과외의 경우, 이미 가입된 과외의 경우
        AppliedLesson appliedLesson = appliedLessonRepository.findByLessonIdAndStudentId(lessonId, s_me.getId()).orElse(null);
        TakingLesson takingLesson = takingLessonRepository.findByLessonIdAndStudentId(lessonId, s_me.getId()).orElse(null);

        if(appliedLesson == null && takingLesson == null){
            throw new LessonException(LessonExceptionType.ETC_EXCEPTION);
        }//신청한것도 아니고, 수강한것도 아니라면 신청도 전에 취소 요청부터 보낸거므로 예외 발생

        if(takingLesson != null){//이미 수락된 상태라면
            GroupLesson groupLesson = (GroupLesson) takingLesson.getLesson();//개인레슨은 수락 시 바로 모집완료되므로 , 무조건 그룹레슨일 경우밖에 없음
            groupLesson.removeTakingLesson(takingLesson);//강의에서 지워주고, 강의 듣는 학생 -1,
            s_me.getTakingLessonList().remove(takingLesson);//학생이 듣는 강의에서도 지워줌

        }

    }



    /**
     수락했을때 보내지는 알람의 target은  applicantMember임
     *
     * 학생의 appliedLessonList에서 지워주고 takingLessonList에 추가시켜 주어야 함.
     *
     * 만약 학생이 요청을 보냈다 취소한 상태에서 수락을 누른 경우 예외를 발생시켜야 함.
     *
     *
     * 알람의 target이 현재 로그인한 사용자여야 함 + 알람의 lesson과 요청이 들어온 lesson이 같아야 함
     *
     * 중요!!! 알람의 타입이 SEND_APPLY 이어야 하며, 이때 read를 여기서 처리한다!
     */
    @Trace
    @Override
    public void accept(Long lessonId,Long alarmId) throws BaseException{
        Alarm alarm = alarmRepository.findWithAllById(alarmId).orElse(null);//페치조인 사용
        //== 임의로 주소 치고 들어와서 수락하는거 방지 ==//
        if(!alarm.getTarget().getUsername().equals(getMyUsername())){
            throw new LessonException(LessonExceptionType.NO_AUTHORITY_ACCEPT_LESSON);
        }
        if(alarm.getAlarmType() != AlarmType.SEND_APPLY){
            throw new LessonException(LessonExceptionType.ETC_EXCEPTION);
        }//SEND_APPLY가 아닌 경우 accept를 할 수 없음

        Lesson findLesson = alarm.getLesson();

        //== 강의 수락 가능여부 체크 로직 ==//
        if(findLesson == null){
            throw new LessonException(LessonExceptionType.NOT_FOUND_LESSON);
        }//== 수락할 강의가 없는경우 -> 먼가 문제;;==//

        //== 임의로 주소 치고 들어와서 수락하는거 방지 2 ==//
        if(!findLesson.getId().equals(lessonId)){//Long 이므로 equals비교 해야함
            throw new LessonException(LessonExceptionType.ETC_EXCEPTION);
        }


        //이 위에까지는 사용자가 아닌 권한 없는 사람이 임의로 접근할 거일수도 있으므로 read를 하지 않는다!
        alarm.read();//읽음 표시!

        if(findLesson.isCompleted()){
            throw new LessonException(LessonExceptionType.CAN_NOT_ACCEPT_STUDENT_COMPLETED);
        }//모집완료된 경우

        Student student = alarm.getApplicantMember();//신청한 학생
        AppliedLesson appliedLesson = appliedLessonRepository.findByLessonIdAndStudentId(findLesson.getId(), student.getId()).orElse(null);

        if(appliedLesson == null){
            throw new LessonException(LessonExceptionType.CAN_NOT_ACCEPT_STUDENT_CANCEL);
        }//신청한 강의가 없다면 => 즉 학생이 강의 신청을 보냈다가 그 요청을 취소한 경우
        //== 강의 신청 가능여부 체크 로직 종료 ==//


        student.getAppliedLessonList().remove(appliedLesson);//신청한 강의에서 지워주고


        //== 수락한 강의가 개인과외라면, 수락과 동시에 모집완료 + 알람, 학생이 수강한 강의에 추가 ==//
        casePersonalLesson(alarm, findLesson);

        //== 그룹과외라면, 인원이 다 찼으면 모집완료 알림, 아니라면 +1만,학생이 수강한 강의에 추가 ==//
        caseGroupLesson(alarm, findLesson);


    }


    private void caseGroupLesson(Alarm alarm, Lesson findLesson) throws BaseException {
        if(findLesson instanceof GroupLesson groupLesson){
            TakingLesson tl = TakingLesson.builder()
                    .lesson(groupLesson)  //여기서 강의 설정
                    .student(alarm.getApplicantMember())//여기서 학생 설정
                    .title(groupLesson.getTitle())
                    .teacherName(alarm.getTarget().getName())
                    .teacherId(alarm.getTarget().getId())
                    .build();

            groupLesson.addTakingLesson(tl);//학생 +1
            alarm.getApplicantMember().addTakingLesson(tl);//학생이 수강하는 강의 추가

            alarmService.sendAlarm(AlarmType.APPROVED, alarm.getApplicantMember(), findLesson);//신청한 학생에게 수학되었다고 알람

            //== 수락하여 모집이 완료된 경우 -> applyCompleted실행 ==//
            if(groupLesson.getNowStudentCount() == groupLesson.getMaxStudentCount()){
                applyCompleted(groupLesson.getId());//모집완료 알람 전송
            }
        }
    }

    private void casePersonalLesson(Alarm alarm, Lesson findLesson) throws AlarmException {
        if(findLesson instanceof PersonalLesson personalLesson){
            TakingLesson tl = TakingLesson.builder()
                    .lesson(personalLesson)  //여기서 강의 설정
                    .student(alarm.getApplicantMember())//여기서 학생 설정
                    .title(personalLesson.getTitle())
                    .teacherName(alarm.getTarget().getName())
                    .teacherId(alarm.getTarget().getId())
                    .build();

            alarm.getApplicantMember().addTakingLesson(tl);//학생이 듣는 강의 추가

            personalLesson.complete();//강의 모집완료

            alarmService.sendAlarm(AlarmType.APPROVED, alarm.getApplicantMember(), findLesson);//가입이 승인되었고
            alarmService.sendAlarm(AlarmType.COMPLETION,  alarm.getApplicantMember(), findLesson);//모집이 완료되었습니다
            alarmService.sendAlarm(AlarmType.COMPLETION, alarm.getTarget(), findLesson);//이거 안해줘도 될 것 같은데, 그냥 해주는게 나을듯???
        }
    }








    /**
     * 가입 거절
     *
     * 알람의 target이 현재 로그인한 사용자여야 함 + 알람의 lesson과 요청이 들어온 lesson이 같아야 함
     *
     *  중요!!! 알람의 타입이 SEND_APPLY 이어야 하며, 이때 read를 여기서 처리한다!
     */
    @Trace
    @Override
    public void refuse(Long lessonId,Long alarmId) throws BaseException{
        Alarm alarm = alarmRepository.findWithAllById(alarmId).orElse(null);//페치조인 사용

        //== 임의로 주소 치고 들어와서 수락하는거 방지 ==//
        if(!alarm.getTarget().getUsername().equals(getMyUsername())) throw new LessonException(LessonExceptionType.NO_AUTHORITY_ACCEPT_LESSON);

        if(alarm.getAlarmType() != AlarmType.SEND_APPLY) throw new LessonException(LessonExceptionType.ETC_EXCEPTION);//SEND_APPLY가 아닌 경우 refuse를 할 수 없음 => 뭔가 잘못된 요청임

        Lesson findLesson = alarm.getLesson();
        if(!findLesson.getId().equals(lessonId)) throw new LessonException(LessonExceptionType.ETC_EXCEPTION);

        if(findLesson == null) throw new LessonException(LessonExceptionType.NOT_FOUND_LESSON);//== 과외가 없는경우 -> 먼가 문제;;==//


        alarm.read();//읽음 표시! , 이 위에까지는 사용자가 아닌 권한 없는 사람이 임의로 접근할 거일수도 있으므로 read를 하지 않는다!

        //모집완료된 경우에도 결국은 거절이므로!
        alarmService.sendAlarm(AlarmType.REFUSED, alarm.getApplicantMember(), findLesson);//신청한 학생에게 거절되었다고 알림
    }


    /**
     * 모집 완료할 강의가 내가 올린 강의여야 함
     */
    //== 모집완료 ==//
    @Trace
    @Override
    public void applyCompleted(Long lessonId) throws BaseException{

        Lesson findLesson = lessonRepository.findWithTeacherById(lessonId).orElse(null);

        //== 강의 모집완료 ==//
        if(findLesson == null){
            throw new LessonException(LessonExceptionType.NOT_FOUND_LESSON);
        }// 완료할 강의가 없는경우 -> 먼가 문제;;==//
        if(!findLesson.getTeacher().getUsername().equals(getMyUsername())){
            throw new LessonException(LessonExceptionType.ETC_EXCEPTION);
        }//내가 올린 강의가 아닌데 모집완료 버튼을 누른경우

        if(findLesson.isCompleted()){
            throw new LessonException(LessonExceptionType.ALREADY_COMPLETED);
        }//이미 모집완료된 경우
        List<TakingLesson> takingLessonList = takingLessonRepository.findAllWithStudentByLessonId(lessonId);//강의1개에, 여러 학생이 나올거임

        findLesson.complete();//강의 모집완료로 바꾸기.

        //== 선생님께 알람 보내기 ==//
        alarmService.sendAlarm(AlarmType.COMPLETION, findLesson.getTeacher(), findLesson);

        for (TakingLesson tl : takingLessonList) {//학생들한테 알람 보내기
            alarmService.sendAlarm(AlarmType.COMPLETION, tl.getStudent(), findLesson);
        }

    }



}
