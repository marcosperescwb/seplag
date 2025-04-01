package br.com.seplag.edital.controller;

import br.com.seplag.edital.facade.EnderecoFacade;
import br.com.seplag.edital.model.Endereco;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enderecos")
public class EnderecoController {

    @Autowired
    private EnderecoFacade enderecoFacade;

    @GetMapping
    public ResponseEntity<List<Endereco>> listarEnderecos() {
        List<Endereco> enderecos = enderecoFacade.listarEnderecos();
        return new ResponseEntity<>(enderecos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Endereco> obterEnderecoPorId(@PathVariable Integer id) {
        Endereco endereco = enderecoFacade.obterEnderecoPorId(id);
        if (endereco != null) {
            return new ResponseEntity<>(endereco, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Endereco> criarEndereco(@RequestBody Endereco endereco) {
        Endereco novoEndereco = enderecoFacade.criarEndereco(endereco);
        return new ResponseEntity<>(novoEndereco, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Endereco> atualizarEndereco(@PathVariable Integer id, @RequestBody Endereco endereco) {
        Endereco enderecoAtualizado = enderecoFacade.atualizarEndereco(id, endereco);
        if (enderecoAtualizado != null) {
            return new ResponseEntity<>(enderecoAtualizado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarEndereco(@PathVariable Integer id) {
        boolean deletado = enderecoFacade.deletarEndereco(id);
        if (deletado) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}