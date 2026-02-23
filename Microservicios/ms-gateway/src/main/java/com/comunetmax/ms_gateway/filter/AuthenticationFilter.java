package com.comunetmax.ms_gateway.filter;

import com.comunetmax.ms_gateway.config.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    private JwtUtil jwtUtil; // Necesitarás copiar una versión simple de JwtUtil aquí también para validar

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            // Log para monitorear en la consola de Docker
            System.out.println("DEBUG: Petición recibida en: " + request.getURI().getPath());

            // 1. Validar presencia del Header
            if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                return onError(exchange, "Acceso no autorizado: No se encontró el encabezado de seguridad.", HttpStatus.UNAUTHORIZED);
            }

            String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

            // 2. Validar formato Bearer
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return onError(exchange, "Acceso no autorizado: El formato del token no es válido.", HttpStatus.UNAUTHORIZED);
            }

            String token = authHeader.substring(7);

            // 3. Validar integridad/expiración del Token
            if (!jwtUtil.validateToken(token)) {
                return onError(exchange, "Acceso no autorizado: El token ha expirado o es inválido.", HttpStatus.UNAUTHORIZED);
            }

            return chain.filter(exchange);
        };
    }

    private Mono<Void> onError(ServerWebExchange exchange, String message, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        // Cuerpo del JSON que verá el usuario en el navegador
        String body = "{\"error\": \"" + httpStatus.getReasonPhrase() +
                "\", \"mensaje\": \"" + message + "\"}";

        DataBuffer buffer = response.bufferFactory().wrap(body.getBytes());
        return response.writeWith(Mono.just(buffer));
    }

    public static class Config {}
}