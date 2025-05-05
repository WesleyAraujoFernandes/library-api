package io.github.cursodsousa.libraryapi.controller.dto;

import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UsuarioDTO(
    @NotBlank(message = "campo obrigatório")
    String login, 
    @Email(message = "inválido") 
    @NotBlank(message = "campo obrigatório")
    String email, 
    @NotBlank(message = "campo obrigatório")
    String senha, 
    List<String> roles) {

}
