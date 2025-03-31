package br.com.seplag.edital.service;

import br.com.seplag.edital.model.UnidadeEndereco;

import java.util.List;

public interface UnidadeEnderecoService {

    List<UnidadeEndereco> listarUnidadeEnderecos();

    UnidadeEndereco obterUnidadeEnderecoPorId(Integer unidId, Integer endId);

    UnidadeEndereco criarUnidadeEndereco(UnidadeEndereco unidadeEndereco);

    boolean deletarUnidadeEndereco(Integer unidId, Integer endId);
}