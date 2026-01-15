package com.comunetmax.ms_empresas.service;

import com.comunetmax.ms_empresas.client.UsuarioCliente; // Importa tu cliente Feign
import com.comunetmax.ms_empresas.model.Empresa;
import com.comunetmax.ms_empresas.repository.EmpresaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmpresaService {

    private final EmpresaRepository empresaRepository;
    private final UsuarioCliente usuarioCliente; // 1. Inyectamos el cliente Feign

    public List<Empresa> listarTodas() {
        return empresaRepository.findAll();
    }

    public Optional<Empresa> obtenerPorId(Long id) {
        return empresaRepository.findById(id);
    }

    // 2. Modificamos el metodo guardar para que valide
    public Empresa guardar(Empresa empresa) {
        // Validación: ¿Viene el ID del usuario en la petición?
        if (empresa.getUsuarioId() == null) {
            throw new RuntimeException("Error: El ID del usuario es obligatorio para crear una empresa.");
        }

        try {
            // 3. Llamada al microservicio de Usuarios vía Feign
            usuarioCliente.obtenerUsuarioPorId(empresa.getUsuarioId());
        } catch (Exception e) {
            // Si el usuario no existe o el microservicio está caído, lanzará este error
            throw new RuntimeException("Error: El Usuario con ID " + empresa.getUsuarioId() + " no existe en el sistema.");
        }

        return empresaRepository.save(empresa);
    }

    public void eliminar(Long id) {
        empresaRepository.deleteById(id);
    }
}