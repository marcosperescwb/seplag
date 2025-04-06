package br.com.seplag.edital.facade;

import br.com.seplag.edital.model.Unidade;
import br.com.seplag.edital.service.UnidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UnidadeFacade {

    @Autowired
    private UnidadeService unidadeService;

    public Page<Unidade> listarUnidades(Pageable pageable) {
        return unidadeService.listarUnidades(pageable);
    }

    public Unidade obterUnidadePorId(Integer id) {
        return unidadeService.obterUnidadePorId(id);
    }

    public Unidade criarUnidade(Unidade unidade) {
        return unidadeService.criarUnidade(unidade);
    }

    public Unidade atualizarUnidade(Integer id, Unidade unidade) {
        return unidadeService.atualizarUnidade(id, unidade);
    }

    public boolean deletarUnidade(Integer id) {
        return unidadeService.deletarUnidade(id);
    }
}