package br.com.seplag.edital.controller;

import br.com.seplag.edital.facade.PessoaEnderecoFacade;
import br.com.seplag.edital.model.PessoaEndereco;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pessoaEnderecos")
public class PessoaEnderecoController {

    @Autowired
    private PessoaEnderecoFacade pessoaEnderecoFacade;

    @GetMapping
    public ResponseEntity<List<PessoaEndereco>> listarPessoaEnderecos() {
        List<PessoaEndereco> pessoaEnderecos = pessoaEnderecoFacade.listarPessoaEnderecos();
        return new ResponseEntity<>(pessoaEnderecos, HttpStatus.OK);
    }

    @GetMapping("/{pesId}/{endId}")
    public ResponseEntity<PessoaEndereco> obterPessoaEnderecoPorId(@PathVariable Integer pesId, @PathVariable Integer endId) {
        PessoaEndereco pessoaEndereco = pessoaEnderecoFacade.obterPessoaEnderecoPorId(pesId, endId);
        if (pessoaEndereco != null) {
            return new ResponseEntity<>(pessoaEndereco, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<PessoaEndereco> criarPessoaEndereco(@RequestBody PessoaEndereco pessoaEndereco) {
        PessoaEndereco novaPessoaEndereco = pessoaEnderecoFacade.criarPessoaEndereco(pessoaEndereco);
        return new ResponseEntity<>(novaPessoaEndereco, HttpStatus.CREATED);
    }

    @DeleteMapping("/{pesId}/{endId}")
    public ResponseEntity<Void> deletarPessoaEndereco(@PathVariable Integer pesId, @PathVariable Integer endId) {
        boolean deletado = pessoaEnderecoFacade.deletarPessoaEndereco(pesId, endId);
        if (deletado) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}