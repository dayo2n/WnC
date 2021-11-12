package com.springweb.web.repository.alarm;

import com.springweb.web.domain.alarm.Alarm;
import com.springweb.web.service.alarm.AlarmSearchCond;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AlarmRepositoryCustom {

    Page<Alarm> searchMyAlarms(String username,AlarmSearchCond cond, Pageable pageable);
}
