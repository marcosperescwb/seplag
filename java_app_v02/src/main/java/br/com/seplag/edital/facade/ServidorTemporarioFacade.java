package br.com.seplag.edital.facade;

import br.com.seplag.edital.model.ServidorTemporario;
import br.com.seplag.edital.service.ServidorTemporarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ServidorTemporarioFacade {

    @Autowired
    private ServidorTemporarioService servidorTemporarioService;

    public ServidorTemporario obterServidorTemporarioPorId(Integer id) {
        return servidorTemporarioService.obterServidorTemporarioPorId(id);
    }

    public ServidorTemporario criarServidorTemporario(ServidorTemporario servidorTemporario) {
        return servidorTemporarioService.criarServidorTemporario(servidorTemporario);
    }

    public ServidorTemporario atualizarServidorTemporario(Integer id, ServidorTemporario servidorTemporario) {
        return servidorTemporarioService.atualizarServidorTemporario(id, servidorTemporario);
    }

    public boolean deletarServidorTemporario(Integer id) {
        return servidorTemporarioService.deletarServidorTemporario(id);
    }
}