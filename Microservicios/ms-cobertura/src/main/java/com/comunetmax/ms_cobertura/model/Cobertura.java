package com.comunetmax.ms_cobertura.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "coberturas")
@Data
public class Cobertura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "municipio_id")
    @JsonIgnoreProperties({"id"})
    private Municipio municipio;

    @Enumerated(EnumType.STRING)
    private TipoTecnologia tecnologia;
}