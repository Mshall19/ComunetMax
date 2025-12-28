package com.comunetmax.ms_usuarios.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Bean
    public RestClient restClient() {
        // En lugar de usar el builder inyectado, lo creamos directo
        return RestClient.create();
    }
}