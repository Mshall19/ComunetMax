package com.comunetmax.ms_cobertura.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Desactiva protección CSRF para pruebas
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // ¡ESTA ES LA MAGIA: permite todo sin login!
                )
                .httpBasic(basic -> basic.disable()) // Desactiva el popup de login
                .formLogin(form -> form.disable()); // Desactiva el formulario de la imagen que viste

        return http.build();
    }
}