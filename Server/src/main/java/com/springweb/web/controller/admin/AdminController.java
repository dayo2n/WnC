package com.springweb.web.controller.admin;

import com.springweb.web.aop.annotation.Trace;
import com.springweb.web.controller.dto.report.ReportDto;
import com.springweb.web.exception.member.MemberException;
import com.springweb.web.exception.report.ReportException;
import com.springweb.web.service.report.BlackTeacher;
import com.springweb.web.service.report.ReportService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
@PreAuthorize("hasRole('ADMIN')")//이거되나?
public class AdminController {

    private final ReportService reportService;


    /**
     * 신고목록 보기
     */
    @Trace
    @GetMapping("/reports")
    public ResponseEntity getReportList() throws ReportException, MemberException {
        List<ReportDto> list = reportService.getList();

        return new ResponseEntity(new ListReportDto(list) , HttpStatus.OK);
    }

    /**
     * 신고내용 보기
     */
    @Trace
    @GetMapping("/reports/{reportId}")
    public ResponseEntity getReport(@PathVariable("reportId")Long reportId) throws ReportException, MemberException {
        ReportDto reportDto = reportService.readReport(reportId);

        return new ResponseEntity(reportDto , HttpStatus.OK);
    }


    /**
     * 신고 무시하기 -> 읽었으나 아무 처리 X
     */
    @Trace
    @PostMapping("/reports/{reportId}/ignore")
    public ResponseEntity ignore(@PathVariable("reportId")Long reportId) throws ReportException, MemberException {
        reportService.ignore(reportId);
        return new ResponseEntity("신고를 무시했습니다" , HttpStatus.OK);
    }


    /**
     * 경고 주기
     */
    @Trace
    @PostMapping("/reports/{reportId}/warn")
    public ResponseEntity addWarning(@PathVariable("reportId")Long reportId ) throws ReportException, MemberException {
        reportService.addWarning(reportId);

        return new ResponseEntity("경고 요청을 주었습니다." , HttpStatus.OK);
    }

    /**
     * 블랙리스트 만들기
     */
    @Trace
    @PostMapping("/reports/{reportId}/black")
    public ResponseEntity makeBlack(@PathVariable("reportId")Long reportId ) throws ReportException, MemberException {
        reportService.makeBlack(reportId);

        return new ResponseEntity("블랙리스트로 만들었습니다." , HttpStatus.OK);
    }

    /**
     * 블랙리스트에서 원상복구히키기
     */
    @Trace
    @PostMapping("/reports/white/{teacherId}")
    public ResponseEntity makeWhite(@PathVariable("teacherId")Long teacherId) throws ReportException, MemberException {
        reportService.makeWhite(teacherId);

        return new ResponseEntity(teacherId+"번의 선생님을 블랙리스트에서 해제했습니다.", HttpStatus.OK);
    }


    /**
     * 블랙리스트 목록 조회
     */
    @Trace
    @GetMapping("/reports/blacklist")
    public ResponseEntity showBlackList() throws ReportException, MemberException {
        List<BlackTeacher> blackTeachers = reportService.showBlackList();

        return new ResponseEntity(blackTeachers, HttpStatus.OK);
    }











    @Data
    private static class ListReportDto{
        private int totalReportCount;

        private List<ReportDto> reportDtos;

        public ListReportDto(List<ReportDto> reportDtos) {
            this.totalReportCount = reportDtos.size();
            this.reportDtos = reportDtos;
        }
    }

}
