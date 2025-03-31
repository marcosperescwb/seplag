package br.com.seplag.edital.factory;

import br.com.seplag.edital.model.Cidade;

public interface CidadeFactory {

    Cidade criarCidade(String nome, String estado);
}