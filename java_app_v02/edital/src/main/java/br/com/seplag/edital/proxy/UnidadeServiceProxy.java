package br.com.seplag.edital.proxy;

import br.com.seplag.edital.model.Unidade;
import br.com.seplag.edital.service.UnidadeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public class UnidadeServiceProxy implements UnidadeService {

    private static final Logger logger = LoggerFactory.getLogger(UnidadeServiceProxy.class);

    @Autowired
    @Qualifier("unidadeServiceImpl") // Adapte o nome do bean da implementação
    private UnidadeService unidadeService;

    @Override
    public List<Unidade> listarUnidades() {
        logger.info("Listando todas as unidades");
        return unidadeService.listarUnidades();
    }

    @Override
    public Unidade obterUnidadePorId(Integer id) {
        logger.info("Obtendo unidade por ID: {}", id);
        return unidadeService.obterUnidadePorId(id);
    }

    @Override
    public Unidade criarUnidade(Unidade unidade) {
        logger.info("Criando unidade: {}", unidade);
        return unidadeService.criarUnidade(unidade);
    }

    @Override
    public Unidade atualizarUnidade(Integer id, Unidade unidade) {
        logger.info("Alterando unidade com ID: {}, Dados: {}", id, unidade);
        return unidadeService.atualizarUnidade(id, unidade);
    }

    @Override
    public boolean deletarUnidade(Integer id) {
        logger.info("Deletando unidade com ID: {}", id);
        return unidadeService.deletarUnidade(id);
    }
}