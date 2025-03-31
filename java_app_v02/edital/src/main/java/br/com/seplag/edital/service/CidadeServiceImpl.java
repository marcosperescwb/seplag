package br.com.seplag.edital.service;

import br.com.seplag.edital.model.Cidade;
import br.com.seplag.edital.repository.CidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component("cidadeServiceImpl")
public class CidadeServiceImpl implements CidadeService {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Override
    @Transactional
    public Cidade salvar(Cidade cidade) {
        return cidadeRepository.save(cidade);
    }

    @Override
    public Cidade buscarPorId(Integer id) {
        return cidadeRepository.findById(id).orElse(null);
    }

    @Override
    public List<Cidade> listarTodos() {
        return cidadeRepository.findAll();
    }

    @Override
    public void excluir(Integer id) {
        cidadeRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Cidade alterar(Integer id, Cidade cidade) {
        Optional<Cidade> cidadeExistente = cidadeRepository.findById(id);
        if (cidadeExistente.isPresent()) {
            Cidade cidadeParaAtualizar = cidadeExistente.get();
            cidadeParaAtualizar.setNome(cidade.getNome());
            cidadeParaAtualizar.setEstado(cidade.getEstado());
            return cidadeRepository.save(cidadeParaAtualizar);
        } else {
            return null; // Ou lançar uma exceção
        }
    }
}