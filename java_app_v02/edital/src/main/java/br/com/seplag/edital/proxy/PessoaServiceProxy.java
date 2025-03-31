package br.com.seplag.edital.proxy;

import br.com.seplag.edital.model.Pessoa;
import br.com.seplag.edital.service.PessoaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public class PessoaServiceProxy implements PessoaService {

    private static final Logger logger = LoggerFactory.getLogger(PessoaServiceProxy.class);

    @Autowired
    @Qualifier("pessoaServiceImpl")
    private PessoaService pessoaService;

    @Override
    public Pessoa salvar(Pessoa pessoa) {
        logger.info("Salvando pessoa: {}", pessoa);
        return pessoaService.salvar(pessoa);
    }

    @Override
    public Pessoa buscarPorId(Integer id) {
        logger.info("Buscando pessoa por ID: {}", id);
        return pessoaService.buscarPorId(id);
    }

    @Override
    public List<Pessoa> listarTodos() {
        logger.info("Listando todas as pessoas");
        return pessoaService.listarTodos();
    }

    @Override
    public boolean excluir(Integer id) {
        logger.info("Excluindo pessoa com ID: {}", id);
        pessoaService.excluir(id);
        return true;
    }

    @Override
    public Pessoa alterar(Integer id, Pessoa pessoa) {
        logger.info("Alterando pessoa com ID: {}, Dados: {}", id, pessoa);
        return pessoaService.alterar(id, pessoa);
    }
}