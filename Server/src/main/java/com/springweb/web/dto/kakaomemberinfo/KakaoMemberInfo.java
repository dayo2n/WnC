package com.springweb.web.dto.kakaomemberinfo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)//https://coding-today.tistory.com/24
public class KakaoMemberInfo {

    private Long id;//kakaoId

}
