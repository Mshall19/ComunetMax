package com.comunetmax.ms_usuarios.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password; // En el futuro la encriptaremos

    @Column(nullable = false)
    private String rol; // ADMIN, EMPLEADO, SUPER_ADMIN

    @Column(name = "empresa_id")
    private Long empresaId; // Relación con ms-empresas

    private Boolean activo = true;
}