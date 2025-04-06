package br.com.seplag.edital.service;

import br.com.seplag.edital.model.Lotacao;
import br.com.seplag.edital.dto.ServidorLotacaoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LotacaoService {

    Page<Lotacao> listarLotacoes(Pageable pageable);

    Lotacao obterLotacaoPorId(Integer id);

    Lotacao criarLotacao(Lotacao lotacao);

    Lotacao atualizarLotacao(Integer id, Lotacao lotacao);

    boolean deletarLotacao(Integer id);

    Page<ServidorLotacaoDTO> findServidoresEfetivosPorUnidade(Integer unidadeId, Pageable pageable);
}
