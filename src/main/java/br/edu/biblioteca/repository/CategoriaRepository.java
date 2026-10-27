package br.edu.biblioteca.repository;

import br.edu.biblioteca.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}
