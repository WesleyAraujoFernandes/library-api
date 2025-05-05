package io.github.cursodsousa.libraryapi.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import io.github.cursodsousa.libraryapi.model.Autor;
import io.github.cursodsousa.libraryapi.model.GeneroLivro;
import io.github.cursodsousa.libraryapi.model.Livro;

/**
 * @see LivroRepositoryTest
 */
public interface LivroRepository extends JpaRepository<Livro, UUID>, JpaSpecificationExecutor<Livro> {
    Page<Livro> findByAutor(Autor autor, Pageable Pageable);


    // QUERY METHODS
    // SELECT * FROM livro WHERE id_autor = id
    List<Livro> findByAutor(Autor autor);

    List<Livro> findByTitulo(String titulo);

    Optional<Livro> findByIsbn(String isbn);

    // SELECT * FROM livro WHERE titulo = ? AND preco = ?
    List<Livro> findByTituloAndPreco(String titulo, BigDecimal preco);

    // SELECT * FROM livro WHERE titulo = ? OR isbn = ?
    List<Livro> findByTituloOrIsbnOrderByTitulo(String titulo, String isbn);

    // SELECT * FROM livro WHERE data_publicacao BETWEEN ? AND ?
    List<Livro> findByDataPublicacaoBetween(LocalDate inicio, LocalDate fim);

    // JPQL -> referência as entidades e as propriedades das entidades
    // SELECT l.* FROM livro as l order by l.titulo
    @Query("SELECT l FROM Livro as l order by l.titulo, l.preco")
    List<Livro> listarTodosOrdenadoPorTituloAndPreco();

    @Query("select a from Livro l join l.autor a")
    List<Autor> listarAutoresDosLivros();

    @Query("select distinct l.titulo from Livro l")
    List<String> listarNomesDiferentesLivros();

    @Query("""
                    select l.genero
                    from Livro l
                    join l.autor a
                    where a.nacionalidade = 'Brasileira'
                    order by l.genero
            """)
    List<String> listarGenerosAutoresBrasileiros();

    // named parameters
    @Query("select l from Livro l where l.genero = :genero order by :paramOrdenacao")
    List<Livro> findByGenero(
            @Param("genero") GeneroLivro generoLivro,
            @Param("paramOrdenacao") String nomePropriedade);

    // positional parameters
    @Query("select l from Livro l where l.genero = ?1 order by ?2")
    List<Livro> findByGeneroPositionalParameters(GeneroLivro generoLivro, String nomePropriedade);

    @Modifying // Para operações de escrita (insert, update, delete)
    @Transactional // Para garantir que a transação seja gerenciada pelo Spring
    @Query("delete from Livro where genero = ?1")
    void deleteByGenero(GeneroLivro generoLivro);

    @Modifying // Para operações de escrita (insert, update, delete)
    @Transactional // Para garantir que a transação seja gerenciada pelo Spring
    @Query("update Livro set dataPublicacao = ?1")
    void updateDataPublicacao(LocalDate novaData);

    /*
     * @Modifying // Para operações de escrita (insert, update, delete)
     * 
     * @Transactional // Para garantir que a transação seja gerenciada pelo Spring
     * 
     * @Query("update Livro set dataPublicacao = :dataPublicacao where id = :id")
     * void updateDataPublicacao(
     * 
     * @Param("dataPublicacao") LocalDate dataPublicacao,
     * 
     * @Param("id") UUID idLivro); // Não é necessário o @Modifying, pois o Spring
     * Data JPA já faz isso automaticamente
     */
    boolean existsByAutor(Autor autor);

}
