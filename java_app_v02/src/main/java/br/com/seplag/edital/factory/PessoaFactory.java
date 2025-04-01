package br.com.seplag.edital.factory;

import br.com.seplag.edital.model.Pessoa;
import java.time.LocalDate;

public interface PessoaFactory {

    Pessoa criarPessoa(String nome, LocalDate dataNascimento, String sexo, String mae, String pai);
}