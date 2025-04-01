package br.com.seplag.edital.repository;

import br.com.seplag.edital.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {
}