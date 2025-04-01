package br.com.seplag.edital.factory;

import br.com.seplag.edital.model.Cidade;
import br.com.seplag.edital.model.Endereco;
import org.springframework.stereotype.Component;

@Component
public class EnderecoFactoryImpl implements EnderecoFactory {

    @Override
    public Endereco criarEndereco(String tipoLogradouro, String logradouro, Integer numero, String bairro, Cidade cidade) {
        Endereco endereco = new Endereco();
        endereco.setTipoLogradouro(tipoLogradouro);
        endereco.setLogradouro(logradouro);
        endereco.setNumero(numero);
        endereco.setBairro(bairro);
        endereco.setCidade(cidade);
        return endereco;
    }
}