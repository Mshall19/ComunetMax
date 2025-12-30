package com.comunetmax.ms_usuarios.service;

import com.comunetmax.ms_usuarios.dto.PlanDTO;
import com.comunetmax.ms_usuarios.model.Usuario;
import com.comunetmax.ms_usuarios.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RestClient restClient; // Lombok inyectará esto automáticamente gracias al @RequiredArgsConstructor

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> obtenerPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public Usuario guardar(Usuario usuario) {
        // Aquí más adelante encriptaremos la contraseña antes de guardar
        return usuarioRepository.save(usuario);
    }

    public void eliminar(Long id) {
        usuarioRepository.deleteById(id);
    }

    public Optional<Usuario> obtenerPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    // --- METODO PARA COMUNICACIÓN CON MS-PLANES ---
    public PlanDTO obtenerDetallePlan(Long idPlan) {
        // IMPORTANTE: Asegúrate de que el puerto 8082 y la ruta "/planes/"
        // coincidan con cómo tienes configurado tu Controller en ms-planes.
        String url = "http://localhost:8082/planes/" + idPlan;

        try {
            return restClient.get()
                    .uri(url)
                    .retrieve()
                    .body(PlanDTO.class);
        } catch (Exception e) {
            // Si el servicio de planes está caído o no encuentra el plan, devolvemos null o manejamos el error
            System.err.println("Error consultando ms-planes: " + e.getMessage());
            return null;
        }
    }
}