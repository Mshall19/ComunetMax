package com.comunetmax.ms_planes.dto;

import com.comunetmax.ms_planes.model.TipoTecnologia;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanDTO {
    private Long id;
    private String nombre;
    private Integer velocidadMb;
    private TipoTecnologia tecnologia; // Asegúrate de importar el Enum también aquí
    private Double precio;
    private String descripcion;
    private String tipo;
    private Boolean activo;
}
