package br.com.seplag.edital.repository;

import br.com.seplag.edital.model.Lotacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LotacaoRepository extends JpaRepository<Lotacao, Integer> {
}