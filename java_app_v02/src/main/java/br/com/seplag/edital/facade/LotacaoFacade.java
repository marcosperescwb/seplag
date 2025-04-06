package br.com.seplag.edital.facade;

import br.com.seplag.edital.model.Lotacao;
import br.com.seplag.edital.service.LotacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LotacaoFacade {

    @Autowired
    private LotacaoService lotacaoService;

    public Page<Lotacao> listarLotacoes(Pageable pageable) {
        return lotacaoService.listarLotacoes(pageable);
    }

    public Lotacao obterLotacaoPorId(Integer id) {
        return lotacaoService.obterLotacaoPorId(id);
    }

    public Lotacao criarLotacao(Lotacao lotacao) {
        return lotacaoService.criarLotacao(lotacao);
    }

    public Lotacao atualizarLotacao(Integer id, Lotacao lotacao) {
        return lotacaoService.atualizarLotacao(id, lotacao);
    }

    public boolean deletarLotacao(Integer id) {
        return lotacaoService.deletarLotacao(id);
    }
}