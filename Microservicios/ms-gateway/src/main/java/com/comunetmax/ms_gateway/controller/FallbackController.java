package com.comunetmax.ms_gateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class FallbackController {

    @GetMapping(value = "/fallback/usuarios", produces = "text/plain;charset=UTF-8")
    public Mono<String> usuariosFallback() {
        return Mono.just("El servicio de Usuarios no está disponible. Intente más tarde.");
    }

    @GetMapping(value = "/fallback/planes", produces = "text/plain;charset=UTF-8")
    public Mono<String> planesFallback() {
        return Mono.just("El servicio de Planes no está disponible actualmente.");
    }

    @GetMapping(value = "/fallback/contacto", produces = "text/plain;charset=UTF-8")
    public Mono<String> contactoFallback() {
        return Mono.just("El servicio de Contacto está fuera de línea.");
    }
}