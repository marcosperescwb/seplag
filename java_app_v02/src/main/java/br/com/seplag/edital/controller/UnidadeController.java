package br.com.seplag.edital.controller;

import br.com.seplag.edital.facade.UnidadeFacade;
import br.com.seplag.edital.model.Unidade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/unidades")
public class UnidadeController {

    @Autowired
    private UnidadeFacade unidadeFacade;

    @GetMapping
    public ResponseEntity<List<Unidade>> listarUnidades() {
        List<Unidade> unidades = unidadeFacade.listarUnidades();
        return new ResponseEntity<>(unidades, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Unidade> obterUnidadePorId(@PathVariable Integer id) {
        Unidade unidade = unidadeFacade.obterUnidadePorId(id);
        if (unidade != null) {
            return new ResponseEntity<>(unidade, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Unidade> criarUnidade(@RequestBody Unidade unidade) {
        Unidade novaUnidade = unidadeFacade.criarUnidade(unidade);
        return new ResponseEntity<>(novaUnidade, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Unidade> atualizarUnidade(@PathVariable Integer id, @RequestBody Unidade unidade) {
        Unidade unidadeAtualizada = unidadeFacade.atualizarUnidade(id, unidade);
        if (unidadeAtualizada != null) {
            return new ResponseEntity<>(unidadeAtualizada, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUnidade(@PathVariable Integer id) {
        boolean deletado = unidadeFacade.deletarUnidade(id);
        if (deletado) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}