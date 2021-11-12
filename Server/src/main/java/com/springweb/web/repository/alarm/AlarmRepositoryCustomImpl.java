package com.springweb.web.repository.alarm;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.springweb.web.domain.alarm.Alarm;
import com.springweb.web.service.alarm.AlarmSearchCond;
import com.springweb.web.service.alarm.ReadType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.springweb.web.domain.alarm.QAlarm.alarm;

@Repository
@Slf4j
public class AlarmRepositoryCustomImpl implements AlarmRepositoryCustom{
    private final EntityManager em;
    private final JPAQueryFactory query;

    public AlarmRepositoryCustomImpl(EntityManager em) {
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }


    @Override
    public Page<Alarm> searchMyAlarms(String username,AlarmSearchCond cond, Pageable pageable) {

        List<Alarm> content = query
                .selectFrom(alarm)
                .where(
                        searchTypeEq(cond.getReadType()),
                        eqUsername(username)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Alarm> countQuery = query
                .selectFrom(alarm)
                .where(
                        searchTypeEq(cond.getReadType()),
                        eqUsername(username)
                );

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);
    }

    private BooleanExpression eqUsername(String username) {
        return alarm.target.username.eq(username);
    }

    private BooleanExpression searchTypeEq(ReadType readType) {

        if(readType == ReadType.READ){
            return alarm.isRead.eq(true);
        }else if(readType == ReadType.NOT_READ) {
            return alarm.isRead.eq(false);
        }

        return null;
    }


}
