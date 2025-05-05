package io.github.cursodsousa.libraryapi.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "livro", schema = "public") // Caso tenha um schema diferente do padrão
@Data
@ToString(exclude = "autor") // Para não gerar o loop infinito no toString
@EntityListeners(AuditingEntityListener.class) // Para adicionar os campos de auditoria
public class Livro {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "titulo", length = 150, nullable = false)
    private String titulo;
    @Column(name = "isbn", length = 20, nullable = true)
    private String isbn;
    @Column(name = "data_publicacao", nullable = false)
    private LocalDate dataPublicacao;
    @Enumerated(EnumType.STRING) // Para garantir que o valor seja o correto
    @Column(name = "genero", length = 30, nullable = false)
    private GeneroLivro genero;
    @Column(name = "preco", precision = 18, scale = 2)
    private BigDecimal preco;

    @ManyToOne(fetch = FetchType.LAZY) // Para não carregar os livros do autor quando carregar o livro
    @JoinColumn(name = "id_autor")
    private Autor autor; // Relacionamento com a classe Autor (não é necessário o @ManyToOne aqui, pois
                         // não é uma
    // entidade JPA, mas sim uma classe de domínio)
    @CreatedDate
    @Column(name = "data_cadastro")
    private LocalDateTime dataCadastro;

    @LastModifiedDate
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

}
