package br.com.seplag.edital.service;

import br.com.seplag.edital.model.FotoPessoa;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FotoPessoaService {

    Page<FotoPessoa> listarFotoPessoas(Pageable pageable);

    FotoPessoa obterFotoPessoaPorId(Integer id);

    FotoPessoa criarFotoPessoa(FotoPessoa fotoPessoa);

    FotoPessoa atualizarFotoPessoa(Integer id, FotoPessoa fotoPessoa);

    boolean deletarFotoPessoa(Integer id);
}