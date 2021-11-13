package com.springweb.web.service.alarm;

import com.springweb.web.dto.alarm.AlarmDto;
import com.springweb.web.dto.alarm.SearchAlarmDto;
import com.springweb.web.domain.alarm.AlarmType;
import com.springweb.web.domain.lesson.Lesson;
import com.springweb.web.domain.member.Member;
import com.springweb.web.domain.member.Student;
import com.springweb.web.exception.alarm.AlarmException;
import com.springweb.web.exception.member.MemberException;
import org.springframework.data.domain.Pageable;


public interface AlarmService {

    /**
     * 알람 보내기
     * 내 알람 보여주기
     * 알람 읽기
     * 알람은 삭제 못해!
     */

    void sendAlarm(AlarmType alarmType, Member target, Lesson lesson, Student applicantMember) throws AlarmException;
    void sendAlarm(AlarmType alarmType, Member target, Lesson lesson) throws AlarmException;

    AlarmDto readAlarm(Long alarmId) throws MemberException, AlarmException;

    SearchAlarmDto searchMyAlarms(AlarmSearchCond cond, Pageable pageable) throws MemberException;


     int getMyNoReadAlarm() throws MemberException;


}
