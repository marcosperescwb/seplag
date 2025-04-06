package br.com.seplag.edital.service;

import br.com.seplag.edital.model.ServidorEfetivo;
import br.com.seplag.edital.model.ServidorTemporario;
import br.com.seplag.edital.model.ServidorTemporarioId;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ServidorTemporarioService {

    ServidorTemporario obterServidorTemporarioPorId(Integer id);

    Page<ServidorTemporario> listarServidoresTemporarios(Pageable pageable);

    ServidorTemporario criarServidorTemporario(ServidorTemporario servidorTemporario);

    ServidorTemporario atualizarServidorTemporario(Integer id, ServidorTemporario servidorTemporario);

    boolean deletarServidorTemporario(Integer id);
}