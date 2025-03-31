package br.com.seplag.edital.factory;

import br.com.seplag.edital.model.FotoPessoa;
import br.com.seplag.edital.model.Pessoa;

import java.time.LocalDate;

public interface FotoPessoaFactory {

    FotoPessoa criarFotoPessoa(Pessoa pessoa, LocalDate fpData, String fpBucket, String fpHash);
}