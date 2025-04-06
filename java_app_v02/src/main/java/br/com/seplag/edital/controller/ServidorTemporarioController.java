package br.com.seplag.edital.controller;

import br.com.seplag.edital.facade.ServidorTemporarioFacade;
import br.com.seplag.edital.model.ServidorTemporario;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;

@RestController
@RequestMapping("/api/v1/servidoresTemporarios")
@Tag(name = "Servidores Temporários", description = "Operações relacionadas a Servidores Temporários")
public class ServidorTemporarioController {

    @Autowired
    private ServidorTemporarioFacade servidorTemporarioFacade;

    // Nota: Geralmente se busca pelo ID da Pessoa ou por um ID próprio do contrato temporário.
    // Usando o ID próprio (como no código original)
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Busca dados de servidor temporário pelo seu ID único")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Servidor Temporário encontrado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ServidorTemporario.class))),
            @ApiResponse(responseCode = "404", description = "Servidor Temporário não encontrado", content = @Content)
    })
    public ResponseEntity<ServidorTemporario> obterServidorTemporarioPorId(
            @Parameter(description = "ID único do registro do servidor temporário", required = true, example = "305")
            @PathVariable Integer id) {
        ServidorTemporario servidorTemporario = servidorTemporarioFacade.obterServidorTemporarioPorId(id);
        if (servidorTemporario != null) {
            return new ResponseEntity<>(servidorTemporario, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Cria um novo registro de servidor temporário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Servidor Temporário criado com sucesso",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ServidorTemporario.class))),
            @ApiResponse(responseCode = "400", description = "Requisição inválida (ex: ID de pessoa não existe, dados inválidos)", content = @Content)
    })
    public ResponseEntity<ServidorTemporario> criarServidorTemporario(
            @Parameter(description="Objeto JSON do servidor temporário a ser criado. Deve incluir o ID da Pessoa associada.", required = true,
                    schema=@Schema(implementation = ServidorTemporario.class))
            @RequestBody ServidorTemporario servidorTemporario) {
        // @Valid se necessário
        ServidorTemporario novoServidorTemporario = servidorTemporarioFacade.criarServidorTemporario(servidorTemporario);
        return new ResponseEntity<>(novoServidorTemporario, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Atualiza dados de um servidor temporário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Servidor Temporário atualizado com sucesso",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ServidorTemporario.class))),
            @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content),
            @ApiResponse(responseCode = "404", description = "Servidor Temporário não encontrado", content = @Content)
    })
    public ResponseEntity<ServidorTemporario> atualizarServidorTemporario(
            @Parameter(description = "ID único do registro do servidor temporário a ser atualizado", required = true, example = "305")
            @PathVariable Integer id,
            @Parameter(description="Objeto JSON com os dados atualizados do servidor temporário", required = true,
                    schema=@Schema(implementation = ServidorTemporario.class))
            @RequestBody ServidorTemporario servidorTemporario) {
        // @Valid se necessário
        ServidorTemporario servidorTemporarioAtualizado = servidorTemporarioFacade.atualizarServidorTemporario(id, servidorTemporario);
        if (servidorTemporarioAtualizado != null) {
            return new ResponseEntity<>(servidorTemporarioAtualizado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta o registro de um servidor temporário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Servidor Temporário deletado com sucesso", content = @Content),
            @ApiResponse(responseCode = "404", description = "Servidor Temporário não encontrado", content = @Content)
    })
    public ResponseEntity<Void> deletarServidorTemporario(
            @Parameter(description = "ID único do registro do servidor temporário a ser deletado", required = true, example = "305")
            @PathVariable Integer id) {
        boolean deletado = servidorTemporarioFacade.deletarServidorTemporario(id);
        if (deletado) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}