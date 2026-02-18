package com.comunetmax.ms_planes.service;

import com.comunetmax.ms_planes.dto.CoberturaDTO;
import com.comunetmax.ms_planes.dto.PlanDTO;
import com.comunetmax.ms_planes.mapper.PlanMapper;
import com.comunetmax.ms_planes.model.PlanInternet;
import com.comunetmax.ms_planes.model.TipoTecnologia;
import com.comunetmax.ms_planes.repository.PlanInternetRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlanInternetService {

    private final PlanInternetRepository repository;
    private final PlanMapper planMapper; // Inyectamos el mapper aunque no lo forcemos en el retorno
    private final com.comunetmax.ms_planes.client.CoberturaClient coberturaClient;


    public List<PlanDTO> listarTodos() {
        // ...y aquí es donde fluye la corriente (usando el mapper)
        return repository.findAll().stream()
                .map(planMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<PlanInternet> listarActivos() {
        return repository.findByActivoTrue();
    }

    public List<PlanInternet> listarPorTecnologia(TipoTecnologia tecnologia) {
        return repository.findByTecnologiaAndActivoTrue(tecnologia);
    }

    public PlanInternet guardar(PlanInternet plan) {
        PlanDTO dto = planMapper.toDto(plan);
        return repository.save(plan);
    }

    public Optional<PlanInternet> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }

    @CircuitBreaker(name = "coberturaCB", fallbackMethod = "fallbackObtenerCobertura")
    public CoberturaDTO obtenerDetalleCobertura(Long municipioId) {
        return coberturaClient.obtenerCoberturaPorMunicipio(municipioId);
    }

    public CoberturaDTO fallbackObtenerCobertura(Long municipioId, Throwable e) {
        System.out.println("Servicio de Cobertura no disponible. Error: " + e.getMessage());

        CoberturaDTO dto = new CoberturaDTO();
        dto.setId(municipioId);

        dto.setNombreMunicipo("Información temporalmente no disponible");

        return dto;
    }
}