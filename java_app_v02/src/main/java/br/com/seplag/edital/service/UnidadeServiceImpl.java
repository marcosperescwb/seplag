package br.com.seplag.edital.service;

import br.com.seplag.edital.model.Unidade;
import br.com.seplag.edital.repository.UnidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UnidadeServiceImpl implements UnidadeService {

    @Autowired
    private UnidadeRepository unidadeRepository;

    @Override
    public Page<Unidade> listarUnidades(Pageable pageable) {
        return unidadeRepository.findAll(pageable);
    }

    @Override
    public Unidade obterUnidadePorId(Integer id) {
        Optional<Unidade> unidadeOptional = unidadeRepository.findById(id);
        return unidadeOptional.orElse(null);
    }

    @Override
    public Unidade criarUnidade(Unidade unidade) {
        return unidadeRepository.save(unidade);
    }

    @Override
    public Unidade atualizarUnidade(Integer id, Unidade unidade) {
        Optional<Unidade> unidadeOptional = unidadeRepository.findById(id);
        if (unidadeOptional.isPresent()) {
            unidade.setUnidId(id);
            return unidadeRepository.save(unidade);
        } else {
            return null;
        }
    }

    @Override
    public boolean deletarUnidade(Integer id) {
        if (unidadeRepository.existsById(id)) {
            unidadeRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}