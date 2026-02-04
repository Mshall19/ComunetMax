package com.comunetmax.ms_cobertura.controller;

import com.comunetmax.ms_cobertura.model.*;
import com.comunetmax.ms_cobertura.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cobertura")
@RequiredArgsConstructor
public class CoberturaController {

    private final CoberturaRepository coberturaRepository;
    private final MunicipioRepository municipioRepository;

    // Consultar qué lugares tienen cobertura actualmente
    @GetMapping
    public List<Cobertura> listarCoberturas() {
        return coberturaRepository.findAll();
    }

    // Consultar el catálogo de municipios disponibles para asignar
    @GetMapping("/municipios")
    public List<Municipio> listarMunicipios() {
        return municipioRepository.findAll();
    }

    // ASIGNAR O CAMBIAR TECNOLOGÍA
    // POST /api/cobertura/asignar/1?tecnologia=FIBRA_OPTICA
    @PostMapping("/asignar/{municipioId}")
    public ResponseEntity<Cobertura> asignar(
            @PathVariable Long municipioId,
            @RequestParam TipoTecnologia tecnologia) {

        return municipioRepository.findById(municipioId).map(muni -> {
            Cobertura nueva = new Cobertura();
            nueva.setMunicipio(muni);
            nueva.setTecnologia(tecnologia);
            return ResponseEntity.ok(coberturaRepository.save(nueva));
        }).orElse(ResponseEntity.notFound().build());
    }
}