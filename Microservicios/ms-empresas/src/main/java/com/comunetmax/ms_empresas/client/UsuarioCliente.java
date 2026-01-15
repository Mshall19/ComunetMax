package com.comunetmax.ms_empresas.client; // Tu paquete correspondiente

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-usuarios", url = "http://ms-usuarios:8080/api/usuarios")
public interface UsuarioCliente {

    @GetMapping("/{id}")
    ResponseEntity<?> obtenerUsuarioPorId(@PathVariable("id") Long id);
}