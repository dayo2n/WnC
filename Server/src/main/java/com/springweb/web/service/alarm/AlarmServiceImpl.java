package com.springweb.web.service.alarm;

import com.springweb.web.aop.annotation.Trace;
import com.springweb.web.controller.dto.alarm.AlarmDto;
import com.springweb.web.controller.dto.alarm.SearchAlarmDto;
import com.springweb.web.domain.alarm.Alarm;
import com.springweb.web.domain.alarm.AlarmType;
import com.springweb.web.domain.lesson.Lesson;
import com.springweb.web.domain.member.Member;
import com.springweb.web.domain.member.Student;
import com.springweb.web.exception.alarm.AlarmException;
import com.springweb.web.exception.alarm.AlarmExceptionType;
import com.springweb.web.exception.member.MemberException;
import com.springweb.web.exception.member.MemberExceptionType;
import com.springweb.web.repository.alarm.AlarmRepository;
import com.springweb.web.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class AlarmServiceImpl implements AlarmService{

    private final AlarmRepository alarmRepository;
    /**
     * SEND_APPLY => 과외 신청 보냈을 때  =>
     *              교수님 : XX학생이 YY과외에 가입신청을 보냈습니다. 수락하시겠습니까? (예, 아니오)
     *
     * APPROVED => 승인 
     *              학생 : YY과외에 가입이 거절되었습니다.
     *
     * REFUSED =>   거정
     *                학생 : YY과외에 가입이 거절되었습니다.
     *
     * COMPLETION => 교수님 + 학생들 모두에게 : YY 과외 모집이 완료되었습니다.
     *
     *
     *
     * SEND_APPLY, APPROVED, COMPLETION
     *
     * SEND_APPLY 이면 applicantMember, target, lesson이 모두 있어야 한다.
     * APPROVED,REFUSED   이면 target, lesson이 있어야 한다.
     * COMPLETION 이면 target, lesson
     */

    @Override
    public void sendAlarm(AlarmType alarmType, Member target,Lesson lesson, Student applicantMember) throws AlarmException {
        if(alarmType != AlarmType.SEND_APPLY){
            throw new AlarmException(AlarmExceptionType.ALARM_PRODUCE_EXCEPTION);
        }

        Alarm alarm = Alarm.builder().alarmType(alarmType).target(target)
                .lessonId(lesson.getId())
                .lessonTitle(lesson.getTitle())
                .lessonTeacherName(lesson.getTeacher().getName())
                .applicantMemberId(applicantMember.getId())
                .applicantMemberName(applicantMember.getName())
                .build();
        alarmRepository.save(alarm);

    }

    @Override
    public void sendAlarm(AlarmType alarmType, Member target, Lesson lesson) throws AlarmException {
        if(alarmType == AlarmType.SEND_APPLY ){
            throw new AlarmException(AlarmExceptionType.ALARM_PRODUCE_EXCEPTION);
        }


        Alarm alarm = Alarm.builder().alarmType(alarmType).target(target)
                .lessonId(lesson.getId())
                .lessonTitle(lesson.getTitle())
                .lessonTeacherName(lesson.getTeacher().getName())
                .build();
        alarmRepository.save(alarm);

    }




    @Override
    public AlarmDto readAlarm(Long alarmId) throws MemberException, AlarmException {
        Alarm alarm = alarmRepository.findById(alarmId).orElse(null);
        if(alarm == null){
            throw new AlarmException(AlarmExceptionType.NO_EXIST_ALARM);
        }
        if(!alarm.getTarget().getUsername().equals(getMyUsername())){
            throw new AlarmException(AlarmExceptionType.NO_AUTHORITY_READ_ALARM);
        }

        //== 중요!! 알람이 SEND_APPLY형식, 즉 XX학생이 가입요청을 보냈습니다인 경우 예, 아니오를 누르기 전까진 읽음 처리 안할거임!  ==//
        //=> 즉 AppliedLessonService의 accept 이나 refuse에서 읽음 처리를 함!
        if(alarm.getAlarmType() == AlarmType.SEND_APPLY){
            return new AlarmDto(alarm);
        }

        alarm.read();
        return new AlarmDto(alarm);
    }

    @Override
    public SearchAlarmDto searchMyAlarms(AlarmSearchCond cond, Pageable pageable) throws MemberException {
        Page<Alarm> alarmPage = alarmRepository.searchMyAlarms(getMyUsername(), cond, pageable);
        return new SearchAlarmDto(alarmPage);
    }




    @Trace
    private String getMyUsername() throws MemberException {
        String username = SecurityUtil.getCurrentUsername().orElse(null);
        if(username == null){
            log.error("SecurityContextHolder에 있는 username을 가져오던 중 오류 발생");
            throw new MemberException(MemberExceptionType.PLEASE_LOGIN_AGAIN);
        }
        return username;
    }
}
