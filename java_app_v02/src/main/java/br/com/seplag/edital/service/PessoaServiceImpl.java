package br.com.seplag.edital.service;

import br.com.seplag.edital.model.Pessoa;
import br.com.seplag.edital.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component("pessoaServiceImpl")
public class PessoaServiceImpl implements PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Override
    @Transactional
    public Pessoa salvar(Pessoa pessoa) {
        return pessoaRepository.save(pessoa);
    }

    @Override
    public Pessoa buscarPorId(Integer id) {
        return pessoaRepository.findById(id).orElse(null);
    }

    @Override
    public Page<Pessoa> listarTodos(Pageable pageable) {
        return pessoaRepository.findAll(pageable);
    }

    @Override
    public void excluir(Integer id) {
        if (!pessoaRepository.existsById(id)) {
            // Lança uma exceção se a pessoa não for encontrada
            throw new EmptyResultDataAccessException("Nenhuma Pessoa encontrada com ID: " + id, 1);
        }
        pessoaRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Pessoa alterar(Integer id, Pessoa pessoa) {
        Optional<Pessoa> pessoaExistente = pessoaRepository.findById(id);
        if (pessoaExistente.isPresent()) {
            Pessoa pessoaParaAtualizar = pessoaExistente.get();
            pessoaParaAtualizar.setNome(pessoa.getNome());
            pessoaParaAtualizar.setDataNascimento(pessoa.getDataNascimento());
            pessoaParaAtualizar.setSexo(pessoa.getSexo());
            pessoaParaAtualizar.setMae(pessoa.getMae());
            pessoaParaAtualizar.setPai(pessoa.getPai());
            return pessoaRepository.save(pessoaParaAtualizar);
        } else {
            return null; // Ou lançar uma exceção
        }
    }
}