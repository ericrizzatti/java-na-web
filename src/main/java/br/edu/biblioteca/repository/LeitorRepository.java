package br.edu.biblioteca.repository;

import br.edu.biblioteca.model.Leitor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeitorRepository extends JpaRepository<Leitor, Long> {
}
