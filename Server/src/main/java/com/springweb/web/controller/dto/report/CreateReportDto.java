package com.springweb.web.controller.dto.report;

import com.springweb.web.domain.report.Report;
import lombok.Data;

@Data
public class CreateReportDto {

    private String content;



    public Report toEntity(){
        return Report.builder().content(content).build();
    }
}
