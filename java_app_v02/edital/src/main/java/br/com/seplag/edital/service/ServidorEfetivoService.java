package br.com.seplag.edital.service;

import br.com.seplag.edital.model.ServidorEfetivo;

public interface ServidorEfetivoService {

    ServidorEfetivo obterServidorEfetivoPorPesId(Integer pesId);

    ServidorEfetivo criarServidorEfetivo(ServidorEfetivo servidorEfetivo);

    ServidorEfetivo atualizarServidorEfetivo(Integer pesId, ServidorEfetivo servidorEfetivo);

    boolean deletarServidorEfetivo(Integer pesId);
}