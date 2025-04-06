package br.com.seplag.edital.service;

import br.com.seplag.edital.model.Pessoa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PessoaService {

    Pessoa salvar(Pessoa pessoa);

    Pessoa buscarPorId(Integer id);

    Page<Pessoa> listarTodos(Pageable pageable);

    void excluir(Integer id);

    Pessoa alterar(Integer id, Pessoa pessoa);
}