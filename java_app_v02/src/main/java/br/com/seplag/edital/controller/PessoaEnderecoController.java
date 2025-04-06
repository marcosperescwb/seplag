package br.com.seplag.edital.controller; // Ajuste o pacote se necessário

import br.com.seplag.edital.facade.PessoaEnderecoFacade;
import br.com.seplag.edital.model.PessoaEndereco;
import br.com.seplag.edital.model.PessoaEnderecoId; // Importar IdClass
// --- Imports para Paginação ---
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.domain.Sort;
// --- Fim Imports Paginação ---
// --- Imports para OpenAPI/Swagger ---
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
// --- Fim Imports OpenAPI ---
import org.slf4j.Logger;        // Importar Logger
import org.slf4j.LoggerFactory; // Importar LoggerFactory
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Remover import List se não for mais usado
// import java.util.List;

@RestController
@RequestMapping("/api/v1/pessoa-enderecos") // Padronizar com /api/v1/ e kebab-case
@Tag(name = "Pessoa Endereços", description = "Associação entre Pessoas e Endereços")
public class PessoaEnderecoController {

    private static final Logger logger = LoggerFactory.getLogger(PessoaEnderecoController.class); // Adicionar Logger

    @Autowired
    private PessoaEnderecoFacade pessoaEnderecoFacade;

    // --- MÉTODO GET LISTAR PAGINADO ---
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Lista todas as associações Pessoa-Endereço de forma paginada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Página de associações retornada",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Page.class))) // Esquema é Page
    })
    public ResponseEntity<Page<PessoaEndereco>> listarPessoaEnderecos(
            @PageableDefault(size = 10, sort = "pessoa.nome", direction = Sort.Direction.ASC) Pageable pageable) {
        logger.info("Requisição GET /pessoa-enderecos com paginação: {}", pageable);
        Page<PessoaEndereco> paginaPessoaEnderecos = pessoaEnderecoFacade.listarPessoaEnderecos(pageable);
        return ResponseEntity.ok(paginaPessoaEnderecos);
    }
    // --- FIM MÉTODO GET LISTAR PAGINADO ---

    // --- OUTROS ENDPOINTS (GET por ID composta, POST, DELETE) ---
    // O GET por ID composta já está correto para chaves compostas via @PathVariable
    @GetMapping(value = "/pessoa/{pesId}/endereco/{endId}", produces = MediaType.APPLICATION_JSON_VALUE) // Path mais descritivo
    @Operation(summary = "Busca uma associação Pessoa-Endereço pela chave composta")
    @ApiResponses(value = { /* ... */ })
    public ResponseEntity<PessoaEndereco> obterPessoaEnderecoPorId(
            @Parameter(description = "ID da Pessoa", required = true, example = "100")
            @PathVariable Integer pesId,
            @Parameter(description = "ID do Endereço", required = true, example = "10")
            @PathVariable Integer endId) {
        logger.debug("Requisição GET /pessoa-enderecos/pessoa/{}/endereco/{}", pesId, endId);
        PessoaEndereco pessoaEndereco = pessoaEnderecoFacade.obterPessoaEnderecoPorId(pesId, endId);
        if (pessoaEndereco != null) {
            return ResponseEntity.ok(pessoaEndereco);
        } else {
            logger.warn("Associação PessoaEndereco não encontrada para Pessoa ID: {} e Endereco ID: {}", pesId, endId);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Cria uma nova associação Pessoa-Endereço")
    @ApiResponses(value = { /* ... */ })
    public ResponseEntity<PessoaEndereco> criarPessoaEndereco(
            @Parameter(description="Objeto JSON da associação a ser criada. Precisa incluir 'pessoa': {'id': id_pessoa} e 'endereco': {'id': id_endereco}", required = true,
                    schema=@Schema(implementation = PessoaEndereco.class)) // Schema pode precisar de ajuste para mostrar a estrutura esperada
            @RequestBody PessoaEndereco pessoaEndereco) { // Adicionar @Valid se tiver validações
        // Validação: Garantir que pessoa e endereco com seus IDs foram enviados no RequestBody
        if (pessoaEndereco.getPessoa() == null || pessoaEndereco.getPessoa().getId() == null ||
                pessoaEndereco.getEndereco() == null || pessoaEndereco.getEndereco().getId() == null) {
            logger.warn("Requisição POST /pessoa-enderecos inválida: IDs de Pessoa e Endereço são obrigatórios.");
            return ResponseEntity.badRequest().build(); // Ou retornar uma mensagem de erro mais específica
        }
        logger.info("Requisição POST /pessoa-enderecos - Criando associação para Pessoa ID: {} e Endereço ID: {}",
                pessoaEndereco.getPessoa().getId(), pessoaEndereco.getEndereco().getId());
        PessoaEndereco novaPessoaEndereco = pessoaEnderecoFacade.criarPessoaEndereco(pessoaEndereco);
        // TODO: Retornar header 'Location' com a URI do recurso criado (ex: /pessoa-enderecos/pessoa/{pesId}/endereco/{endId})
        return ResponseEntity.status(HttpStatus.CREATED).body(novaPessoaEndereco);
    }

    // O DELETE por ID composta também já estava correto
    @DeleteMapping("/pessoa/{pesId}/endereco/{endId}") // Path mais descritivo
    @Operation(summary = "Deleta uma associação Pessoa-Endereço pela chave composta")
    @ApiResponses(value = { /* ... */ })
    public ResponseEntity<Void> deletarPessoaEndereco(
            @Parameter(description = "ID da Pessoa", required = true, example = "100")
            @PathVariable Integer pesId,
            @Parameter(description = "ID do Endereço", required = true, example = "10")
            @PathVariable Integer endId) {
        logger.info("Requisição DELETE /pessoa-enderecos/pessoa/{}/endereco/{}", pesId, endId);
        boolean deletado = pessoaEnderecoFacade.deletarPessoaEndereco(pesId, endId);
        if (deletado) {
            logger.info("Associação PessoaEndereco deletada para Pessoa ID: {} e Endereço ID: {}", pesId, endId);
            return ResponseEntity.noContent().build();
        } else {
            logger.warn("Tentativa de deletar associação PessoaEndereco não encontrada para Pessoa ID: {} e Endereço ID: {}", pesId, endId);
            return ResponseEntity.notFound().build();
        }
    }
}