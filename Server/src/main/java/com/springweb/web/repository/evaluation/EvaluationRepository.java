package com.springweb.web.repository.evaluation;

import com.springweb.web.domain.evaluation.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {
}
