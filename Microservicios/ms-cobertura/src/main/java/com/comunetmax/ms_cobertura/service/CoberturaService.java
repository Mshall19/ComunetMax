package com.comunetmax.ms_cobertura.service;

import com.comunetmax.ms_cobertura.dto.CoberturaDTO;
import com.comunetmax.ms_cobertura.mapper.CoberturaMapper;
import com.comunetmax.ms_cobertura.model.Cobertura;
import com.comunetmax.ms_cobertura.model.Municipio;
import com.comunetmax.ms_cobertura.model.TipoTecnologia;
import com.comunetmax.ms_cobertura.repository.CoberturaRepository;
import com.comunetmax.ms_cobertura.repository.MunicipioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CoberturaService {
    private final CoberturaRepository coberturaRepository;
    private final MunicipioRepository municipioRepository;
    private final CoberturaMapper mapper;

    public List<CoberturaDTO> listarTodas() {
        return coberturaRepository.findAll().stream()
                .map(mapper::toCoberturaDto)
                .collect(Collectors.toList());
    }

    public CoberturaDTO guardar(CoberturaDTO dto, Long municipioId) {
        Municipio municipio = municipioRepository.findById(municipioId)
                .orElseThrow(() -> new RuntimeException("Municipio no encontrado"));

        Cobertura entidad = mapper.toCoberturaEntity(dto);
        entidad.setMunicipio(municipio);

        return mapper.toCoberturaDto(coberturaRepository.save(entidad));
    }

    // Metodo para eliminar cobertura
    public void eliminar(Long id) {
        if (!coberturaRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar: Cobertura no encontrada con ID: " + id);
        }
        coberturaRepository.deleteById(id);
    }

    public CoberturaDTO obtenerPorMunicipioId(Long municipioId) {
        Municipio muni = municipioRepository.findById(municipioId)
                .orElseThrow(() -> new RuntimeException("Municipio no encontrado"));

        return coberturaRepository.findByMunicipio(muni)
                .map(mapper::toCoberturaDto)
                .orElse(null); // O podrías devolver un DTO vacío
    }

    public CoberturaDTO asignarTecnologia(Long municipioId, TipoTecnologia tecnologia) {
        Municipio muni = municipioRepository.findById(municipioId)
                .orElseThrow(() -> new RuntimeException("Municipio no encontrado"));

        Cobertura cobertura = coberturaRepository.findByMunicipio(muni)
                .orElse(new Cobertura());

        cobertura.setMunicipio(muni);
        cobertura.setTecnologia(tecnologia);

        return mapper.toCoberturaDto(coberturaRepository.save(cobertura));
    }
}