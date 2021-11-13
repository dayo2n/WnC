package com.springweb.web.dto.login;

import lombok.*;

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