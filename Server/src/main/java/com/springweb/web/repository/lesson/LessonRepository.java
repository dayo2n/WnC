package com.springweb.web.repository.lesson;

import com.springweb.web.domain.lesson.Lesson;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LessonRepository extends JpaRepository<Lesson, Long> , LessonRepositoryCustom{

    @EntityGraph(attributePaths = {"teacher"})
    Optional<Lesson> findWithTeacherById(Long id);


}
