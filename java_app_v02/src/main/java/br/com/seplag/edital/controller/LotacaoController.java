package br.com.seplag.edital.controller;

import br.com.seplag.edital.facade.LotacaoFacade;
import br.com.seplag.edital.model.Lotacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lotacoes")
public class LotacaoController {

    @Autowired
    private LotacaoFacade lotacaoFacade;

    @GetMapping
    public ResponseEntity<List<Lotacao>> listarLotacoes() {
        List<Lotacao> lotacoes = lotacaoFacade.listarLotacoes();
        return new ResponseEntity<>(lotacoes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Lotacao> obterLotacaoPorId(@PathVariable Integer id) {
        Lotacao lotacao = lotacaoFacade.obterLotacaoPorId(id);
        if (lotacao != null) {
            return new ResponseEntity<>(lotacao, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Lotacao> criarLotacao(@RequestBody Lotacao lotacao) {
        Lotacao novaLotacao = lotacaoFacade.criarLotacao(lotacao);
        return new ResponseEntity<>(novaLotacao, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Lotacao> atualizarLotacao(@PathVariable Integer id, @RequestBody Lotacao lotacao) {
        Lotacao lotacaoAtualizada = lotacaoFacade.atualizarLotacao(id, lotacao);
        if (lotacaoAtualizada != null) {
            return new ResponseEntity<>(lotacaoAtualizada, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarLotacao(@PathVariable Integer id) {
        boolean deletado = lotacaoFacade.deletarLotacao(id);
        if (deletado) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}