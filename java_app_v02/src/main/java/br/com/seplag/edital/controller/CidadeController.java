package br.com.seplag.edital.controller;

import br.com.seplag.edital.facade.CidadeFacade;
import br.com.seplag.edital.model.Cidade;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cidades")
public class CidadeController {

    @Autowired
    private CidadeFacade cidadeFacade;

    @PostMapping
    public ResponseEntity<Cidade> criarCidade(@Valid @RequestBody Cidade cidade) {
        Cidade novaCidade = cidadeFacade.criarCidade(cidade);
        return new ResponseEntity<>(novaCidade, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cidade> buscarCidade(@PathVariable Integer id) {
        Cidade cidade = cidadeFacade.buscarCidadePorId(id);
        if (cidade != null) {
            return new ResponseEntity<>(cidade, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<Cidade>> listarCidades(
            @RequestParam(name = "nome", required = false) String nome,
            @RequestParam(name = "estado", required = false) String estado) {

        List<Cidade> cidades = cidadeFacade.listarCidades(nome, estado);
        return new ResponseEntity<>(cidades, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirCidade(@PathVariable Integer id) {
        cidadeFacade.excluirCidade(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cidade> alterarCidade(@PathVariable Integer id, @Valid @RequestBody Cidade cidade) {
        Cidade cidadeAtualizada = cidadeFacade.alterarCidade(id, cidade);
        if (cidadeAtualizada != null) {
            return new ResponseEntity<>(cidadeAtualizada, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}