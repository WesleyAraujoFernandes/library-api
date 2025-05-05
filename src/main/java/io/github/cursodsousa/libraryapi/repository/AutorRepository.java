package io.github.cursodsousa.libraryapi.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.cursodsousa.libraryapi.model.Autor;

public interface AutorRepository extends JpaRepository<Autor, UUID> {
    List<Autor> findByNomeContainingIgnoreCase(String nome);

    List<Autor> findByNacionalidadeContainingIgnoreCase(String nacionalidade);

    List<Autor> findByNomeContainingIgnoreCaseAndNacionalidadeContainingIgnoreCase(String nome, String nacionalidade);

    Optional<Autor> findByNomeAndDataNascimentoAndNacionalidade(String nome, LocalDate dataNascimento, String nacionalidade);
}
