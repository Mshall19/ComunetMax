package com.comunetmax.ms_cobertura.controller;

import com.comunetmax.ms_cobertura.dto.MunicipioDTO;
import com.comunetmax.ms_cobertura.service.MunicipioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/municipios") // Mantenemos tu ruta original
@RequiredArgsConstructor
public class MunicipioController {

    private final MunicipioService municipioService;

    @GetMapping
    public List<MunicipioDTO> listarTodos() {
        return municipioService.listarTodos();
    }
}