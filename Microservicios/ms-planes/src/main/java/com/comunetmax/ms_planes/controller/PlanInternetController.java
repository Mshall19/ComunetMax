package com.comunetmax.ms_planes.controller;

import com.comunetmax.ms_planes.model.PlanInternet;
import com.comunetmax.ms_planes.service.PlanInternetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/planes")
@RequiredArgsConstructor
public class PlanInternetController {

    private final PlanInternetService service;

    @GetMapping
    public List<PlanInternet> listar() {
        return service.listarActivos();
    }

    @PostMapping
    public ResponseEntity<PlanInternet> crear(@RequestBody PlanInternet plan) {
        return ResponseEntity.ok(service.guardar(plan));
    }

    // Endpoint secreto para crear datos falsos y probar rápido
    @PostMapping("/seed")
    public String poblarDatos() {
        service.crearDatosPrueba();
        return "Datos de prueba creados";
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanInternet> obtenerPorId(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}