package br.com.seplag.edital.service;

import br.com.seplag.edital.model.Lotacao;

import java.util.List;

public interface LotacaoService {

    List<Lotacao> listarLotacoes();

    Lotacao obterLotacaoPorId(Integer id);

    Lotacao criarLotacao(Lotacao lotacao);

    Lotacao atualizarLotacao(Integer id, Lotacao lotacao);

    boolean deletarLotacao(Integer id);
}