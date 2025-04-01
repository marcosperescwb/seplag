package br.com.seplag.edital.facade;

import br.com.seplag.edital.model.ServidorEfetivo;
import br.com.seplag.edital.service.ServidorEfetivoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ServidorEfetivoFacade {

    @Autowired
    private ServidorEfetivoService servidorEfetivoService;

    public ServidorEfetivo obterServidorEfetivoPorPesId(Integer pesId) {
        return servidorEfetivoService.obterServidorEfetivoPorPesId(pesId);
    }

    public ServidorEfetivo criarServidorEfetivo(ServidorEfetivo servidorEfetivo) {
        return servidorEfetivoService.criarServidorEfetivo(servidorEfetivo);
    }

    public ServidorEfetivo atualizarServidorEfetivo(Integer pesId, ServidorEfetivo servidorEfetivo) {
        return servidorEfetivoService.atualizarServidorEfetivo(pesId, servidorEfetivo);
    }

    public boolean deletarServidorEfetivo(Integer pesId) {
        return servidorEfetivoService.deletarServidorEfetivo(pesId);
    }
}