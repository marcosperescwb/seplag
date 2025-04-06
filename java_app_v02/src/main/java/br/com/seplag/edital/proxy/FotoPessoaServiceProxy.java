package br.com.seplag.edital.proxy;

import br.com.seplag.edital.model.FotoPessoa;
import br.com.seplag.edital.service.FotoPessoaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public class FotoPessoaServiceProxy implements FotoPessoaService {

    private static final Logger logger = LoggerFactory.getLogger(FotoPessoaServiceProxy.class);

    @Autowired
    @Qualifier("fotoPessoaServiceImpl") // Adapte o nome do bean da implementação
    private FotoPessoaService fotoPessoaService;

    @Override
    public Page<FotoPessoa> listarFotoPessoas(Pageable pageable) {
        logger.info("Listando todas as fotos de pessoas");
        return fotoPessoaService.listarFotoPessoas(pageable);
    }

    @Override
    public FotoPessoa obterFotoPessoaPorId(Integer id) {
        logger.info("Obtendo foto de pessoa por ID: {}", id);
        return fotoPessoaService.obterFotoPessoaPorId(id);
    }

    @Override
    public FotoPessoa criarFotoPessoa(FotoPessoa fotoPessoa) {
        logger.info("Criando foto de pessoa: {}", fotoPessoa);
        return fotoPessoaService.criarFotoPessoa(fotoPessoa);
    }

    @Override
    public FotoPessoa atualizarFotoPessoa(Integer id, FotoPessoa fotoPessoa) {
        logger.info("Atualizando foto de pessoa com ID: {}, Dados: {}", id, fotoPessoa);
        return fotoPessoaService.atualizarFotoPessoa(id, fotoPessoa);
    }

    @Override
    public boolean deletarFotoPessoa(Integer id) {
        logger.info("Deletando foto de pessoa com ID: {}", id);
        return fotoPessoaService.deletarFotoPessoa(id);
    }
}