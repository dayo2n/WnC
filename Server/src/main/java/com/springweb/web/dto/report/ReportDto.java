package com.springweb.web.dto.report;

import com.springweb.web.domain.report.Report;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReportDto {

    private Long reportId;  //신고 id
    private String content; //신고내용
    private LocalDateTime createDate;//신고날짜


    private Long writerId;  //작성자id
    private String writerName; //작성자 이름
    private String writerUsername; //작성자 username

    private Long targetTeacherId; //신고당한 선생님 id
    private String targetTeacherName;//신고당한 선생님 이름
    private String targetTeacherUsername;//신고당한 선생님 username

    private boolean isSolved; //해결되었는지 여부

    private Long solverAdminId;//해결한 관리자 id
    private String solverAdminName;//해결한 관리자 이름

    public ReportDto(Report report) {

        this.reportId = report.getId();
        this.content = report.getContent();
        this.createDate = report.getCreatedDate();

        this.writerId = report.getWriter().getId();
        this.writerName = report.getWriter().getName();
        this.writerUsername = report.getWriter().getUsername();;

        this.targetTeacherId = report.getTarget().getId();
        this.targetTeacherName = report.getTarget().getName();
        this.targetTeacherUsername = report.getTarget().getUsername();

        this.isSolved = report.isSolved();

        if(isSolved){//해결되었다면 관리자가 존재
            this.solverAdminId = report.getSolver().getId();
            this.solverAdminName = report.getSolver().getName();
        }

    }
}
