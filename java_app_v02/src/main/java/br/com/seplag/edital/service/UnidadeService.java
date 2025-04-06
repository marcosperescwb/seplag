package br.com.seplag.edital.service;

import br.com.seplag.edital.model.Unidade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;


public interface UnidadeService {

    Page<Unidade> listarUnidades(Pageable pageable);

    Unidade obterUnidadePorId(Integer id);

    Unidade criarUnidade(Unidade unidade);

    Unidade atualizarUnidade(Integer id, Unidade unidade);

    boolean deletarUnidade(Integer id);
}