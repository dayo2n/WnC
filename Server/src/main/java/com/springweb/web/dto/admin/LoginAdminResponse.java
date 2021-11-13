package com.springweb.web.dto.admin;

import com.springweb.web.dto.login.MemberType;
import com.springweb.web.dto.report.ReportDto;
import lombok.Data;

import java.util.List;

@Data
public class LoginAdminResponse {

    private String token;

    private Long id;

    private List<ReportDto> reportDtoList;


    public LoginAdminResponse(String token, Long id, List<ReportDto> reportDtoList) {
        this.token = token;
        this.id = id;
        this.reportDtoList = reportDtoList;
    }
}
