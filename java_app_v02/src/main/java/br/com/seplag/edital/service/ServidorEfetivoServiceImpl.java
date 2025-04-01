package br.com.seplag.edital.service;

import br.com.seplag.edital.model.ServidorEfetivo;
import br.com.seplag.edital.model.ServidorEfetivoId;
import br.com.seplag.edital.repository.ServidorEfetivoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ServidorEfetivoServiceImpl implements ServidorEfetivoService {

    @Autowired
    private ServidorEfetivoRepository servidorEfetivoRepository;

    @Override
    public ServidorEfetivo obterServidorEfetivoPorPesId(Integer pesId) {
        ServidorEfetivoId seId = new ServidorEfetivoId(pesId);
        Optional<ServidorEfetivo> servidorEfetivoOptional = servidorEfetivoRepository.findById(seId);
        return servidorEfetivoOptional.orElse(null);
    }

    @Override
    public ServidorEfetivo criarServidorEfetivo(ServidorEfetivo servidorEfetivo) {
        return servidorEfetivoRepository.save(servidorEfetivo);
    }

    @Override
    public ServidorEfetivo atualizarServidorEfetivo(Integer pesId, ServidorEfetivo servidorEfetivo) {
        ServidorEfetivoId seId = new ServidorEfetivoId(pesId);
        Optional<ServidorEfetivo> servidorEfetivoOptional = servidorEfetivoRepository.findById(seId);

        if (servidorEfetivoOptional.isPresent()) {
            servidorEfetivo.setPessoa(pesId);  //Mantém a integridade do pes_id
            return servidorEfetivoRepository.save(servidorEfetivo);
        } else {
            return null;
        }
    }

    @Override
    public boolean deletarServidorEfetivo(Integer pesId) {
        ServidorEfetivoId seId = new ServidorEfetivoId(pesId);
        if (servidorEfetivoRepository.existsById(seId)) {
            servidorEfetivoRepository.deleteById(seId);
            return true;
        } else {
            return false;
        }
    }
}