package br.com.seplag.edital.controller;

import br.com.seplag.edital.facade.PessoaFacade;
import br.com.seplag.edital.model.Pessoa;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

    @Autowired
    private PessoaFacade pessoaFacade;

    @PostMapping
    public ResponseEntity<Pessoa> criarPessoa(@Valid @RequestBody Pessoa pessoa) {
        Pessoa novaPessoa = pessoaFacade.criarPessoa(pessoa);
        return new ResponseEntity<>(novaPessoa, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pessoa> buscarPessoa(@PathVariable Integer id) {
        Pessoa pessoa = pessoaFacade.buscarPessoaPorId(id);
        if (pessoa != null) {
            return new ResponseEntity<>(pessoa, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<Pessoa>> listarTodasPessoas() {
        List<Pessoa> pessoas = pessoaFacade.listarTodasPessoas();
        return new ResponseEntity<>(pessoas, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pessoa> alterarPessoa(@PathVariable Integer id, @Valid @RequestBody Pessoa pessoa) {
        Pessoa pessoaAtualizada = pessoaFacade.alterarPessoa(id, pessoa);
        if (pessoaAtualizada != null) {
            return new ResponseEntity<>(pessoaAtualizada, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirPessoa(@PathVariable Integer id) {
        pessoaFacade.excluirPessoa(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}