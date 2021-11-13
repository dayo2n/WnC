package com.springweb.web.repository.member;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.springweb.web.domain.member.Teacher;
import com.springweb.web.service.member.search.TeacherSearchCond;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static com.springweb.web.domain.lesson.QLesson.lesson;
import static com.springweb.web.domain.member.QTeacher.teacher;

@Slf4j
@Repository
public class MemberRepositoryCustomImpl implements MemberRepositoryCustom{

    private final EntityManager em;
    private final JPAQueryFactory query;

    public MemberRepositoryCustomImpl(EntityManager em) {
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }


    @Override
    public Page<Teacher> search(TeacherSearchCond cond, Pageable pageable) {

        List<Teacher> content= new ArrayList<>();
        if(cond.isDESC()){
             content = query
                    .selectFrom(teacher)
                    .where(
                            teacherNameHasStr(cond.getTeacherName())
                            )
                    .orderBy(teacher.starPoint.desc())
                    .orderBy(teacher.createdDate.desc())
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .fetch();
        }else {
            content = query
                    .selectFrom(teacher)
                    .where(
                            teacherNameHasStr(cond.getTeacherName())
                            )
                    .orderBy(teacher.starPoint.asc())
                    .orderBy(teacher.createdDate.desc())
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .fetch();
        }


        JPAQuery<Teacher> countQuery = query
                .selectFrom(teacher)
                .where(
                        teacherNameHasStr(cond.getTeacherName())
                );


        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);
    }



    private BooleanExpression teacherNameHasStr(String teacherName) {
        return StringUtils.hasLength(teacherName) ? teacher.name.contains(teacherName) : null;
    }


}
