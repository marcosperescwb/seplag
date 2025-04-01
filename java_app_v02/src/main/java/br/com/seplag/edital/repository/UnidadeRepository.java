package br.com.seplag.edital.repository;

import br.com.seplag.edital.model.Unidade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UnidadeRepository extends JpaRepository<Unidade, Integer> {
}