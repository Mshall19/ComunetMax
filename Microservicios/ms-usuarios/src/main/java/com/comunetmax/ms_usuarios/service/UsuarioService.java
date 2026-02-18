package com.comunetmax.ms_usuarios.service;

import com.comunetmax.ms_usuarios.client.PlanCliente;
import com.comunetmax.ms_usuarios.dto.UsuarioDTO;
import com.comunetmax.ms_usuarios.mapper.UsuarioMapper;
import com.comunetmax.ms_usuarios.model.Rol;
import com.comunetmax.ms_usuarios.model.Usuario;
import com.comunetmax.ms_usuarios.repository.UsuarioRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository; // Este es el nombre que usaremos
    private final PlanCliente planCliente;
    private final UsuarioMapper usuarioMapper;

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> obtenerPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    // Protegemos el guardado porque depende de un microservicio externo (Planes)
    @CircuitBreaker(name = "usuariosCB", fallbackMethod = "fallbackGuardarUsuario")
    public Usuario guardar(UsuarioDTO dto) {
        Usuario usuario = usuarioMapper.toEntity(dto);

        if (usuario.getRol() == null) {
            usuario.setRol(Rol.ADMIN);
        }

        if (usuario.getPlanId() != null) {
            // Si el MS de Planes está caído, el Circuit Breaker saltará al fallback
            planCliente.obtenerPlanPorId(usuario.getPlanId());
        }

        return usuarioRepository.save(usuario);
    }

    public Usuario actualizar(Long id, Usuario usuarioDetalles) {
        return usuarioRepository.findById(id)
                .map(usuario -> {
                    if (usuarioDetalles.getPlanId() != null) {
                        planCliente.obtenerPlanPorId(usuarioDetalles.getPlanId());
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

    // Corregido: Ahora usa 'usuarioRepository' en lugar de 'repository'
    @CircuitBreaker(name = "usuariosCB", fallbackMethod = "fallbackObtenerInfo")
    public UsuarioDTO obtenerInformacionAdicional(String email) {
        return usuarioRepository.findByEmail(email)
                .map(usuarioMapper::toDto)
                .orElse(null);
    }

    // FALLBACKS
    public Usuario fallbackGuardarUsuario(UsuarioDTO dto, Throwable e) {
        System.out.println("No se pudo validar el plan. Guardando usuario en modo local.");
        Usuario usuario = usuarioMapper.toEntity(dto);
        return usuarioRepository.save(usuario);
    }

    public UsuarioDTO fallbackObtenerInfo(String email, Throwable e) {
        UsuarioDTO fallbackDto = new UsuarioDTO();
        fallbackDto.setEmail(email);
        fallbackDto.setNombre("Usuario (Modo Resiliencia)");
        fallbackDto.setApellido("Temporal");
        fallbackDto.setRol("INVITADO");
        return fallbackDto;
    }
}