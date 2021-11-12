package com.springweb.web.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class WebConfig {

    //TODO : 좆대로 ObjectMappper를 쳐 만들어서 등록하지 말자^^ 이미 스프링부트가 등록해준다 이 씨발년아 ^^
    /*@Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }*/

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
