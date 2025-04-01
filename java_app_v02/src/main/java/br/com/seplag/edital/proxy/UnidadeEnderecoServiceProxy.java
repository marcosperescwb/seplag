package br.com.seplag.edital.proxy;

import br.com.seplag.edital.model.UnidadeEndereco;
import br.com.seplag.edital.service.UnidadeEnderecoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public class UnidadeEnderecoServiceProxy implements UnidadeEnderecoService {

    private static final Logger logger = LoggerFactory.getLogger(UnidadeEnderecoServiceProxy.class);

    @Autowired
    @Qualifier("unidadeEnderecoServiceImpl") // Adapte o nome do bean da implementação
    private UnidadeEnderecoService unidadeEnderecoService;

    @Override
    public List<UnidadeEndereco> listarUnidadeEnderecos() {
        logger.info("Listando todos os relacionamentos unidade-endereço");
        return unidadeEnderecoService.listarUnidadeEnderecos();
    }

    @Override
    public UnidadeEndereco obterUnidadeEnderecoPorId(Integer unidId, Integer endId) {
        logger.info("Obtendo relacionamento unidade-endereço por unidId: {} e endId: {}", unidId, endId);
        return unidadeEnderecoService.obterUnidadeEnderecoPorId(unidId, endId);
    }

    @Override
    public UnidadeEndereco criarUnidadeEndereco(UnidadeEndereco unidadeEndereco) {
        logger.info("Criando relacionamento unidade-endereço: {}", unidadeEndereco);
        return unidadeEnderecoService.criarUnidadeEndereco(unidadeEndereco);
    }

    @Override
    public boolean deletarUnidadeEndereco(Integer unidId, Integer endId) {
        logger.info("Deletando relacionamento unidade-endereço por unidId: {} e endId: {}", unidId, endId);
        return unidadeEnderecoService.deletarUnidadeEndereco(unidId, endId);
    }
}