package com.springweb.web.repository.lesson;


import com.springweb.web.domain.lesson.AppliedLesson;
import com.springweb.web.domain.lesson.TakingLesson;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AppliedLessonRepository extends JpaRepository<AppliedLesson, Long> {

    @EntityGraph(attributePaths = {"student"})
    List<AppliedLesson> findAllWithStudentByLessonId(Long lessonId);


    @Query("select al from AppliedLesson al where al.student.id = :studentId and al.lesson.id = :lessonId")
    Optional<AppliedLesson> findByLessonIdAndStudentId(@Param(value = "lessonId")Long lessonId, @Param(value = "studentId")Long studentId);
}
