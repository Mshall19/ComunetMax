package com.comunetmax.ms_usuarios.client;

import com.comunetmax.ms_usuarios.dto.PlanDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// name: nombre del servicio destino
// url: la dirección donde está corriendo ms-planes
@FeignClient(name = "ms-planes", url = "http://localhost:8081/api/planes")
public interface PlanCliente {

    @GetMapping("/{id}")
    PlanDTO obtenerPlanPorId(@PathVariable Long id);
}