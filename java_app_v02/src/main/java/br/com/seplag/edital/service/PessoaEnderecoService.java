package br.com.seplag.edital.service;

import br.com.seplag.edital.model.PessoaEndereco;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface PessoaEnderecoService {

    Page<PessoaEndereco> listarPessoaEnderecos(Pageable pageable);

    PessoaEndereco obterPessoaEnderecoPorId(Integer pesId, Integer endId);

    PessoaEndereco criarPessoaEndereco(PessoaEndereco pessoaEndereco);

    boolean deletarPessoaEndereco(Integer pesId, Integer endId);
}