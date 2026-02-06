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

    // 1. LISTAR TODOS
    @GetMapping
    public List<PlanInternet> listar() {
        return service.listarActivos();
    }

    // 2. CREAR
    @PostMapping
    public ResponseEntity<PlanInternet> crear(@RequestBody PlanInternet plan) {
        return ResponseEntity.ok(service.guardar(plan));
    }

    // 3. ELIMINAR
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // 4. BUSCAR POR ID
    @GetMapping("/{id}")
    public ResponseEntity<PlanInternet> obtenerPorId(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 5. ACTUALIZAR (El que causaba conflicto antes)
    @PutMapping("/{id}")
    public ResponseEntity<PlanInternet> actualizar(@PathVariable Long id, @RequestBody PlanInternet planDetalles) {
        return service.buscarPorId(id)
                .map(planExistente -> {
                    planExistente.setNombre(planDetalles.getNombre());
                    planExistente.setPrecio(planDetalles.getPrecio());
                    planExistente.setDescripcion(planDetalles.getDescripcion());
                    return ResponseEntity.ok(service.guardar(planExistente));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // 6. BUSCAR POR MUNICIPIO (El nuevo)
    @GetMapping("/municipio/{municipioId}")
    public ResponseEntity<List<PlanInternet>> obtenerPlanesPorMunicipio(@PathVariable Long municipioId) {
        CoberturaDTO cobertura = coberturaClient.obtenerCoberturaPorMunicipio(municipioId);

        if (cobertura == null || cobertura.getTecnologia() == null) {
            return ResponseEntity.notFound().build();
        }

        TipoTecnologia tecnologia = TipoTecnologia.valueOf(cobertura.getTecnologia());
        return ResponseEntity.ok(service.listarPorTecnologia(tecnologia));
    }
}