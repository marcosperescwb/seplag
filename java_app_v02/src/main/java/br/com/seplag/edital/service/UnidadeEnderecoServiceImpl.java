package br.com.seplag.edital.service;

import br.com.seplag.edital.model.UnidadeEndereco;
import br.com.seplag.edital.model.Unidade;
import br.com.seplag.edital.model.Endereco;
import br.com.seplag.edital.model.UnidadeEnderecoId;
import br.com.seplag.edital.repository.UnidadeEnderecoRepository;
import br.com.seplag.edital.repository.UnidadeRepository;
import br.com.seplag.edital.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UnidadeEnderecoServiceImpl implements UnidadeEnderecoService {

    @Autowired
    private UnidadeEnderecoRepository unidadeEnderecoRepository;

    @Autowired
    private UnidadeRepository unidadeRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Override
    public List<UnidadeEndereco> listarUnidadeEnderecos() {
        return unidadeEnderecoRepository.findAll();
    }

    @Override
    public UnidadeEndereco obterUnidadeEnderecoPorId(Integer unidId, Integer endId) {
        UnidadeEnderecoId id = new UnidadeEnderecoId(unidId, endId);
        Optional<UnidadeEndereco> unidadeEnderecoOptional = unidadeEnderecoRepository.findById(id);
        return unidadeEnderecoOptional.orElse(null);
    }

    @Override
    public UnidadeEndereco criarUnidadeEndereco(UnidadeEndereco unidadeEndereco) {
        Optional<Unidade> unidadeOptional = unidadeRepository.findById(unidadeEndereco.getUnidade().getUnidId());
        Optional<Endereco> enderecoOptional = enderecoRepository.findById(unidadeEndereco.getEndereco().getId());

        if (unidadeOptional.isPresent() && enderecoOptional.isPresent()) {
            Unidade unidade = unidadeOptional.get();
            Endereco endereco = enderecoOptional.get();
            unidadeEndereco.setUnidade(unidade);
            unidadeEndereco.setEndereco(endereco);
            return unidadeEnderecoRepository.save(unidadeEndereco);
        } else {
            return null; // Tratar o caso em que a unidade ou o endereço não existem
        }
    }

    @Override
    public boolean deletarUnidadeEndereco(Integer unidId, Integer endId) {
        UnidadeEnderecoId id = new UnidadeEnderecoId(unidId, endId);
        if (unidadeEnderecoRepository.existsById(id)) {
            unidadeEnderecoRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}