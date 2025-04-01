package br.com.seplag.edital.factory;

import br.com.seplag.edital.model.Pessoa;
import br.com.seplag.edital.model.Endereco;
import br.com.seplag.edital.model.PessoaEndereco;
import org.springframework.stereotype.Component;

@Component
public class PessoaEnderecoFactoryImpl implements PessoaEnderecoFactory {

    @Override
    public PessoaEndereco criarPessoaEndereco(Pessoa pessoa, Endereco endereco) {
        PessoaEndereco pessoaEndereco = new PessoaEndereco();
        pessoaEndereco.setPessoa(pessoa);
        pessoaEndereco.setEndereco(endereco);
        return pessoaEndereco;
    }
}