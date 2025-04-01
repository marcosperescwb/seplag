package br.com.seplag.edital.service;

import br.com.seplag.edital.model.Lotacao;
import br.com.seplag.edital.repository.LotacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LotacaoServiceImpl implements LotacaoService {

    @Autowired
    private LotacaoRepository lotacaoRepository;

    @Override
    public List<Lotacao> listarLotacoes() {
        return lotacaoRepository.findAll();
    }

    @Override
    public Lotacao obterLotacaoPorId(Integer id) {
        Optional<Lotacao> lotacaoOptional = lotacaoRepository.findById(id);
        return lotacaoOptional.orElse(null);
    }

    @Override
    public Lotacao criarLotacao(Lotacao lotacao) {
        return lotacaoRepository.save(lotacao);
    }

    @Override
    public Lotacao atualizarLotacao(Integer id, Lotacao lotacao) {
        Optional<Lotacao> lotacaoOptional = lotacaoRepository.findById(id);
        if (lotacaoOptional.isPresent()) {
            lotacao.setLotId(id);
            return lotacaoRepository.save(lotacao);
        } else {
            return null;
        }
    }

    @Override
    public boolean deletarLotacao(Integer id) {
        if (lotacaoRepository.existsById(id)) {
            lotacaoRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}