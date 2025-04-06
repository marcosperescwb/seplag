package br.com.seplag.edital.service;

import br.com.seplag.edital.model.ServidorEfetivo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ServidorEfetivoService {

    ServidorEfetivo obterServidorEfetivoPorPesId(Integer pesId);

    Page<ServidorEfetivo> listarServidoresEfetivos(Pageable pageable);

    ServidorEfetivo criarServidorEfetivo(ServidorEfetivo servidorEfetivo);

    ServidorEfetivo atualizarServidorEfetivo(Integer pesId, ServidorEfetivo servidorEfetivo);

    boolean deletarServidorEfetivo(Integer pesId);
}