package br.edu.biblioteca.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Emprestimo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "leitor_id")
    private Leitor leitor;

    @ManyToOne(optional = false)
    @JoinColumn(name = "livro_id")
    private Livro livro;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dataEmprestimo;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dataPrevistaDevolucao;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dataDevolucao;

    @Enumerated(EnumType.STRING)
    private StatusEmprestimo status;
}
