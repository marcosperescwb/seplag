package br.com.seplag.edital.service;

import br.com.seplag.edital.model.Endereco;
import br.com.seplag.edital.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnderecoServiceImpl implements EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Override
    public List<Endereco> listarEnderecos() {
        return enderecoRepository.findAll();
    }

    @Override
    public Endereco obterEnderecoPorId(Integer id) {
        Optional<Endereco> enderecoOptional = enderecoRepository.findById(id);
        return enderecoOptional.orElse(null);
    }

    @Override
    public Endereco criarEndereco(Endereco endereco) {
        return enderecoRepository.save(endereco);
    }

    @Override
    public Endereco atualizarEndereco(Integer id, Endereco endereco) {
        Optional<Endereco> enderecoOptional = enderecoRepository.findById(id);
        if (enderecoOptional.isPresent()) {
            Endereco enderecoExistente = enderecoOptional.get();
            endereco.setId(id);  //Garante que o ID não seja sobrescrito
            return enderecoRepository.save(endereco);
        } else {
            return null;
        }
    }

    @Override
    public boolean deletarEndereco(Integer id) {
        if (enderecoRepository.existsById(id)) {
            enderecoRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}