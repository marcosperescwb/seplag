package br.com.seplag.edital.service;

import br.com.seplag.edital.model.Pessoa;
import java.util.List;

public interface PessoaService {

    Pessoa salvar(Pessoa pessoa);

    Pessoa buscarPorId(Integer id);

    List<Pessoa> listarTodos();

    boolean excluir(Integer id);

    Pessoa alterar(Integer id, Pessoa pessoa);
}