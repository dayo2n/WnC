package com.springweb.web.controller.dto.login;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class KakaoLoginDto {

    @NotNull
    private String accessToken;
}
