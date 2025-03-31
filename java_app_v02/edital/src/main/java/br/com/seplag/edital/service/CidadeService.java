package br.com.seplag.edital.service;

import br.com.seplag.edital.model.Cidade;
import java.util.List;

public interface CidadeService {

    Cidade salvar(Cidade cidade);

    Cidade buscarPorId(Integer id);

    List<Cidade> listarTodos();

    void excluir(Integer id);

    Cidade alterar(Integer id, Cidade cidade); // Novo método
}