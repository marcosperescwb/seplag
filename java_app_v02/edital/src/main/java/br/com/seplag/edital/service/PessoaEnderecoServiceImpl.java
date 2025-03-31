package br.com.seplag.edital.service;

import br.com.seplag.edital.model.PessoaEndereco;
import br.com.seplag.edital.model.PessoaEnderecoId;
import br.com.seplag.edital.repository.PessoaEnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PessoaEnderecoServiceImpl implements PessoaEnderecoService {

    @Autowired
    private PessoaEnderecoRepository pessoaEnderecoRepository;

    @Override
    public List<PessoaEndereco> listarPessoaEnderecos() {
        return pessoaEnderecoRepository.findAll();
    }

    @Override
    public PessoaEndereco obterPessoaEnderecoPorId(Integer pesId, Integer endId) {
        PessoaEnderecoId id = new PessoaEnderecoId(pesId, endId);
        Optional<PessoaEndereco> pessoaEnderecoOptional = pessoaEnderecoRepository.findById(id);
        return pessoaEnderecoOptional.orElse(null);
    }


    @Override
    public PessoaEndereco criarPessoaEndereco(PessoaEndereco pessoaEndereco) {
        return pessoaEnderecoRepository.save(pessoaEndereco);
    }

    @Override
    public boolean deletarPessoaEndereco(Integer pesId, Integer endId) {
        PessoaEnderecoId id = new PessoaEnderecoId(pesId, endId);
        if (pessoaEnderecoRepository.existsById(id)) {
            pessoaEnderecoRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}