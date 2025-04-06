package br.com.seplag.edital.facade;

import br.com.seplag.edital.model.Endereco;
import br.com.seplag.edital.service.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EnderecoFacade {

    @Autowired
    private EnderecoService enderecoService;

    public Page<Endereco> listarEnderecos(Pageable pageable) {
        return enderecoService.listarEnderecos(pageable);
    }

    public Page<Endereco> findEnderecoUnidadePorNomeServidor(String nomeServidor, Pageable pageable) {
        return enderecoService.findEnderecoUnidadePorNomeServidor(nomeServidor, pageable);
    }

    public Endereco obterEnderecoPorId(Integer id) {
        return enderecoService.obterEnderecoPorId(id);
    }

    public Endereco criarEndereco(Endereco endereco) {
        return enderecoService.criarEndereco(endereco);
    }

    public Endereco atualizarEndereco(Integer id, Endereco endereco) {
        return enderecoService.atualizarEndereco(id, endereco);
    }

    public boolean deletarEndereco(Integer id) {
        return enderecoService.deletarEndereco(id);
    }
}