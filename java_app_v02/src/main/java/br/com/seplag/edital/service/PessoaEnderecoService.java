package br.com.seplag.edital.service;

import br.com.seplag.edital.model.PessoaEndereco;

import java.util.List;

public interface PessoaEnderecoService {

    List<PessoaEndereco> listarPessoaEnderecos();

    PessoaEndereco obterPessoaEnderecoPorId(Integer pesId, Integer endId);

    PessoaEndereco criarPessoaEndereco(PessoaEndereco pessoaEndereco);

    boolean deletarPessoaEndereco(Integer pesId, Integer endId);
}