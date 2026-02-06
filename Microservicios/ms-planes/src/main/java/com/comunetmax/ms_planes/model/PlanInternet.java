package com.comunetmax.ms_planes.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "planes")
@Data // Lombok: Crea Getters, Setters, ToString automáticamente
@NoArgsConstructor
@AllArgsConstructor
public class PlanInternet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre; // Ej: "Fibra 300MB"

    @NotNull
    @Positive
    private Integer velocidadMb; // Ej: 300

    @Enumerated(EnumType.STRING)
    private TipoTecnologia tecnologia;

    @NotNull
    @Positive
    private Double precio;

    private String descripcion;

    private String tipo; // "HOGAR" o "EMPRESA"

    private Boolean activo = true;
}