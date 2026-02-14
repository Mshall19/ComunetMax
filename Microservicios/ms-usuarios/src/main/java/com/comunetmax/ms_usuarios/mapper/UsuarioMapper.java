package com.comunetmax.ms_usuarios.mapper;

import com.comunetmax.ms_usuarios.dto.UsuarioDTO;
import com.comunetmax.ms_usuarios.model.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring") // Esto permite usar @Autowired
public interface UsuarioMapper {

    // Convierte de Entidad a DTO (para enviar al cliente)
    UsuarioDTO toDto(Usuario usuario);

    // Convierte de DTO a Entidad (para guardar en la BD)
    @Mapping(target = "id", ignore = true) // El ID suele ser autoincremental
    Usuario toEntity(UsuarioDTO usuarioDto);
}

