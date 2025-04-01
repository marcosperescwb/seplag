package br.com.seplag.edital.repository;

import br.com.seplag.edital.model.PessoaEndereco;
import br.com.seplag.edital.model.PessoaEnderecoId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaEnderecoRepository extends JpaRepository<PessoaEndereco, PessoaEnderecoId> {
}