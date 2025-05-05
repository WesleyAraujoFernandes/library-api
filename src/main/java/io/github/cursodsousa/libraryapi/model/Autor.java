package io.github.cursodsousa.libraryapi.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import jakarta.persistence.*;

@Entity
@Table(name = "autor", schema = "public") // Caso tenha um schema diferente do padrão
// @Table(name = "autor") // Caso não tenha um schema diferente do padrão
// @Table(name = "autor", schema = "library") // Caso tenha um schema diferente
// do padrão
@Getter
@Setter
@ToString(exclude = { "livros" }) // Para não gerar o loop infinito no toString
@EntityListeners(AuditingEntityListener.class) // Para auditar as datas de
// criação e atualização
public class Autor {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "nome", length = 100, nullable = false)
    private String nome;

    @Column(name = "data_nascimento", nullable = false)
    // @Temporal(TemporalType.DATE) // Para garantir que o formato da data seja o
    // correto
    private LocalDate dataNascimento;

    @Column(name = "nacionalidade", length = 50, nullable = false)
    // @Enumerated(EnumType.STRING) // Para garantir que o valor seja o correto
    private String nacionalidade;

    /*
     * Padrão de relacionamentos ToMany é lazy, ou seja, não carrega os livros
     */
    @OneToMany(mappedBy = "autor", fetch = FetchType.LAZY
    // ,cascade = CascadeType.ALL
    ) // mappedBy = nome do atributo na
      // classe Livro
    private List<Livro> livros;

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
