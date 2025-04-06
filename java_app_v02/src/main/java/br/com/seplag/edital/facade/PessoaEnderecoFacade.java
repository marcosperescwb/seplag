package br.com.seplag.edital.facade;

import br.com.seplag.edital.model.PessoaEndereco;
import br.com.seplag.edital.service.PessoaEnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PessoaEnderecoFacade {

    @Autowired
    private PessoaEnderecoService pessoaEnderecoService;

    public Page<PessoaEndereco> listarPessoaEnderecos(Pageable pageable) {
        return pessoaEnderecoService.listarPessoaEnderecos(pageable);
    }

    public PessoaEndereco obterPessoaEnderecoPorId(Integer pesId, Integer endId) {
        return pessoaEnderecoService.obterPessoaEnderecoPorId(pesId, endId);
    }

    public PessoaEndereco criarPessoaEndereco(PessoaEndereco pessoaEndereco) {
        return pessoaEnderecoService.criarPessoaEndereco(pessoaEndereco);
    }

    public boolean deletarPessoaEndereco(Integer pesId, Integer endId) {
        return pessoaEnderecoService.deletarPessoaEndereco(pesId, endId);
    }
}