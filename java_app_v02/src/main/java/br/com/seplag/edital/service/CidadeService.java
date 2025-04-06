package br.com.seplag.edital.service;

import br.com.seplag.edital.model.Cidade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CidadeService {

    Cidade salvar(Cidade cidade);

    Cidade buscarPorId(Integer id);

    Page<Cidade> listarCidades(String nome, String estado, Pageable pageable);
    //Page<Cidade> listarTodos(Pageable pageable);

    void excluir(Integer id);

    Cidade alterar(Integer id, Cidade cidade); // Novo método
}