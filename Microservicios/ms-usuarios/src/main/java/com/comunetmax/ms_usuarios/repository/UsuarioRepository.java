package com.comunetmax.ms_usuarios.repository;

import com.comunetmax.ms_usuarios.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Metodo clave para buscar usuario por correo (Login)
    Optional<Usuario> findByEmail(String email);
}