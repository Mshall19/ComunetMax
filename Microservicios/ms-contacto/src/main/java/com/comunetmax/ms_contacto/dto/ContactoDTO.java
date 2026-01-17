package com.comunetmax.ms_contacto.dto;

import lombok.Data;

/**
 * DTO para capturar los datos del formulario de contacto.
 * Cumple con la norma de no usar entidades de base de datos.
 */
@Data
public class ContactoDTO {
    private String nombre;
    private String apellidos;
    private String telefono;
    private String correoElectronico;
    private String mensaje;
}