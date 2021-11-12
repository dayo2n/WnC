package com.springweb.web.repository.evaluation;

import com.springweb.web.domain.evaluation.Evaluation;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {

    @EntityGraph(attributePaths = {"student"})
    List<Evaluation> findAllWithStudentByTeacherId(Long teacherId);
}
