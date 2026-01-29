package com.comunetmax.ms_usuarios.service;

import com.comunetmax.ms_usuarios.client.PlanCliente;
import com.comunetmax.ms_usuarios.dto.UsuarioDTO;
import com.comunetmax.ms_usuarios.model.Rol;
import com.comunetmax.ms_usuarios.model.Usuario;
import com.comunetmax.ms_usuarios.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor // Lombok inyectará automáticamente los campos 'final'
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PlanCliente planCliente; // <--- Faltaba declarar esta variable como 'final'

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> obtenerPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public Usuario guardar(UsuarioDTO dto) {
        // 1. Convertir DTO a Entidad (Mapeo)
        Usuario usuario = new Usuario();
        usuario.setNombre(dto.getNombre());
        usuario.setApellido(dto.getApellido());
        usuario.setEmail(dto.getEmail());
        usuario.setPassword(dto.getPassword());

        // Asignamos el planId desde el DTO a la entidad
        usuario.setPlanId(dto.getPlanId());

        // Definir Rol (Si no viene, por defecto es ADMIN o USER según prefieras)
        usuario.setRol(dto.getRol() != null ? Rol.valueOf(dto.getRol()) : Rol.ADMIN);

        // 2. Validación de existencia del Plan vía Feign
        // Ahora usuario.getPlanId() ya NO es nulo porque lo asignamos arriba
        if (usuario.getPlanId() != null) {
            try {
                planCliente.obtenerPlanPorId(usuario.getPlanId());
            } catch (Exception e) {
                throw new RuntimeException("El Plan con ID " + usuario.getPlanId() + " no existe.");
            }
        }

        // 3. Guardar la entidad
        return usuarioRepository.save(usuario);
    }

    public Usuario actualizar(Long id, Usuario usuarioDetalles) {
        return usuarioRepository.findById(id)
                .map(usuario -> {
                    // Si el usuario cambia de plan, validamos el nuevo planId
                    if (usuarioDetalles.getPlanId() != null) {
                        try {
                            planCliente.obtenerPlanPorId(usuarioDetalles.getPlanId());
                        } catch (Exception e) {
                            throw new RuntimeException("No se puede actualizar: El nuevo Plan ID no existe.");
                        }
                    }
                    usuario.setNombre(usuarioDetalles.getNombre());
                    usuario.setApellido(usuarioDetalles.getApellido());
                    usuario.setEmail(usuarioDetalles.getEmail());
                    usuario.setRol(usuarioDetalles.getRol());
                    usuario.setPlanId(usuarioDetalles.getPlanId());
                    return usuarioRepository.save(usuario);
                })
                .orElseThrow(() -> new RuntimeException("No se encontró el usuario con ID: " + id));
    }

    public void eliminar(Long id) {
        usuarioRepository.deleteById(id);
    }
}