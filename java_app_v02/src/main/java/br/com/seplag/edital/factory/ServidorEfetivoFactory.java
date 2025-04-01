package br.com.seplag.edital.factory;

import br.com.seplag.edital.model.ServidorEfetivo;
import br.com.seplag.edital.model.Pessoa;

public interface ServidorEfetivoFactory {

    ServidorEfetivo criarServidorEfetivo(Pessoa pessoa, String seMatricula);
}