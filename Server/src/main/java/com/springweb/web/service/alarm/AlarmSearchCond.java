package com.springweb.web.service.alarm;

import lombok.Data;

@Data
public class AlarmSearchCond {
    private ReadType readType; //READ, NOT_READ, ALL
}
