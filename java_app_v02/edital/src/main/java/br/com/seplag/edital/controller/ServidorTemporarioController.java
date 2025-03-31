package br.com.seplag.edital.controller;

import br.com.seplag.edital.facade.ServidorTemporarioFacade;
import br.com.seplag.edital.model.ServidorTemporario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/servidoresTemporarios")
public class ServidorTemporarioController {

    @Autowired
    private ServidorTemporarioFacade servidorTemporarioFacade;

    @GetMapping("/{id}")
    public ResponseEntity<ServidorTemporario> obterServidorTemporarioPorId(@PathVariable Integer id) {
        ServidorTemporario servidorTemporario = servidorTemporarioFacade.obterServidorTemporarioPorId(id);
        if (servidorTemporario != null) {
            return new ResponseEntity<>(servidorTemporario, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<ServidorTemporario> criarServidorTemporario(@RequestBody ServidorTemporario servidorTemporario) {
        ServidorTemporario novoServidorTemporario = servidorTemporarioFacade.criarServidorTemporario(servidorTemporario);
        return new ResponseEntity<>(novoServidorTemporario, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServidorTemporario> atualizarServidorTemporario(@PathVariable Integer id, @RequestBody ServidorTemporario servidorTemporario) {
        ServidorTemporario servidorTemporarioAtualizado = servidorTemporarioFacade.atualizarServidorTemporario(id, servidorTemporario);
        if (servidorTemporarioAtualizado != null) {
            return new ResponseEntity<>(servidorTemporarioAtualizado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarServidorTemporario(@PathVariable Integer id) {
        boolean deletado = servidorTemporarioFacade.deletarServidorTemporario(id);
        if (deletado) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}