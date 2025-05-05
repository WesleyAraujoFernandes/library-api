package io.github.cursodsousa.libraryapi.repository.specs;

import org.springframework.data.jpa.domain.Specification;

import io.github.cursodsousa.libraryapi.model.GeneroLivro;
import io.github.cursodsousa.libraryapi.model.Livro;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;

public class LivroSpecs {

    public static Specification<Livro> isbnEqual(String isbn) {
        return (root, query, cb) -> cb.equal(root.get("isbn"), isbn);
    }

    public static Specification<Livro> tituloLike(String titulo) {
        return (root, query, cb) -> 
            cb.like(cb.upper(root.get("titulo")), "%" + titulo.toUpperCase() + "%");
    }
    //root.get("genero") = nome da propriedade na classe
    public static Specification<Livro> generoEqual(GeneroLivro genero) {
        return (root, query, cb) -> 
            cb.equal(root.get("genero"), genero);
    }

    public static Specification<Livro> anoPublicacaoEqual(Integer anoPublicacao) {
        // and to_char(ano_publicacao, 'YYYY') = :anoPublicacao
        return (root, query, cb) -> 
            cb.equal(cb.function("to_char", String.class, 
                root.get("dataPublicacao"), cb.literal("YYYY")), anoPublicacao.toString());
            
    }

    public static Specification<Livro> nomeAutorLike(String nome) {
        // root representa a projeção, neste caso, o livro
        return (root, query, cb) -> {
            Join<Object, Object> joinAutor = root.join("autor", JoinType.INNER);
            return cb.like(cb.upper(joinAutor.get("nome")), "%" + nome.toUpperCase() + "%");
            //return cb.like(cb.upper(root.get("autor").get("nome")), "%" + nome.toUpperCase() + "%");
        };
    }
}
