package br.com.seplag.edital.service;

import br.com.seplag.edital.model.ServidorTemporario;
import br.com.seplag.edital.model.ServidorTemporarioId;
import br.com.seplag.edital.repository.ServidorTemporarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ServidorTemporarioServiceImpl implements ServidorTemporarioService {

    @Autowired
    private ServidorTemporarioRepository servidorTemporarioRepository;

    @Override
    public ServidorTemporario obterServidorTemporarioPorId(Integer id) {
        ServidorTemporarioId stId = new ServidorTemporarioId(id);
        Optional<ServidorTemporario> servidorTemporarioOptional = servidorTemporarioRepository.findById(stId);
        return servidorTemporarioOptional.orElse(null);
    }

    @Override
    public ServidorTemporario criarServidorTemporario(ServidorTemporario servidorTemporario) {
        return servidorTemporarioRepository.save(servidorTemporario);
    }

    @Override
    public ServidorTemporario atualizarServidorTemporario(Integer id, ServidorTemporario servidorTemporario) {
        ServidorTemporarioId stId = new ServidorTemporarioId(id);
        Optional<ServidorTemporario> servidorTemporarioOptional = servidorTemporarioRepository.findById(stId);

        if (servidorTemporarioOptional.isPresent()) {
            servidorTemporario.setPessoa(id); //Mantém a integridade do pes_id
            return servidorTemporarioRepository.save(servidorTemporario);
        } else {
            return null;
        }
    }

    @Override
    public boolean deletarServidorTemporario(Integer id) {
        ServidorTemporarioId stId = new ServidorTemporarioId(id);
        if (servidorTemporarioRepository.existsById(stId)) {
            servidorTemporarioRepository.deleteById(stId);
            return true;
        } else {
            return false;
        }
    }
}