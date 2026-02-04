package com.comunetmax.ms_cobertura.repository;

import com.comunetmax.ms_cobertura.model.Municipio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MunicipioRepository extends JpaRepository<Municipio, Long> {
    // Aquí puedes buscar municipios por nombre si lo necesitas después
}