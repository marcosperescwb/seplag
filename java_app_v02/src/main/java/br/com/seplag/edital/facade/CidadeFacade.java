package br.com.seplag.edital.facade;

import br.com.seplag.edital.model.Cidade;
import br.com.seplag.edital.service.CidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Component
public class CidadeFacade {

    @Autowired
    private CidadeService cidadeService;

    public Cidade criarCidade(Cidade cidade) {
        return cidadeService.salvar(cidade);
    }

    public Cidade buscarCidadePorId(Integer id) {
        return cidadeService.buscarPorId(id);
    }

    public Page<Cidade> listarCidades(String nome, String estado, Pageable pageable) { // Aceita Pageable, Retorna Page
        return cidadeService.listarCidades(nome, estado, pageable);
    }

    public void excluirCidade(Integer id) {
        cidadeService.excluir(id);
    }

    public Cidade alterarCidade(Integer id, Cidade cidade) {
        return cidadeService.alterar(id, cidade);
    }
}