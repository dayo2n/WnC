package com.springweb.web.repository.report;

import com.springweb.web.domain.report.Report;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReportRepository extends JpaRepository<Report, Long> {


    @EntityGraph(attributePaths = {"writer", "target","solver"})
    Optional<Report> findWithStudentAndTeacherAndAdminById(Long id);


    @EntityGraph(attributePaths = {"writer", "target","solver"})
    List<Report> findAllWithStudentAndTeacherAndAdminById();
}
