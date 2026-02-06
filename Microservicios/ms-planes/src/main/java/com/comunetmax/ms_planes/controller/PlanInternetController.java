package com.comunetmax.ms_planes.controller;

import com.comunetmax.ms_planes.client.CoberturaClient;
import com.comunetmax.ms_planes.dto.CoberturaDTO;
import com.comunetmax.ms_planes.model.PlanInternet;
import com.comunetmax.ms_planes.model.TipoTecnologia;
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
    private final CoberturaClient coberturaClient;

    @GetMapping
    public List<PlanInternet> listar() {
        return service.listarActivos();
    }

    @PostMapping
    public ResponseEntity<PlanInternet> crear(@RequestBody PlanInternet plan) {
        return ResponseEntity.ok(service.guardar(plan));
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

    @PutMapping("/{id}")
    public ResponseEntity<PlanInternet> actualizar(@PathVariable Long id, @RequestBody PlanInternet planDetalles) {
        return service.buscarPorId(id)
                .map(planExistente -> {
                    // Actualizamos los campos
                    planExistente.setNombre(planDetalles.getNombre());
                    planExistente.setPrecio(planDetalles.getPrecio());
                    planExistente.setDescripcion(planDetalles.getDescripcion());
                    // Guardamos los cambios
                    return ResponseEntity.ok(service.guardar(planExistente));
                })
                .orElse(ResponseEntity.notFound().build());
    }


    @GetMapping("/municipio/{municipioId}")
    public ResponseEntity<List<PlanInternet>> obtenerPlanesPorMunicipio(@PathVariable Long municipioId) {

        // 1. Llamamos y recibimos una COBERTURA
        CoberturaDTO cobertura = coberturaClient.obtenerCoberturaPorMunicipio(municipioId);

        // 2. Validación (Se lee mucho más natural: "si la cobertura no tiene tecnología...")
        if (cobertura == null || cobertura.getTecnologia() == null) {
            return ResponseEntity.notFound().build();
        }

        // 3. Convertimos y filtramos
        TipoTecnologia tecnologia = TipoTecnologia.valueOf(cobertura.getTecnologia());
        return ResponseEntity.ok(service.listarPorTecnologia(tecnologia));
    }
}