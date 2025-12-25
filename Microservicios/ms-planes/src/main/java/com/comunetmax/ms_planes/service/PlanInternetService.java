package com.comunetmax.ms_planes.service;

import com.comunetmax.ms_planes.model.PlanInternet;
import com.comunetmax.ms_planes.repository.PlanInternetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    public PlanInternet guardar(PlanInternet plan) {
        return repository.save(plan);
    }

    // Método simple para poblar base de datos si está vacía (opcional)
    public void crearDatosPrueba() {
        if (repository.count() == 0) {
            repository.save(new PlanInternet(null, "Fibra Básica", 100, 50000.0, "Ideal para estudiantes", "HOGAR", true));
            repository.save(new PlanInternet(null, "Fibra Gamer", 300, 80000.0, "Alta velocidad simétrica", "HOGAR", true));
            repository.save(new PlanInternet(null, "Empresarial Pro", 600, 150000.0, "IP Fija incluida", "EMPRESA", true));
        }
    }
}