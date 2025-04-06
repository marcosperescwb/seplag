package br.com.seplag.edital.service;

import br.com.seplag.edital.model.Endereco;
import br.com.seplag.edital.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import br.com.seplag.edital.model.Endereco;
import br.com.seplag.edital.repository.EnderecoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException; // Para exclusão
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Importar Transactional


import java.util.Optional;

@Service
public class EnderecoServiceImpl implements EnderecoService {

    private static final Logger logger = LoggerFactory.getLogger(EnderecoServiceImpl.class);

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Override
    public Page<Endereco> listarEnderecos(Pageable pageable) {
        return enderecoRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Endereco> findEnderecoUnidadePorNomeServidor(String nomeServidor, Pageable pageable) {
        if (nomeServidor == null || nomeServidor.trim().isEmpty()) {
            logger.warn("Nome do servidor para busca está vazio ou nulo. Retornando página vazia.");
            return Page.empty(pageable);
        }
        logger.info("Buscando endereços de unidade por nome de servidor contendo '{}' com paginação: {}", nomeServidor, pageable);
        return enderecoRepository.findEnderecoUnidadeByServidorNomeContaining(nomeServidor, pageable);
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