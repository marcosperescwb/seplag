package br.com.seplag.edital.controller;

import br.com.seplag.edital.facade.ServidorEfetivoFacade;
import br.com.seplag.edital.model.ServidorEfetivo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/servidoresEfetivos")
public class ServidorEfetivoController {

    @Autowired
    private ServidorEfetivoFacade servidorEfetivoFacade;

    @GetMapping("/{pesId}")
    public ResponseEntity<ServidorEfetivo> obterServidorEfetivoPorPesId(@PathVariable Integer pesId) {
        ServidorEfetivo servidorEfetivo = servidorEfetivoFacade.obterServidorEfetivoPorPesId(pesId);
        if (servidorEfetivo != null) {
            return new ResponseEntity<>(servidorEfetivo, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<ServidorEfetivo> criarServidorEfetivo(@RequestBody ServidorEfetivo servidorEfetivo) {
        ServidorEfetivo novoServidorEfetivo = servidorEfetivoFacade.criarServidorEfetivo(servidorEfetivo);
        return new ResponseEntity<>(novoServidorEfetivo, HttpStatus.CREATED);
    }

    @PutMapping("/{pesId}")
    public ResponseEntity<ServidorEfetivo> atualizarServidorEfetivo(@PathVariable Integer pesId, @RequestBody ServidorEfetivo servidorEfetivo) {
        ServidorEfetivo servidorEfetivoAtualizado = servidorEfetivoFacade.atualizarServidorEfetivo(pesId, servidorEfetivo);
        if (servidorEfetivoAtualizado != null) {
            return new ResponseEntity<>(servidorEfetivoAtualizado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{pesId}")
    public ResponseEntity<Void> deletarServidorEfetivo(@PathVariable Integer pesId) {
        boolean deletado = servidorEfetivoFacade.deletarServidorEfetivo(pesId);
        if (deletado) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}