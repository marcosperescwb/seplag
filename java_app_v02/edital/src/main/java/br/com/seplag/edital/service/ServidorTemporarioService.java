package br.com.seplag.edital.service;

import br.com.seplag.edital.model.ServidorTemporario;
import br.com.seplag.edital.model.ServidorTemporarioId;
import java.util.List;

public interface ServidorTemporarioService {

    ServidorTemporario obterServidorTemporarioPorId(Integer id);

    ServidorTemporario criarServidorTemporario(ServidorTemporario servidorTemporario);

    ServidorTemporario atualizarServidorTemporario(Integer id, ServidorTemporario servidorTemporario);

    boolean deletarServidorTemporario(Integer id);
}