package com.comunetmax.ms_usuarios.client; // Verifica que esta sea tu carpeta real

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.ResponseEntity;

@FeignClient(name = "ms-planes", url = "http://ms-planes:8080/api/planes")
public interface PlanCliente {

    @GetMapping("/{id}")
    ResponseEntity<?> obtenerPlanPorId(@PathVariable("id") Long id);
}