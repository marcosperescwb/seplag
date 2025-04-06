package br.com.seplag.edital.service;

import br.com.seplag.edital.model.Endereco;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EnderecoService {

    Page<Endereco> listarEnderecos(Pageable pageable);

    Endereco obterEnderecoPorId(Integer id);

    Endereco criarEndereco(Endereco endereco);

    Endereco atualizarEndereco(Integer id, Endereco endereco);

    boolean deletarEndereco(Integer id);

    Page<Endereco> findEnderecoUnidadePorNomeServidor(String nomeServidor, Pageable pageable);
}