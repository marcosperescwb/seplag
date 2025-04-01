package br.com.seplag.edital.service;

import br.com.seplag.edital.model.FotoPessoa;

import java.util.List;

public interface FotoPessoaService {

    List<FotoPessoa> listarFotoPessoas();

    FotoPessoa obterFotoPessoaPorId(Integer id);

    FotoPessoa criarFotoPessoa(FotoPessoa fotoPessoa);

    FotoPessoa atualizarFotoPessoa(Integer id, FotoPessoa fotoPessoa);

    boolean deletarFotoPessoa(Integer id);
}