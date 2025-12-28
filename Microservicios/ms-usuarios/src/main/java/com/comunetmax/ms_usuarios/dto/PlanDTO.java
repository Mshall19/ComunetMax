package com.comunetmax.ms_usuarios.dto;

import lombok.Data;

@Data
public class PlanDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private Double precio;
}