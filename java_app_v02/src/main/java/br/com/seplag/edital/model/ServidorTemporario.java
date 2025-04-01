package br.com.seplag.edital.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "servidor_temporario")
@IdClass(ServidorTemporarioId.class) // Especifica a classe de chave primária composta
public class ServidorTemporario {

    @Id
    @Column(name = "pes_id")
    private Integer pessoa; // Use Integer para corresponder ao tipo do ID da Pessoa

    @Column(name = "st_data_admissao")
    private LocalDate stDataAdmissao;

    @Column(name = "st_data_demissao")
    private LocalDate stDataDemissao;

    public Integer getPessoa() {
        return pessoa;
    }

    public void setPessoa(Integer pessoa) {
        this.pessoa = pessoa;
    }

    public LocalDate getStDataAdmissao() {
        return stDataAdmissao;
    }

    public void setStDataAdmissao(LocalDate stDataAdmissao) {
        this.stDataAdmissao = stDataAdmissao;
    }

    public LocalDate getStDataDemissao() {
        return stDataDemissao;
    }

    public void setStDataDemissao(LocalDate stDataDemissao) {
        this.stDataDemissao = stDataDemissao;
    }
}