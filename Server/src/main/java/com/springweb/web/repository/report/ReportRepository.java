package com.springweb.web.repository.report;

import com.springweb.web.domain.report.Report;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ReportRepository extends JpaRepository<Report, Long> {


    @EntityGraph(attributePaths = {"writer", "target","solver"})
    Optional<Report> findWithStudentAndTeacherAndAdminById(Long id);



    @Query("select r from Report r left join fetch r.writer left join fetch r.target left join fetch r.solver order by r.createdDate desc ")
    List<Report> findAllWithWriterAndTargetAndSolverOrderByCreatedDate();
}
