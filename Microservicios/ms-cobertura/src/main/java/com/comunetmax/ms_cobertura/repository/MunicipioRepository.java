package com.comunetmax.ms_cobertura.repository;

import com.comunetmax.ms_cobertura.model.Municipio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MunicipioRepository extends JpaRepository<Municipio, Long> {

    // Spring Data JPA crea la consulta automáticamente basándose en el nombre
    List<Municipio> findByDepartamento(String departamento);
}