package br.com.seplag.edital.controller;

import br.com.seplag.edital.facade.UnidadeEnderecoFacade;
import br.com.seplag.edital.model.UnidadeEndereco;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/unidadeEnderecos")
public class UnidadeEnderecoController {

    @Autowired
    private UnidadeEnderecoFacade unidadeEnderecoFacade;

    @GetMapping
    public ResponseEntity<List<UnidadeEndereco>> listarUnidadeEnderecos() {
        List<UnidadeEndereco> unidadeEnderecos = unidadeEnderecoFacade.listarUnidadeEnderecos();
        return new ResponseEntity<>(unidadeEnderecos, HttpStatus.OK);
    }

    @GetMapping("/{unidId}/{endId}")
    public ResponseEntity<UnidadeEndereco> obterUnidadeEnderecoPorId(@PathVariable Integer unidId, @PathVariable Integer endId) {
        UnidadeEndereco unidadeEndereco = unidadeEnderecoFacade.obterUnidadeEnderecoPorId(unidId, endId);
        if (unidadeEndereco != null) {
            return new ResponseEntity<>(unidadeEndereco, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<UnidadeEndereco> criarUnidadeEndereco(@RequestBody UnidadeEndereco unidadeEndereco) {
        UnidadeEndereco novaUnidadeEndereco = unidadeEnderecoFacade.criarUnidadeEndereco(unidadeEndereco);
        return new ResponseEntity<>(novaUnidadeEndereco, HttpStatus.CREATED);
    }

    @DeleteMapping("/{unidId}/{endId}")
    public ResponseEntity<Void> deletarUnidadeEndereco(@PathVariable Integer unidId, @PathVariable Integer endId) {
        boolean deletado = unidadeEnderecoFacade.deletarUnidadeEndereco(unidId, endId);
        if (deletado) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}