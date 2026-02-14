package com.comunetmax.ms_planes.service;

import com.comunetmax.ms_planes.dto.PlanDTO;
import com.comunetmax.ms_planes.mapper.PlanMapper;
import com.comunetmax.ms_planes.model.PlanInternet;
import com.comunetmax.ms_planes.model.TipoTecnologia;
import com.comunetmax.ms_planes.repository.PlanInternetRepository;
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
}