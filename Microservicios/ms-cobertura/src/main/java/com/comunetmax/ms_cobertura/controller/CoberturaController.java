package com.comunetmax.ms_cobertura.controller;

import com.comunetmax.ms_cobertura.model.*;
import com.comunetmax.ms_cobertura.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/cobertura")
@RequiredArgsConstructor
public class CoberturaController {

    private final CoberturaRepository coberturaRepository;
    private final MunicipioRepository municipioRepository;

    // 1. CATÁLOGO PURO (Solo devuelve la lista de municipios, sin tecnología)
    // URL: GET /api/cobertura/municipios
    @GetMapping("/municipios")
    public List<Municipio> listarMunicipios() {
        return municipioRepository.findAll();
    }

    // 2. COBERTURAS (Devuelve la relación completa: ID cobertura + Municipio + Tecnología)
    // URL: GET /api/cobertura
    @GetMapping({"", "/"})
    public List<Cobertura> listarCoberturas() {
        return coberturaRepository.findAll();
    }

    // 3. FILTRO POR DEPARTAMENTO (Solo municipios)
    @GetMapping("/municipios/dpto/{departamento}")
    public List<Municipio> listarPorDepartamento(@PathVariable String departamento) {
        return municipioRepository.findByDepartamento(departamento);
    }

    // 4. EL METODO "PUENTE" PARA MS-PLANES
    // Este metodo busca el municipio y averigua si tiene tecnología en la otra tabla.
    // URL: GET /api/cobertura/municipios/{id}
    @GetMapping("/municipios/{id}")
    public ResponseEntity<?> obtenerMunicipioConTecnologia(@PathVariable Long id) {
        return municipioRepository.findById(id).map(muni -> {

            // Buscamos en la tabla 'coberturas' si este municipio tiene algo asignado
            String tecnologiaEncontrada = coberturaRepository.findByMunicipio(muni)
                    .map(cob -> cob.getTecnologia().toString())
                    .orElse(null); // Si no hay cobertura, devolvemos null o "SIN_COBERTURA"

            // Creamos un mapa manual para responder con la estructura que ms-planes necesita
            Map<String, Object> respuesta = new HashMap<>();
            respuesta.put("id", muni.getId());
            respuesta.put("nom_mpio", muni.getNombre()); // Asegúrate que en tu entidad sea getNombre()
            respuesta.put("dpto", muni.getDepartamento());
            respuesta.put("tecnologia", tecnologiaEncontrada);

            return ResponseEntity.ok(respuesta);
        }).orElse(ResponseEntity.notFound().build());
    }

    // 5. ASIGNAR TECNOLOGÍA (IMPORTANTE: Sin esto no puedes guardar datos)
    // URL: POST /api/cobertura/asignar/5?tecnologia=FIBRA_OPTICA
    @PostMapping("/asignar/{municipioId}")
    public ResponseEntity<?> asignar(@PathVariable Long municipioId, @RequestParam TipoTecnologia tecnologia) {

        return municipioRepository.findById(municipioId).map(muni -> {
            // Buscamos si ya existe o creamos una nueva cobertura
            Cobertura cobertura = coberturaRepository.findByMunicipio(muni)
                    .orElse(new Cobertura());

            cobertura.setMunicipio(muni);
            cobertura.setTecnologia(tecnologia);

            return ResponseEntity.ok(coberturaRepository.save(cobertura));
        }).orElse(ResponseEntity.notFound().build());
    }
}