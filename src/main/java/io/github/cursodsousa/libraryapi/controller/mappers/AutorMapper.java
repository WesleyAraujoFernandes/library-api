package io.github.cursodsousa.libraryapi.controller.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import io.github.cursodsousa.libraryapi.controller.dto.AutorDTO;
import io.github.cursodsousa.libraryapi.model.Autor;

@Mapper(componentModel = "spring")
public interface AutorMapper {

    @Mapping(source = "nome", target = "nome")
    @Mapping(source = "dataNascimento", target = "dataNascimento")
    @Mapping(source = "nacionalidade", target = "nacionalidade")
    Autor toEntity(AutorDTO dto);

    AutorDTO toDTO(Autor autor);
}
