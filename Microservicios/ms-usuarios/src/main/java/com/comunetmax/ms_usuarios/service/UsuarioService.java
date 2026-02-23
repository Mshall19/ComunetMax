package com.comunetmax.ms_usuarios.service;

import com.comunetmax.ms_usuarios.client.PlanCliente;
import com.comunetmax.ms_usuarios.config.JwtUtil;
import com.comunetmax.ms_usuarios.dto.UsuarioDTO;
import com.comunetmax.ms_usuarios.mapper.UsuarioMapper;
import com.comunetmax.ms_usuarios.model.Rol;
import com.comunetmax.ms_usuarios.model.Usuario;
import com.comunetmax.ms_usuarios.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PlanCliente planCliente;
    private final UsuarioMapper usuarioMapper;
    private final JwtUtil jwtUtil; // <--- Inyectado aquí


    public Map<String, String> login(Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");

        // 1. Buscamos al usuario real en la base de datos usando el repositorio
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // 2. Comparamos la contraseña (por ahora texto plano, luego le pondremos BCrypt)
        if (usuario.getPassword().equals(password)) {
            String token = jwtUtil.generateToken(usuario.getEmail());
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            response.put("nombre", usuario.getNombre());
            return response;
        } else {
            throw new RuntimeException("Credenciales incorrectas");
        }
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> obtenerPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public Usuario guardar(UsuarioDTO dto) {
        Usuario usuario = usuarioMapper.toEntity(dto);
        if (usuario.getRol() == null) {
            usuario.setRol(Rol.ADMIN);
        }
        validarPlan(usuario.getPlanId());
        return usuarioRepository.save(usuario);
    }

    public Usuario actualizar(Long id, Usuario usuarioDetalles) {
        return usuarioRepository.findById(id)
                .map(usuario -> {
                    validarPlan(usuarioDetalles.getPlanId());
                    usuario.setNombre(usuarioDetalles.getNombre());
                    usuario.setApellido(usuarioDetalles.getApellido());
                    usuario.setEmail(usuarioDetalles.getEmail());
                    usuario.setRol(usuarioDetalles.getRol());
                    usuario.setPlanId(usuarioDetalles.getPlanId());
                    return usuarioRepository.save(usuario);
                })
                .orElseThrow(() -> new RuntimeException("No se encontró el usuario con ID: " + id));
    }

    // Metodo privado para no repetir código de validación de Plan
    private void validarPlan(Long planId) {
        if (planId != null) {
            try {
                planCliente.obtenerPlanPorId(planId);
            } catch (Exception e) {
                throw new RuntimeException("El Plan con ID " + planId + " no existe.");
            }
        }
    }

    public void eliminar(Long id) {
        usuarioRepository.deleteById(id);
    }
}