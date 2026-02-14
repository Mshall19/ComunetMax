package com.comunetmax.ms_usuarios.service;

import com.comunetmax.ms_usuarios.client.PlanCliente;
import com.comunetmax.ms_usuarios.dto.UsuarioDTO;
import com.comunetmax.ms_usuarios.mapper.UsuarioMapper;
import com.comunetmax.ms_usuarios.model.Rol;
import com.comunetmax.ms_usuarios.model.Usuario;
import com.comunetmax.ms_usuarios.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PlanCliente planCliente;
    private final UsuarioMapper usuarioMapper; // <--- 1. Inyectamos el nuevo Mapper

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> obtenerPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public Usuario guardar(UsuarioDTO dto) {
        // 2. ¡MAGIA! Reemplazamos todos los setNombre, setApellido, etc. con una sola línea
        Usuario usuario = usuarioMapper.toEntity(dto);

        // 3. Si el rol no venía en el DTO, MapStruct lo deja nulo, así que aplicamos el default:
        if (usuario.getRol() == null) {
            usuario.setRol(Rol.ADMIN);
        }

        // 4. Validación de existencia del Plan vía Feign
        if (usuario.getPlanId() != null) {
            try {
                planCliente.obtenerPlanPorId(usuario.getPlanId());
            } catch (Exception e) {
                throw new RuntimeException("El Plan con ID " + usuario.getPlanId() + " no existe.");
            }
        }

        return usuarioRepository.save(usuario);
    }

    public Usuario actualizar(Long id, Usuario usuarioDetalles) {
        return usuarioRepository.findById(id)
                .map(usuario -> {
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