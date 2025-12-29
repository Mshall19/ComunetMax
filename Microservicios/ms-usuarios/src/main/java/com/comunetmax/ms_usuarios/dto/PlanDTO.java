package com.comunetmax.ms_usuarios.dto;

import lombok.Data;

@Data
public class PlanDTO {
    private Long id;
    private String nombre;
    private Double precio;
    private Integer velocidadMb;
    private Boolean activo;
}