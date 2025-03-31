package br.com.seplag.edital.factory;

import br.com.seplag.edital.model.ServidorTemporario;
import br.com.seplag.edital.model.Pessoa;

import java.time.LocalDate;

public interface ServidorTemporarioFactory {

    ServidorTemporario criarServidorTemporario(Pessoa pessoa, LocalDate stDataAdmissao, LocalDate stDataDemissao);
}