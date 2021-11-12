package com.springweb.web.service.report;

import com.springweb.web.controller.dto.report.CreateReportDto;
import com.springweb.web.controller.dto.report.ReportDto;
import com.springweb.web.domain.member.Teacher;
import com.springweb.web.exception.member.MemberException;
import com.springweb.web.exception.report.ReportException;

import java.util.List;

/**
 * 신고 기능
 */
public interface ReportService {


    /**
     * 신고 접수
     * 학생 -> 선생 만 신고가능
     */
    void report( Long teacherId, CreateReportDto createReportDto) throws MemberException, ReportException;





    //== 어드민의 권한 ==//
    /**
     * 신고목록 가져오기
     */
    List<ReportDto> getList();

    /**
     * 신고내용 보기
     */
    ReportDto readReport(Long reportId);


    /**
     * 신고 무시하기 -> 읽었으나 아무 처리 X
     */
    void ignore(Long reportId);

    /**
     * 경고 주기
     */
    void addWarning(Long reportId) throws MemberException;

    /**
     * 블랙리스트 만들기
     */
    void makeBlack(Long reportId) throws MemberException;

    /**
     * 블랙리스트에서 원상복구히키기
     */
    void makeWhite(Long teacherId) throws MemberException;


    /**
     * 블랙리스트 목록 보기
     */
    List<BlackTeacher> showBlackList();

}
