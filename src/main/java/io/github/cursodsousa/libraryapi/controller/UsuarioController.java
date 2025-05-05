package io.github.cursodsousa.libraryapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.github.cursodsousa.libraryapi.controller.dto.UsuarioDTO;
import io.github.cursodsousa.libraryapi.controller.mappers.UsuarioMapper;
import io.github.cursodsousa.libraryapi.service.UsuarioService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("usuarios")
@RequiredArgsConstructor
public class UsuarioController {
    
    private final UsuarioService service;
    private final UsuarioMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void salvar(@RequestBody UsuarioDTO dto) {
        var usuario = mapper.toEntity(dto);
        service.salvar(usuario);
    }
}
