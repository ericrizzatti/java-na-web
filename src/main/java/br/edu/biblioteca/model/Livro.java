package br.edu.biblioteca.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private String isbn;

    private Integer anoPublicacao;

    private Integer quantidadeExemplares;

    @ManyToOne(optional = false)
    @JoinColumn(name = "autor_id")
    private Autor autor;

    @ManyToOne(optional = false)
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;
}
