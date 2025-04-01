package br.com.seplag.edital.factory;

import br.com.seplag.edital.model.Lotacao;
import br.com.seplag.edital.model.Pessoa;
import br.com.seplag.edital.model.Unidade;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class LotacaoFactoryImpl implements LotacaoFactory {

    @Override
    public Lotacao criarLotacao(Pessoa pessoa, Unidade unidade, LocalDate lotDataLotacao, LocalDate lotDataRemocao, String lotPortaria) {
        Lotacao lotacao = new Lotacao();
        lotacao.setPessoa(pessoa);
        lotacao.setUnidade(unidade);
        lotacao.setLotDataLotacao(lotDataLotacao);
        lotacao.setLotDataRemocao(lotDataRemocao);
        lotacao.setLotPortaria(lotPortaria);
        return lotacao;
    }
}