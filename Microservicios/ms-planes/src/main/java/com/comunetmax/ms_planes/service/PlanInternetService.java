package com.comunetmax.ms_planes.service;

import com.comunetmax.ms_planes.model.PlanInternet;
import com.comunetmax.ms_planes.model.TipoTecnologia;
import com.comunetmax.ms_planes.repository.PlanInternetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;

import java.util.List;

@Service
@RequiredArgsConstructor // Inyecta el repositorio automáticamente
public class PlanInternetService {

    private final PlanInternetRepository repository;

    public List<PlanInternet> listarTodos() {
        return repository.findAll();
    }

    public List<PlanInternet> listarActivos() {
        return repository.findByActivoTrue();
    }

    public List<PlanInternet> listarPorTecnologia(TipoTecnologia tecnologia) {
        return repository.findByTecnologiaAndActivoTrue(tecnologia);
    }

    public PlanInternet guardar(PlanInternet plan) {
        return repository.save(plan);
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }

    public Optional<PlanInternet> buscarPorId(Long id) {
        return repository.findById(id);
    }

}