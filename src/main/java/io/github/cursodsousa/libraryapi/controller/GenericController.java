package io.github.cursodsousa.libraryapi.controller;

import java.net.URI;
import java.util.UUID;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public interface GenericController {
    // Para criar um método dentro de uma interface, pode-se usar o modificador de
    // acesso "default"
    // O método default pode ser utilizado para fornecer uma implementação padrão
    // para os métodos da interface
    default URI gerarHeaderLocation(UUID id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }
}
