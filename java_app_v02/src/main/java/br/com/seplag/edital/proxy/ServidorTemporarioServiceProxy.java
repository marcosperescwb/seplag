package br.com.seplag.edital.proxy; // Ajuste o pacote se necessário

import br.com.seplag.edital.model.ServidorTemporario;
import br.com.seplag.edital.service.ServidorTemporarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
// --- Imports para Paginação ---
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
// --- Fim Imports Paginação ---
import org.springframework.stereotype.Service;

// Remover import List se não for mais usado
// import java.time.LocalDate;
// import java.util.List;

@Service
@Primary // Define esta proxy como a implementação padrão
public class ServidorTemporarioServiceProxy implements ServidorTemporarioService { // Implementa a interface

    private static final Logger logger = LoggerFactory.getLogger(ServidorTemporarioServiceProxy.class);

    @Autowired
    // Garante injeção da implementação real. Ajuste o nome se necessário.
    @Qualifier("servidorTemporarioServiceImpl")
    private ServidorTemporarioService targetService; // Renomeado para clareza

    @Override
    public Page<ServidorTemporario> listarServidoresTemporarios(Pageable pageable) {
        logger.info("Proxy: Listando Servidores Temporários com paginação: {}", pageable);
        return targetService.listarServidoresTemporarios(pageable);
    }

    @Override
    public ServidorTemporario obterServidorTemporarioPorId(Integer id) {
        logger.debug("Proxy: Obtendo servidor temporário por ID: {}", id);
        ServidorTemporario resultado = targetService.obterServidorTemporarioPorId(id);
        if (resultado != null) {
            logger.debug("Proxy: Servidor Temporário encontrado para ID: {}", id);
        } else {
            logger.warn("Proxy: Servidor Temporário NÃO encontrado para ID: {}", id);
        }
        return resultado;
    }

    @Override
    public ServidorTemporario criarServidorTemporario(ServidorTemporario servidorTemporario) {
        // Log antes da chamada real
        logger.info("Proxy: Criando servidor temporário para Pessoa ID: {}",
                servidorTemporario.getPessoa() != null ? servidorTemporario.getPessoa()  : "NULO/INVÁLIDO"); // Adiciona log com ID da pessoa
        ServidorTemporario criado = targetService.criarServidorTemporario(servidorTemporario);
        // Log depois (assume sucesso se não lançar exceção)
        logger.info("Proxy: Servidor Temporário criado com ID: {}", criado); // Assume que a entidade tem um getId()
        return criado;
    }

    @Override
    public ServidorTemporario atualizarServidorTemporario(Integer id, ServidorTemporario servidorTemporario) {
        logger.info("Proxy: Atualizando servidor temporário com ID: {}", id);
        ServidorTemporario atualizado = targetService.atualizarServidorTemporario(id, servidorTemporario);
        if(atualizado != null) {
            logger.info("Proxy: Servidor Temporário atualizado com sucesso: ID {}", id);
        } else {
            logger.warn("Proxy: Atualização falhou para Servidor Temporário ID {} (não encontrado?).", id);
        }
        return atualizado;
    }

    @Override
    public boolean deletarServidorTemporario(Integer id) { // Assinatura já retorna boolean
        logger.warn("Proxy: Deletando servidor temporário com ID: {}", id);
        boolean resultado = targetService.deletarServidorTemporario(id);
        if (resultado) {
            logger.info("Proxy: Exclusão do servidor temporário ID {} reportada como sucesso.", id);
        } else {
            logger.warn("Proxy: Exclusão do servidor temporário ID {} reportada como falha.", id);
        }
        return resultado;
    }
}