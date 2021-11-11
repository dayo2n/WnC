package com.springweb.web.controller.dto.login;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BasicLoginDto {

   /* @NotNull
    @Size(min = 3, max = 50)*/
    private String username;

    /*@NotNull
    @Size(min = 3, max = 100)*/
    private String password;
}