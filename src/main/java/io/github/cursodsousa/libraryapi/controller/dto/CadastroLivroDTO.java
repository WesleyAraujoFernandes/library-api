package io.github.cursodsousa.libraryapi.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import org.hibernate.validator.constraints.ISBN;

import io.github.cursodsousa.libraryapi.model.GeneroLivro;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

public record CadastroLivroDTO(
        @NotBlank(message = "campo obrigatório") @ISBN(message = "ISBN Inválido") String isbn,
        @NotBlank(message = "campo obrigatório") String titulo,
        @NotNull @Past(message = "não pode ser uma data futura") LocalDate dataPublicacao,
        GeneroLivro genero,
        BigDecimal preco,
        @NotNull(message = "campo obrigatório") UUID idAutor) {

}
