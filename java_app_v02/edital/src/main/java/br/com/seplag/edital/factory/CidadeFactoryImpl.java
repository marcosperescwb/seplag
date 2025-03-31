package br.com.seplag.edital.factory;

import br.com.seplag.edital.model.Cidade;
import org.springframework.stereotype.Component;

@Component("cidadeFactory")
public class CidadeFactoryImpl implements CidadeFactory {
    @Override
    public Cidade criarCidade(String nome, String estado) {
        Cidade cidade = new Cidade();
        cidade.setNome(nome);
        cidade.setEstado(estado);
        return cidade;
    }
}