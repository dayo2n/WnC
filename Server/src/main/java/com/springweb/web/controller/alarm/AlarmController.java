package com.springweb.web.controller.alarm;

import com.springweb.web.dto.alarm.AlarmDto;
import com.springweb.web.dto.alarm.SearchAlarmDto;
import com.springweb.web.exception.BaseException;
import com.springweb.web.exception.member.MemberException;
import com.springweb.web.service.alarm.AlarmSearchCond;
import com.springweb.web.service.alarm.AlarmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AlarmController {

    private final AlarmService alarmService;



    @GetMapping("/myInfo/myAlarms")
    public ResponseEntity searchTeacher(AlarmSearchCond cond,
                                        @PageableDefault(page = 0, size = 20
                                        ) Pageable pageable) throws MemberException {

        SearchAlarmDto result = alarmService.searchMyAlarms(cond, pageable);
        return new ResponseEntity(result, HttpStatus.OK);
    }



    @GetMapping("/myInfo/myAlarms/{alarmId}")
    public ResponseEntity readAlarm(@PathVariable("alarmId") Long alarmId) throws BaseException {

        AlarmDto alarmDto = alarmService.readAlarm(alarmId);
        return new ResponseEntity(alarmDto, HttpStatus.OK);
    }

}
