package br.com.seplag.edital.factory;

import br.com.seplag.edital.model.UnidadeEndereco;
import br.com.seplag.edital.model.Unidade;
import br.com.seplag.edital.model.Endereco;

public interface UnidadeEnderecoFactory {

    UnidadeEndereco criarUnidadeEndereco(Unidade unidade, Endereco endereco);
}