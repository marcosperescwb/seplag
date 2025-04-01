package br.com.seplag.edital.factory;

import br.com.seplag.edital.model.Pessoa;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component("pessoaFactory")
public class PessoaFactoryImpl implements PessoaFactory {
    @Override
    public Pessoa criarPessoa(String nome, LocalDate dataNascimento, String sexo, String mae, String pai) {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome(nome);
        pessoa.setDataNascimento(dataNascimento);
        pessoa.setSexo(sexo);
        pessoa.setMae(mae);
        pessoa.setPai(pai);
        return pessoa;
    }
}