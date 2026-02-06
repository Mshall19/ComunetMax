package com.comunetmax.ms_cobertura.repository;

import com.comunetmax.ms_cobertura.model.Cobertura;
import com.comunetmax.ms_cobertura.model.Municipio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CoberturaRepository extends JpaRepository<Cobertura, Long> {

    // Esto permite buscar una cobertura usando el objeto Municipio completo
    Optional<Cobertura> findByMunicipio(Municipio municipio);
}