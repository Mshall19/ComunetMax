package com.comunetmax.ms_planes.repository;

import com.comunetmax.ms_planes.model.PlanInternet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanInternetRepository extends JpaRepository<PlanInternet, Long> {
    // Método mágico: Spring crea el SQL solo por el nombre del método
    List<PlanInternet> findByActivoTrue();
}