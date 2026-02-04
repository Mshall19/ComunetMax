package com.comunetmax.ms_cobertura.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "municipios_colombia")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Municipio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("nom_mpio")
    @Column(name = "nombre")
    private String nombre;

    @JsonProperty("dpto")
    @Column(name = "departamento")
    private String departamento;

    public Municipio() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }
}