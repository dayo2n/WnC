package com.springweb.web.repository.lesson;


import com.springweb.web.aop.annotation.Trace;
import com.springweb.web.domain.lesson.TakingLesson;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TakingLessonRepository extends JpaRepository<TakingLesson, Long> {

    @EntityGraph(attributePaths = {"student"})
    List<TakingLesson> findAllWithStudentByLessonId(Long lessonId);


    @Query("select tl from TakingLesson tl where tl.student.id = :studentId and tl.lesson.id = :lessonId")
    Optional<TakingLesson> findByLessonIdAndStudentId(@Param(value = "lessonId")Long lessonId, @Param(value = "studentId")Long studentId);


    //TODO: 이거 실험해 봐야 함... 카타시안 곱 나오면서 쿼리 터질 거 같은데...ㅠㅠㅠㅠ
    @Query("select tl from TakingLesson tl where tl.student.id =:studentId and tl.lesson.teacher.id = :teacherId")
    List<TakingLesson> findAllByTeacherIdAndStudentId(@Param(value = "teacherId")Long teacherId, @Param(value = "studentId")Long studentId);




    @Query("select tl from TakingLesson tl join fetch tl.lesson l join fetch l.teacher t where tl.student.username = :username")
    List<TakingLesson> findAllWithTeacherByStudentUsername(@Param(value = "username")String username);



    @Query("select tl from TakingLesson tl join fetch tl.lesson l where tl.student.id = :studentId")
    List<TakingLesson> findAllWithLessonByStudentId(@Param(value = "studentId")Long studentId);
}
