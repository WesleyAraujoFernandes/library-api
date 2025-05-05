package io.github.cursodsousa.libraryapi.controller.mappers;

import org.mapstruct.Mapper;

import io.github.cursodsousa.libraryapi.controller.dto.UsuarioDTO;
import io.github.cursodsousa.libraryapi.model.Usuario;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
    Usuario toEntity(UsuarioDTO dto);
}
