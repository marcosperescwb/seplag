package br.com.seplag.edital.repository;

import br.com.seplag.edital.model.UnidadeEndereco;
import br.com.seplag.edital.model.UnidadeEnderecoId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UnidadeEnderecoRepository extends JpaRepository<UnidadeEndereco, UnidadeEnderecoId> {
}