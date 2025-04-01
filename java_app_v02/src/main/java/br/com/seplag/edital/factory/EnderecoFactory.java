package br.com.seplag.edital.factory;

import br.com.seplag.edital.model.Cidade;
import br.com.seplag.edital.model.Endereco;

public interface EnderecoFactory {
    Endereco criarEndereco(String tipoLogradouro, String logradouro, Integer numero, String bairro, Cidade cidade);
}