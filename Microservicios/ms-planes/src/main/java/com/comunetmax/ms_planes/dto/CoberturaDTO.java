package com.comunetmax.ms_planes.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CoberturaDTO { // <--- ¡VERIFICA QUE DIGA ESTO, NO MunicipioDTO!

    private Long id;

    @JsonProperty("nom_mpio")
    private String nombreMunicipo;

    private String tecnologia;
}