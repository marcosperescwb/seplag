package br.com.seplag.edital.factory;

import br.com.seplag.edital.model.Unidade;

public interface UnidadeFactory {

    Unidade criarUnidade(String unidNome, String unidSigla);
}