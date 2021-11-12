package com.springweb.web.repository.alarm;

import com.springweb.web.aop.annotation.Trace;
import com.springweb.web.domain.alarm.Alarm;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AlarmRepository extends JpaRepository<Alarm, Long>, AlarmRepositoryCustom {

    //TODO : 확인해 봐야 함..
    @Trace
    @EntityGraph(attributePaths = {"target", "lesson", "applicantMember"})
    Optional<Alarm> findWithAllById(Long id);




}
