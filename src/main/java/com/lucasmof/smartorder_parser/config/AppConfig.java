package com.lucasmof.smartorder_parser.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    // Ensinando o Spring a injetar o ObjectMapper
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

}
