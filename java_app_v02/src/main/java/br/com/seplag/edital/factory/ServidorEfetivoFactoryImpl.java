package br.com.seplag.edital.factory;

import br.com.seplag.edital.model.ServidorEfetivo;
import br.com.seplag.edital.model.Pessoa;
import org.springframework.stereotype.Component;

@Component
public class ServidorEfetivoFactoryImpl implements ServidorEfetivoFactory {

    @Override
    public ServidorEfetivo criarServidorEfetivo(Pessoa pessoa, String seMatricula) {
        ServidorEfetivo servidorEfetivo = new ServidorEfetivo();
        servidorEfetivo.setPessoa(pessoa.getId());
        servidorEfetivo.setSeMatricula(seMatricula);
        return servidorEfetivo;
    }
}