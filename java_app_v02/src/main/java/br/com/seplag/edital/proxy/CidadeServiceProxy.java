package br.com.seplag.edital.proxy;

import br.com.seplag.edital.model.Cidade;
import br.com.seplag.edital.service.CidadeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary // Define este proxy como a implementação padrão do CidadeService
public class CidadeServiceProxy implements CidadeService {

    private static final Logger logger = LoggerFactory.getLogger(CidadeServiceProxy.class);

    @Autowired
    @Qualifier("cidadeServiceImpl") // Garante que a implementação real seja injetada
    private CidadeService cidadeService;

    @Override
    public Cidade salvar(Cidade cidade) {
        logger.info("Salvando cidade: {}", cidade);
        return cidadeService.salvar(cidade);
    }

    @Override
    public Cidade buscarPorId(Integer id) {
        logger.info("Buscando cidade por ID: {}", id);
        return cidadeService.buscarPorId(id);
    }

    @Override
    public List<Cidade> listarTodos() {
        logger.info("Listando todas as cidades");
        return cidadeService.listarTodos();
    }

    @Override
    public void excluir(Integer id) {
        logger.info("Excluindo cidade com ID: {}", id);
        cidadeService.excluir(id);
    }

    @Override
    public Cidade alterar(Integer id, Cidade cidade) {
        logger.info("Alterando cidade com ID: {}, Dados: {}", id, cidade);
        return cidadeService.alterar(id, cidade);
    }
}