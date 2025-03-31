package br.com.seplag.edital.factory;

import br.com.seplag.edital.model.Lotacao;
import br.com.seplag.edital.model.Pessoa;
import br.com.seplag.edital.model.Unidade;

import java.time.LocalDate;

public interface LotacaoFactory {

    Lotacao criarLotacao(Pessoa pessoa, Unidade unidade, LocalDate lotDataLotacao, LocalDate lotDataRemocao, String lotPortaria);
}