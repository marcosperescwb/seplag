package br.com.seplag.edital.factory;

import br.com.seplag.edital.model.ServidorTemporario;
import br.com.seplag.edital.model.Pessoa;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ServidorTemporarioFactoryImpl implements ServidorTemporarioFactory {

    @Override
    public ServidorTemporario criarServidorTemporario(Pessoa pessoa, LocalDate stDataAdmissao, LocalDate stDataDemissao) {
        ServidorTemporario servidorTemporario = new ServidorTemporario();
        servidorTemporario.setPessoa(pessoa.getId());
        servidorTemporario.setStDataAdmissao(stDataAdmissao);
        servidorTemporario.setStDataDemissao(stDataDemissao);
        return servidorTemporario;
    }
}