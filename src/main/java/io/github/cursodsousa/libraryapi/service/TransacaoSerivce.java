package io.github.cursodsousa.libraryapi.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.cursodsousa.libraryapi.model.Autor;
import io.github.cursodsousa.libraryapi.model.GeneroLivro;
import io.github.cursodsousa.libraryapi.model.Livro;
import io.github.cursodsousa.libraryapi.repository.AutorRepository;
import io.github.cursodsousa.libraryapi.repository.LivroRepository;

@Service
public class TransacaoSerivce {
    @Autowired
    private AutorRepository autorRepository;
    @Autowired
    private LivroRepository livroRepository;

    // Todos os métodos de transação devem ser public e não podem ser
    // chamados de dentro da própria classe.

    @Transactional
    public void atualizacaoSemAtualizar() {
        var livro = livroRepository
                .findById(UUID.fromString("675b9cd6-28ba-40df-b8ef-954335ea4b8c"))
                .orElse(null);

        livro.setDataPublicacao(LocalDate.of(2024, 6, 1)); // Atualiza a data de publicação do Livro da Francisca.

        // livroRepository.save(livro); // Salva o livro no banco de dados
        // Não é necessário chamar o save, pois o livro já está gerenciado pelo
        // EntityManager.

    }

    @Transactional // Para garantir que a transação seja feita corretamente. Anotação do Spring.
    public void executar() {
        // salva o autor
        Autor autor = new Autor();
        autor.setNome("Teste Francisco");
        autor.setNacionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(1951, 1, 31));

        // saveAndFlush -> Salva o autor no banco de dados e força a execução imediata
        // da operação.
        // Isso é útil quando você precisa garantir que a operação seja realizada
        // imediatamente,
        // por exemplo, antes de realizar outra operação que dependa do resultado da
        // primeira.
        // O flush força a sincronização do estado atual do contexto de persistência com
        // o banco de dados.
        // Isso significa que todas as operações pendentes no contexto de persistência
        // são enviadas para o banco de dados.
        // A melhor prática é usar apenas o save, que é mais eficiente e não força a
        // sincronização imediata.
        autorRepository.saveAndFlush(autor); // Salvar o autor no banco de dados

        // salva o livro
        Livro livro = new Livro();
        livro.setIsbn("90887-84874");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("Teste Livro do Francisco");
        livro.setDataPublicacao(LocalDate.of(1980, 1, 2));

        livro.setAutor(autor); // Associar o autor ao livro
        livroRepository.saveAndFlush(livro); // Salvar o livro no banco de dados

        if (autor.getNome().equals("Teste Francisco")) {
            throw new RuntimeException("Erro ao salvar o autor! Rollback!"); // Simulando um erro para testar o rollback
        }

    }
}
