package com.comunetmax.ms_cobertura.controller;

import com.comunetmax.ms_cobertura.model.Municipio;
import com.comunetmax.ms_cobertura.repository.MunicipioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/municipios")
@RequiredArgsConstructor
public class MunicipioController {

    private final MunicipioRepository municipioRepository;

    @GetMapping
    public List<Municipio> listarTodos() {
        return municipioRepository.findAll();
    }
}