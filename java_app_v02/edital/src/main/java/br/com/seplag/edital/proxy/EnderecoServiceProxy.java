package br.com.seplag.edital.proxy;

import br.com.seplag.edital.model.Endereco;
import br.com.seplag.edital.service.EnderecoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public class EnderecoServiceProxy implements EnderecoService {

    private static final Logger logger = LoggerFactory.getLogger(EnderecoServiceProxy.class);

    @Autowired
    @Qualifier("enderecoServiceImpl") // Adapte o nome do bean da implementação
    private EnderecoService enderecoService;

    @Override
    public List<Endereco> listarEnderecos() {
        logger.info("Listando todos os endereços");
        return enderecoService.listarEnderecos();
    }

    @Override
    public Endereco obterEnderecoPorId(Integer id) {
        logger.info("Obtendo endereço por ID: {}", id);
        return enderecoService.obterEnderecoPorId(id);
    }

    @Override
    public Endereco criarEndereco(Endereco endereco) {
        logger.info("Criando endereço: {}", endereco);
        return enderecoService.criarEndereco(endereco);
    }

    @Override
    public Endereco atualizarEndereco(Integer id, Endereco endereco) {
        logger.info("Atualizando endereço com ID: {}, Dados: {}", id, endereco);
        return enderecoService.atualizarEndereco(id, endereco);
    }

    @Override
    public boolean deletarEndereco(Integer id) {
        logger.info("Deletando endereço com ID: {}", id);
        return enderecoService.deletarEndereco(id);
    }
}