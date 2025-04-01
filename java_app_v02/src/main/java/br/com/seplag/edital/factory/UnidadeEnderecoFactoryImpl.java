package br.com.seplag.edital.factory;

import br.com.seplag.edital.model.UnidadeEndereco;
import br.com.seplag.edital.model.Unidade;
import br.com.seplag.edital.model.Endereco;
import org.springframework.stereotype.Component;

@Component
public class UnidadeEnderecoFactoryImpl implements UnidadeEnderecoFactory {

    @Override
    public UnidadeEndereco criarUnidadeEndereco(Unidade unidade, Endereco endereco) {
        UnidadeEndereco unidadeEndereco = new UnidadeEndereco();
        unidadeEndereco.setUnidade(unidade);
        unidadeEndereco.setEndereco(endereco);
        return unidadeEndereco;
    }
}