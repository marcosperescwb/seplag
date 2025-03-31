package br.com.seplag.edital.factory;

import br.com.seplag.edital.model.FotoPessoa;
import br.com.seplag.edital.model.Pessoa;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class FotoPessoaFactoryImpl implements FotoPessoaFactory {

    @Override
    public FotoPessoa criarFotoPessoa(Pessoa pessoa, LocalDate fpData, String fpBucket, String fpHash) {
        FotoPessoa fotoPessoa = new FotoPessoa();
        fotoPessoa.setPessoa(pessoa);
        fotoPessoa.setFpData(fpData);
        fotoPessoa.setFpBucket(fpBucket);
        fotoPessoa.setFpHash(fpHash);
        return fotoPessoa;
    }
}