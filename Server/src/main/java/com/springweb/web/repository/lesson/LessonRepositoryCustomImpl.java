package com.springweb.web.repository.lesson;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.springweb.web.domain.lesson.Lesson;
import com.springweb.web.domain.lesson.QGroupLesson;
import com.springweb.web.domain.lesson.QLesson;
import com.springweb.web.domain.member.QTeacher;
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


    @Override
    public Page<Lesson> search(LessonSearchCond cond, Pageable pageable) {

        List<Lesson> content = query
                .selectFrom(lesson)
                .where(
                        titleHasStr(cond.getTitle()),
                        teacherNameHasStr(cond.getTeacherName()),
                        contentHasStr(cond.getContent()),
                        maxStudentCountUnder(cond.getMaxStudentCount())
                )
                .join(lesson.teacher, teacher).fetchJoin() //선생님 페치조인
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Lesson> countQuery = query
                .selectFrom(lesson)
                .where(
                        titleHasStr(cond.getTitle()),
                        teacherNameHasStr(cond.getTeacherName()),
                        contentHasStr(cond.getContent()),
                        maxStudentCountUnder(cond.getMaxStudentCount())
                );

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);
    }

    private BooleanExpression teacherNameHasStr(String teacherName) {
        return StringUtils.hasLength(teacherName) ? lesson.teacher.name.contains(teacherName) : null;
    }


    private BooleanExpression contentHasStr(String content) {
        return StringUtils.hasLength(content) ? lesson.content.contains(content) : null;
    }

    //TODO : maxStudentCountUnder메소드 잘 실행되는지 확인
    private BooleanExpression maxStudentCountUnder(int maxStudentCount) {
        if(maxStudentCount == 0){
            return null;
        }
        return lesson.maxStudentCount.lt(maxStudentCount);
    }

    private BooleanExpression titleHasStr(String title) {
        return StringUtils.hasLength(title) ? lesson.title.contains(title) : null;
    }
}
