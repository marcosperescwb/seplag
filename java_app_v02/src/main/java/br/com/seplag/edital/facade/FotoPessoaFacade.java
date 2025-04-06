package br.com.seplag.edital.facade;

import br.com.seplag.edital.model.FotoPessoa;
import br.com.seplag.edital.service.FotoPessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FotoPessoaFacade {

    @Autowired
    private FotoPessoaService fotoPessoaService;

    public Page<FotoPessoa> listarFotoPessoas(Pageable pageable) {
        return fotoPessoaService.listarFotoPessoas(pageable);
    }

    public FotoPessoa obterFotoPessoaPorId(Integer id) {
        return fotoPessoaService.obterFotoPessoaPorId(id);
    }

    public FotoPessoa criarFotoPessoa(FotoPessoa fotoPessoa) {
        return fotoPessoaService.criarFotoPessoa(fotoPessoa);
    }

    public FotoPessoa atualizarFotoPessoa(Integer id, FotoPessoa fotoPessoa) {
        return fotoPessoaService.atualizarFotoPessoa(id, fotoPessoa);
    }

    public boolean deletarFotoPessoa(Integer id) {
        return fotoPessoaService.deletarFotoPessoa(id);
    }
}