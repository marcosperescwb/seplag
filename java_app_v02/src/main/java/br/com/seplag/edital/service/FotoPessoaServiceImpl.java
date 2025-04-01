package br.com.seplag.edital.service;

import br.com.seplag.edital.model.FotoPessoa;
import br.com.seplag.edital.repository.FotoPessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FotoPessoaServiceImpl implements FotoPessoaService {

    @Autowired
    private FotoPessoaRepository fotoPessoaRepository;

    @Override
    public List<FotoPessoa> listarFotoPessoas() {
        return fotoPessoaRepository.findAll();
    }

    @Override
    public FotoPessoa obterFotoPessoaPorId(Integer id) {
        Optional<FotoPessoa> fotoPessoaOptional = fotoPessoaRepository.findById(id);
        return fotoPessoaOptional.orElse(null);
    }

    @Override
    public FotoPessoa criarFotoPessoa(FotoPessoa fotoPessoa) {
        return fotoPessoaRepository.save(fotoPessoa);
    }

    @Override
    public FotoPessoa atualizarFotoPessoa(Integer id, FotoPessoa fotoPessoa) {
        Optional<FotoPessoa> fotoPessoaOptional = fotoPessoaRepository.findById(id);
        if (fotoPessoaOptional.isPresent()) {
            FotoPessoa fotoPessoaExistente = fotoPessoaOptional.get();
            fotoPessoa.setId(id); //Garante que o ID não seja sobrescrito
            return fotoPessoaRepository.save(fotoPessoa);
        } else {
            return null;
        }
    }

    @Override
    public boolean deletarFotoPessoa(Integer id) {
        if (fotoPessoaRepository.existsById(id)) {
            fotoPessoaRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}