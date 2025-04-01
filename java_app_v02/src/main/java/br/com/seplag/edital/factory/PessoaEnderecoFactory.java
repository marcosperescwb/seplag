package br.com.seplag.edital.factory;

import br.com.seplag.edital.model.Pessoa;
import br.com.seplag.edital.model.Endereco;
import br.com.seplag.edital.model.PessoaEndereco;

public interface PessoaEnderecoFactory {

    PessoaEndereco criarPessoaEndereco(Pessoa pessoa, Endereco endereco);
}