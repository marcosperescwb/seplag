package br.com.seplag.edital.proxy; // Ajuste o pacote se necessário

import br.com.seplag.edital.model.Cidade;
import br.com.seplag.edital.service.CidadeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;     // Import Page
import org.springframework.data.domain.Pageable; // Import Pageable
import org.springframework.stereotype.Service;   // Use @Service aqui também

// Remover imports não usados
// import org.springframework.data.domain.Sort;
// import org.springframework.data.web.PageableDefault;
// import java.util.List;

@Service // É um componente de serviço
@Primary // Define este proxy como a implementação padrão do CidadeService a ser injetada
public class CidadeServiceProxy implements CidadeService { // Implementa a interface correta

    private static final Logger logger = LoggerFactory.getLogger(CidadeServiceProxy.class);

    @Autowired
    @Qualifier("cidadeServiceImpl")
    private CidadeService targetService;

    @Override
    public Cidade salvar(Cidade cidade) {
        logger.debug("Proxy: Antes de salvar cidade: {}", cidade.getNome());
        Cidade savedCidade = targetService.salvar(cidade);
        logger.info("Proxy: Cidade salva com ID: {}", savedCidade.getId());
        return savedCidade;
    }

    @Override
    public Cidade buscarPorId(Integer id) {
        logger.debug("Proxy: Buscando cidade por ID: {}", id);
        Cidade cidade = targetService.buscarPorId(id);
        if (cidade != null) {
            logger.debug("Proxy: Cidade encontrada: {}", cidade.getNome());
        } else {
            logger.warn("Proxy: Cidade não encontrada por ID: {}", id);
        }
        return cidade;
    }

    // --- MÉTODO LISTAR CORRIGIDO ---
    @Override
    public Page<Cidade> listarCidades(String nome, String estado, Pageable pageable) { // Assinatura correta
        // Remover @PageableDefault daqui
        logger.info("Proxy: Listando cidades - Filtros: nome='{}', estado='{}', Paginação: {}", nome, estado, pageable);
        // Chama o método correto no serviço injetado, passando TODOS os parâmetros
        return targetService.listarCidades(nome, estado, pageable);
    }
    // --- FIM MÉTODO LISTAR CORRIGIDO ---

    // --- MÉTODO EXCLUIR CORRIGIDO ---
    @Override
    public void excluir(Integer id) { // Assinatura correta com retorno boolean
        logger.warn("Proxy: Tentando excluir cidade com ID: {}", id);
        targetService.excluir(id); // Chama o método e captura o resultado
    }
    // --- FIM MÉTODO EXCLUIR CORRIGIDO ---

    @Override
    public Cidade alterar(Integer id, Cidade cidade) {
        logger.info("Proxy: Tentando alterar cidade com ID: {}", id);
        Cidade cidadeAlterada = targetService.alterar(id, cidade);
        if (cidadeAlterada != null) {
            logger.info("Proxy: Cidade alterada com sucesso: ID {}", id);
        } else {
            logger.warn("Proxy: Alteração da cidade ID {} falhou (não encontrada?).", id);
        }
        return cidadeAlterada;
    }
}