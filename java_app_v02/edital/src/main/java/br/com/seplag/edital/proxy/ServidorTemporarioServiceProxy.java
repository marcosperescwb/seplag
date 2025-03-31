package br.com.seplag.edital.proxy;

import br.com.seplag.edital.model.ServidorTemporario;
import br.com.seplag.edital.service.ServidorTemporarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Primary
public class ServidorTemporarioServiceProxy implements ServidorTemporarioService {

    private static final Logger logger = LoggerFactory.getLogger(ServidorTemporarioServiceProxy.class);

    @Autowired
    @Qualifier("servidorTemporarioServiceImpl") // Adapte o nome do bean da implementação
    private ServidorTemporarioService servidorTemporarioService;


    @Override
    public ServidorTemporario obterServidorTemporarioPorId(Integer id) {
        logger.info("Obtendo servidor temporário por ID: {}", id);
        return servidorTemporarioService.obterServidorTemporarioPorId(id);
    }

    @Override
    public ServidorTemporario criarServidorTemporario(ServidorTemporario servidorTemporario) {
        logger.info("Criando servidor temporário: {}", servidorTemporario);
        return servidorTemporarioService.criarServidorTemporario(servidorTemporario);
    }

    @Override
    public ServidorTemporario atualizarServidorTemporario(Integer id, ServidorTemporario servidorTemporario) {
        logger.info("Alterando servidor temporário com ID: {}, Dados: {}", id, servidorTemporario);
        return servidorTemporarioService.atualizarServidorTemporario(id, servidorTemporario);
    }

    @Override
    public boolean deletarServidorTemporario(Integer id) {
        logger.info("Deletando servidor temporário com ID: {}", id);
        return servidorTemporarioService.deletarServidorTemporario(id);
    }
}