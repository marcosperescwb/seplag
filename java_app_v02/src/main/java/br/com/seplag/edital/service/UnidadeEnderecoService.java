package br.com.seplag.edital.service;

import br.com.seplag.edital.model.UnidadeEndereco;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UnidadeEnderecoService {

    Page<UnidadeEndereco> listarUnidadeEnderecos(Pageable pageable);

    UnidadeEndereco obterUnidadeEnderecoPorId(Integer unidId, Integer endId);

    UnidadeEndereco criarUnidadeEndereco(UnidadeEndereco unidadeEndereco);

    boolean deletarUnidadeEndereco(Integer unidId, Integer endId);
}