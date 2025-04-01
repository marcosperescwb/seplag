package br.com.seplag.edital.factory;

import br.com.seplag.edital.model.Unidade;
import org.springframework.stereotype.Component;

@Component
public class UnidadeFactoryImpl implements UnidadeFactory {

    @Override
    public Unidade criarUnidade(String unidNome, String unidSigla) {
        Unidade unidade = new Unidade();
        unidade.setUnidNome(unidNome);
        unidade.setUnidSigla(unidSigla);
        return unidade;
    }
}