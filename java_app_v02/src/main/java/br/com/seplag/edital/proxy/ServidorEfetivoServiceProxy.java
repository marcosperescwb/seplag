package br.com.seplag.edital.proxy;

import br.com.seplag.edital.model.ServidorEfetivo;
import br.com.seplag.edital.service.ServidorEfetivoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public class ServidorEfetivoServiceProxy implements ServidorEfetivoService {

    private static final Logger logger = LoggerFactory.getLogger(ServidorEfetivoServiceProxy.class);

    @Autowired
    @Qualifier("servidorEfetivoServiceImpl") // Adapte o nome do bean da implementação
    private ServidorEfetivoService servidorEfetivoService;

    @Override
    public ServidorEfetivo obterServidorEfetivoPorPesId(Integer pesId) {
        logger.info("Obtendo servidor efetivo por ID da pessoa: {}", pesId);
        return servidorEfetivoService.obterServidorEfetivoPorPesId(pesId);
    }

    @Override
    public ServidorEfetivo criarServidorEfetivo(ServidorEfetivo servidorEfetivo) {
        logger.info("Criando servidor efetivo: {}", servidorEfetivo);
        return servidorEfetivoService.criarServidorEfetivo(servidorEfetivo);
    }

    @Override
    public ServidorEfetivo atualizarServidorEfetivo(Integer pesId, ServidorEfetivo servidorEfetivo) {
        logger.info("Alterando servidor efetivo com ID da pessoa: {}, Dados: {}", pesId, servidorEfetivo);
        return servidorEfetivoService.atualizarServidorEfetivo(pesId, servidorEfetivo);
    }

    @Override
    public boolean deletarServidorEfetivo(Integer pesId) {
        logger.info("Deletando servidor efetivo com ID da pessoa: {}", pesId);
        return servidorEfetivoService.deletarServidorEfetivo(pesId);
    }
}