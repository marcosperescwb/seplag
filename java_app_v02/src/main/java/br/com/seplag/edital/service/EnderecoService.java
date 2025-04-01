package br.com.seplag.edital.service;

import br.com.seplag.edital.model.Endereco;
import java.util.List;

public interface EnderecoService {

    List<Endereco> listarEnderecos();

    Endereco obterEnderecoPorId(Integer id);

    Endereco criarEndereco(Endereco endereco);

    Endereco atualizarEndereco(Integer id, Endereco endereco);

    boolean deletarEndereco(Integer id);
}