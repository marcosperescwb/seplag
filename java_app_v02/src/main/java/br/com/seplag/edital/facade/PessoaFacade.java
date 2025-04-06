package br.com.seplag.edital.facade;

import br.com.seplag.edital.model.Pessoa;
import br.com.seplag.edital.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PessoaFacade {

    @Autowired
    private PessoaService pessoaService;

    public Pessoa criarPessoa(Pessoa pessoa) {

        return pessoaService.salvar(pessoa);
    }

    public Pessoa buscarPessoaPorId(Integer id) {

        return pessoaService.buscarPorId(id);
    }

    public Page<Pessoa> listarTodasPessoas(Pageable pageable) {

        return pessoaService.listarTodos(pageable);
    }

    public void excluirPessoa(Integer id) {

        pessoaService.excluir(id);
    }

    public Pessoa alterarPessoa(Integer id, Pessoa pessoa) {

        return pessoaService.alterar(id, pessoa);
    }
}