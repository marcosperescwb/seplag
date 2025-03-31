package br.com.seplag.edital.service;

import br.com.seplag.edital.model.Unidade;

import java.util.List;

public interface UnidadeService {

    List<Unidade> listarUnidades();

    Unidade obterUnidadePorId(Integer id);

    Unidade criarUnidade(Unidade unidade);

    Unidade atualizarUnidade(Integer id, Unidade unidade);

    boolean deletarUnidade(Integer id);
}