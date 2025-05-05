package io.github.cursodsousa.libraryapi.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import io.github.cursodsousa.libraryapi.model.Autor;
import io.github.cursodsousa.libraryapi.model.Usuario;
import io.github.cursodsousa.libraryapi.repository.AutorRepository;
import io.github.cursodsousa.libraryapi.repository.LivroRepository;
import io.github.cursodsousa.libraryapi.security.SecurityService;
import io.github.cursodsousa.libraryapi.validator.AutorValidator;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AutorService {

    private final AutorRepository repository;
    private final AutorValidator validator;
    private final LivroRepository livroRepository;
    private final SecurityService securityService;

    public Autor salvar(Autor autor) {
        validator.validar(autor);
        Usuario usuario = securityService.obterUsuarioLogado();
        autor.setUsuario(usuario);
        return repository.save(autor);
    }

    public void atualizar(Autor autor) {
        if (autor.getId() == null) {
            throw new IllegalArgumentException(
                    "Para atualizar, é necessário que o autor já esteja salvo na base de dados.");
        }
        validator.validar(autor);
        repository.save(autor);
    }

    public Optional<Autor> obterPorId(UUID id) {
        return repository.findById(id);
    }

    public void deletar(UUID id) {
        repository.deleteById(id);
    }

    public void deletar(Autor autor) {
        if (possuiLivro(autor)) {
            throw new IllegalArgumentException("Não é permitido excluir o autor, pois ele possui livros cadastrados.");
        }
        repository.delete(autor);
    }

    public List<Autor> pesquisa(String nome, String nacionalidade) {
        if (nome != null && nacionalidade != null) {
            return repository.findByNomeContainingIgnoreCaseAndNacionalidadeContainingIgnoreCase(nome, nacionalidade);
        }

        if (nome != null) {
            return repository.findByNomeContainingIgnoreCase(nome);
        }
        if (nacionalidade != null) {
            return repository.findByNacionalidadeContainingIgnoreCase(nacionalidade);
        }
        return repository.findAll();
    }

    // Exemplo de uso do ExampleMatcher para pesquisa por exemplo
    // com ignore case e ignore null values
    // https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html#jpa.query-methods.using-example
    public List<Autor> pesquisaByExample(String nome, String nacionalidade) {
        var autor = new Autor();
        autor.setNome(nome);
        autor.setNacionalidade(nacionalidade);
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnorePaths("id", "dataNascimento", "dataCadastro")
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Autor> autorExample = Example.of(autor, matcher);
        return repository.findAll(autorExample);
    }

    public boolean possuiLivro(Autor autor) {
        // return autor.getLivros() != null && !autor.getLivros().isEmpty();
        return this.livroRepository.existsByAutor(autor);
    }
}
