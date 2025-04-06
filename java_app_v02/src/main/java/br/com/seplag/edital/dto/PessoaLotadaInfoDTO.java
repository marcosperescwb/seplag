package br.com.seplag.edital.dto; // Ajuste pacote

import java.time.LocalDate;

// DTO para o resultado inicial da query principal
public class PessoaLotadaInfoDTO {
    private Integer pessoaId;
    private String pessoaNome;
    private LocalDate pessoaDataNascimento;
    private String unidadeNome;

    // Construtor usado pelo JPQL
    public PessoaLotadaInfoDTO(Integer pessoaId, String pessoaNome, LocalDate pessoaDataNascimento, String unidadeNome) {
        this.pessoaId = pessoaId;
        this.pessoaNome = pessoaNome;
        this.pessoaDataNascimento = pessoaDataNascimento;
        this.unidadeNome = unidadeNome;
    }

    // Getters
    public Integer getPessoaId() { return pessoaId; }
    public String getPessoaNome() { return pessoaNome; }
    public LocalDate getPessoaDataNascimento() { return pessoaDataNascimento; }
    public String getUnidadeNome() { return unidadeNome; }
}