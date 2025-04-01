package br.com.seplag.edital.controller;

import br.com.seplag.edital.facade.FotoPessoaFacade;
import br.com.seplag.edital.model.FotoPessoa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fotoPessoas")
public class FotoPessoaController {

    @Autowired
    private FotoPessoaFacade fotoPessoaFacade;

    @GetMapping
    public ResponseEntity<List<FotoPessoa>> listarFotoPessoas() {
        List<FotoPessoa> fotoPessoas = fotoPessoaFacade.listarFotoPessoas();
        return new ResponseEntity<>(fotoPessoas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FotoPessoa> obterFotoPessoaPorId(@PathVariable Integer id) {
        FotoPessoa fotoPessoa = fotoPessoaFacade.obterFotoPessoaPorId(id);
        if (fotoPessoa != null) {
            return new ResponseEntity<>(fotoPessoa, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<FotoPessoa> criarFotoPessoa(@RequestBody FotoPessoa fotoPessoa) {
        FotoPessoa novaFotoPessoa = fotoPessoaFacade.criarFotoPessoa(fotoPessoa);
        return new ResponseEntity<>(novaFotoPessoa, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FotoPessoa> atualizarFotoPessoa(@PathVariable Integer id, @RequestBody FotoPessoa fotoPessoa) {
        FotoPessoa fotoPessoaAtualizada = fotoPessoaFacade.atualizarFotoPessoa(id, fotoPessoa);
        if (fotoPessoaAtualizada != null) {
            return new ResponseEntity<>(fotoPessoaAtualizada, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarFotoPessoa(@PathVariable Integer id) {
        boolean deletado = fotoPessoaFacade.deletarFotoPessoa(id);
        if (deletado) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}