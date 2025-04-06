package br.com.seplag.edital.facade;

import br.com.seplag.edital.model.UnidadeEndereco;
import br.com.seplag.edital.service.UnidadeEnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UnidadeEnderecoFacade {

    @Autowired
    private UnidadeEnderecoService unidadeEnderecoService;

    public Page<UnidadeEndereco> listarUnidadeEnderecos(Pageable pageable) {
        return unidadeEnderecoService.listarUnidadeEnderecos(pageable);
    }

    public UnidadeEndereco obterUnidadeEnderecoPorId(Integer unidId, Integer endId) {
        return unidadeEnderecoService.obterUnidadeEnderecoPorId(unidId, endId);
    }

    public UnidadeEndereco criarUnidadeEndereco(UnidadeEndereco unidadeEndereco) {
        return unidadeEnderecoService.criarUnidadeEndereco(unidadeEndereco);
    }

    public boolean deletarUnidadeEndereco(Integer unidId, Integer endId) {
        return unidadeEnderecoService.deletarUnidadeEndereco(unidId, endId);
    }
}