package br.com.seplag.edital.proxy;

import br.com.seplag.edital.model.PessoaEndereco;
import br.com.seplag.edital.service.PessoaEnderecoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public class PessoaEnderecoServiceProxy implements PessoaEnderecoService {

    private static final Logger logger = LoggerFactory.getLogger(PessoaEnderecoServiceProxy.class);

    @Autowired
    @Qualifier("pessoaEnderecoServiceImpl") // Adapte o nome do bean da implementação
    private PessoaEnderecoService pessoaEnderecoService;

    @Override
    public List<PessoaEndereco> listarPessoaEnderecos() {
        logger.info("Listando todos os relacionamentos pessoa-endereço");
        return pessoaEnderecoService.listarPessoaEnderecos();
    }

    @Override
    public PessoaEndereco obterPessoaEnderecoPorId(Integer pesId, Integer endId) {
        logger.info("Obtendo relacionamento pessoa-endereço por pesId: {} e endId: {}", pesId, endId);
        return pessoaEnderecoService.obterPessoaEnderecoPorId(pesId, endId);
    }

    @Override
    public PessoaEndereco criarPessoaEndereco(PessoaEndereco pessoaEndereco) {
        logger.info("Criando relacionamento pessoa-endereço: {}", pessoaEndereco);
        return pessoaEnderecoService.criarPessoaEndereco(pessoaEndereco);
    }

    @Override
    public boolean deletarPessoaEndereco(Integer pesId, Integer endId) {
        logger.info("Deletando relacionamento pessoa-endereço por pesId: {} e endId: {}", pesId, endId);
        return pessoaEnderecoService.deletarPessoaEndereco(pesId, endId);
    }
}