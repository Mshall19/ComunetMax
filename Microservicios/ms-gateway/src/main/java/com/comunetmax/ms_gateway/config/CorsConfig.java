package com.comunetmax.ms_gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
public class CorsConfig {

    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration corsConfig = new CorsConfiguration();

        // 1. Permitir orígenes
        // En desarrollo usamos "*" para aceptar desde cualquier origen
        // En producción cambiarías esto por la URL real de tu front (ej: http://localhost:4200)
        corsConfig.setAllowedOrigins(Arrays.asList("*"));

        // 2. Permitir todos los métodos HTTP (GET, POST, PUT, DELETE, OPTIONS)
        corsConfig.setMaxAge(3600L); // Tiempo de caché de la configuración
        corsConfig.addAllowedMethod("*");

        // 3. Permitir todos los encabezados
        corsConfig.addAllowedHeader("*");

        // 4. Configurar la fuente basada en URL para que aplique a todas las rutas
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);

        return new CorsWebFilter(source);
    }
}