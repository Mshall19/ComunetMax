package com.comunetmax.ms_empresas.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "empresas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // El NIT debe ser único en el sistema
    @Column(nullable = false, unique = true)
    private String nit;

    @Column(name = "razon_social", nullable = false)
    private String razonSocial;

    private String direccion;

    private String telefono;

    // Aquí guardamos el ID del usuario representante (Relación débil)
    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId;

    private Boolean estado = true;
}