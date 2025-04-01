package br.com.seplag.edital.facade;

import br.com.seplag.edital.model.Cidade;
import br.com.seplag.edital.service.CidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CidadeFacade {

    @Autowired
    private CidadeService cidadeService;

    public Cidade criarCidade(Cidade cidade) {
        return cidadeService.salvar(cidade);
    }

    public Cidade buscarCidadePorId(Integer id) {
        return cidadeService.buscarPorId(id);
    }

    public List<Cidade> listarCidades(String nome, String estado) {
        List<Cidade> cidades = cidadeService.listarTodos();

        // Lógica para filtrar as cidades com base nos parâmetros nome e estado
        if (nome != null && !nome.isEmpty()) {
            cidades = cidades.stream()
                    .filter(c -> c.getNome().equalsIgnoreCase(nome))
                    .collect(Collectors.toList());
        }
        if (estado != null && !estado.isEmpty()) {
            cidades = cidades.stream()
                    .filter(c -> c.getEstado().equalsIgnoreCase(estado))
                    .collect(Collectors.toList());
        }

        return cidades;
    }

    public void excluirCidade(Integer id) {
        cidadeService.excluir(id);
    }

    public Cidade alterarCidade(Integer id, Cidade cidade) {
        return cidadeService.alterar(id, cidade);
    }
}