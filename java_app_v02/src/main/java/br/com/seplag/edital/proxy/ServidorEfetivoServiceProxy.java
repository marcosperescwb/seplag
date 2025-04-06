package br.com.seplag.edital.proxy; // Ajuste o pacote se necessário

import br.com.seplag.edital.model.ServidorEfetivo;
import br.com.seplag.edital.service.ServidorEfetivoService;
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
// import java.util.List;

@Service
@Primary // Define esta proxy como a implementação padrão do ServidorEfetivoService
public class ServidorEfetivoServiceProxy implements ServidorEfetivoService { // Implementa a interface

    private static final Logger logger = LoggerFactory.getLogger(ServidorEfetivoServiceProxy.class);

    @Autowired
    // Garante injeção da implementação real. Ajuste "servidorEfetivoServiceImpl" se o nome do bean for outro.
    @Qualifier("servidorEfetivoServiceImpl")
    private ServidorEfetivoService targetService; // Usando targetService para clareza

    // --- NOVO MÉTODO LISTAR PAGINADO ---
    @Override
    public Page<ServidorEfetivo> listarServidoresEfetivos(Pageable pageable) {
        logger.info("Proxy: Listando Servidores Efetivos com paginação: {}", pageable);
        // Delega a chamada para a implementação real
        return targetService.listarServidoresEfetivos(pageable);
    }
    // --- FIM NOVO MÉTODO ---

    @Override
    public ServidorEfetivo obterServidorEfetivoPorPesId(Integer pesId) {
        logger.debug("Proxy: Obtendo servidor efetivo por PesID: {}", pesId);
        ServidorEfetivo resultado = targetService.obterServidorEfetivoPorPesId(pesId);
        if (resultado != null) {
            logger.debug("Proxy: Servidor Efetivo encontrado para PesID: {}", pesId);
        } else {
            logger.warn("Proxy: Servidor Efetivo NÃO encontrado para PesID: {}", pesId);
        }
        return resultado;
    }

    @Override
    public ServidorEfetivo criarServidorEfetivo(ServidorEfetivo servidorEfetivo) {
        logger.info("Proxy: Criando servidor efetivo para PesID: {}", servidorEfetivo.getPesId()); // Usa o getter correto
        ServidorEfetivo criado = targetService.criarServidorEfetivo(servidorEfetivo);
        // Assume que a criação bem sucedida sempre retorna um objeto não nulo
        logger.info("Proxy: Servidor Efetivo criado para PesID: {}", criado.getPesId());
        return criado;
    }

    @Override
    public ServidorEfetivo atualizarServidorEfetivo(Integer pesId, ServidorEfetivo servidorEfetivo) {
        logger.info("Proxy: Atualizando servidor efetivo para PesID: {}", pesId);
        ServidorEfetivo atualizado = targetService.atualizarServidorEfetivo(pesId, servidorEfetivo);
        if(atualizado != null) {
            logger.info("Proxy: Servidor Efetivo atualizado para PesID: {}", pesId);
        } else {
            logger.warn("Proxy: Atualização falhou para Servidor Efetivo PesID {} (não encontrado?).", pesId);
        }
        return atualizado;
    }

    @Override
    public boolean deletarServidorEfetivo(Integer pesId) { // Assinatura já retorna boolean
        logger.warn("Proxy: Deletando servidor efetivo com PesID: {}", pesId);
        boolean resultado = targetService.deletarServidorEfetivo(pesId);
        if (resultado) {
            logger.info("Proxy: Exclusão do servidor efetivo PesID {} reportada como sucesso.", pesId);
        } else {
            logger.warn("Proxy: Exclusão do servidor efetivo PesID {} reportada como falha.", pesId);
        }
        return resultado;
    }
}