package com.springweb.web.controller.report;

import com.springweb.web.dto.report.CreateReportDto;
import com.springweb.web.exception.member.MemberException;
import com.springweb.web.exception.report.ReportException;
import com.springweb.web.service.report.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ReportController {

    private final ReportService reportService;



    @PostMapping("/reports/teachers/{teacherId}")
    public ResponseEntity report(@PathVariable("teacherId")Long teacherId, @ModelAttribute CreateReportDto createReportDto) throws ReportException, MemberException {
        reportService.report(teacherId, createReportDto);

        return new ResponseEntity("신고접수가 완료되었습니다." , HttpStatus.OK);
    }

}
