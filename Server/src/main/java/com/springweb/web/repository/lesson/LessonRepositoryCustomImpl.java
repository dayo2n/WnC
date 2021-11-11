package com.springweb.web.repository.lesson;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.springweb.web.domain.lesson.*;
import com.springweb.web.domain.member.QTeacher;
import com.springweb.web.service.alarm.ReadType;
import com.springweb.web.service.lesson.LessonType;
import com.springweb.web.service.lesson.search.LessonSearchCond;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static com.springweb.web.domain.alarm.QAlarm.alarm;
import static com.springweb.web.domain.lesson.QGroupLesson.groupLesson;
import static com.springweb.web.domain.lesson.QLesson.lesson;
import static com.springweb.web.domain.member.QTeacher.teacher;

@Repository
@Slf4j
public class LessonRepositoryCustomImpl implements LessonRepositoryCustom{

    private final EntityManager em;
    private final JPAQueryFactory query;

    public LessonRepositoryCustomImpl(EntityManager em) {
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }


    //TODO :검색조건 타입 구현
    @Override
    public Page<Lesson> search(LessonSearchCond cond, Pageable pageable) {

        List<Lesson> content = query
                .selectFrom(lesson)
                .where(
                        titleHasStr(cond.getTitle()),
                        teacherNameHasStr(cond.getTeacherName()),
                        contentHasStr(cond.getContent()),
                        searchLessonTypeEq(cond.getLessonType()),
                        maxStudentCountGraterOrEq(cond.getMinStudentCount()),
                        maxStudentCountLowerOrEq(cond.getMaxStudentCount())
                )
                .leftJoin(lesson.teacher, teacher).fetchJoin() //선생님 페치조인
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Lesson> countQuery = query
                .selectFrom(lesson)
                .where(
                        titleHasStr(cond.getTitle()),
                        teacherNameHasStr(cond.getTeacherName()),
                        contentHasStr(cond.getContent()),
                        searchLessonTypeEq(cond.getLessonType()),
                        maxStudentCountGraterOrEq(cond.getMinStudentCount()),
                        maxStudentCountLowerOrEq(cond.getMaxStudentCount())
                );

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);
    }

    private BooleanExpression teacherNameHasStr(String teacherName) {
        return StringUtils.hasLength(teacherName) ? lesson.teacher.name.contains(teacherName) : null;
    }


    private BooleanExpression contentHasStr(String content) {
        return StringUtils.hasLength(content) ? lesson.content.contains(content) : null;
    }


    private BooleanExpression maxStudentCountGraterOrEq(int minStudentCount) {
        if(minStudentCount == 0 || minStudentCount ==1 ){
            return null;
        }
        return lesson.maxStudentCount.goe(minStudentCount);
    }

    private BooleanExpression maxStudentCountLowerOrEq(int maxStudentCount) {
        if(maxStudentCount == 0 || maxStudentCount ==1 ){
            return null;
        }
        return lesson.maxStudentCount.loe(maxStudentCount);
    }

    private BooleanExpression titleHasStr(String title) {
        return StringUtils.hasLength(title) ? lesson.title.contains(title) : null;
    }


    private BooleanExpression searchLessonTypeEq(LessonType lessonType) {

        if(lessonType == LessonType.GROUP){
            return lesson.instanceOf(GroupLesson.class);//TODO :lesson.instanceOf(GroupLesson.class)이거 되나?
        }else if(lessonType == LessonType.PERSONAL) {
            return lesson.instanceOf(PersonalLesson.class);
        }

        return null;
    }
}
