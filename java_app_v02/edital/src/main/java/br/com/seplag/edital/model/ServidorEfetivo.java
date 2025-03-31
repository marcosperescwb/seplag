package br.com.seplag.edital.model;

import jakarta.persistence.*;

@Entity
@Table(name = "servidor_efetivo")
@IdClass(ServidorEfetivoId.class) // Especifica a classe de chave primária
public class ServidorEfetivo {

    @Id
    @Column(name = "pes_id")
    private Integer pessoa;

    @Column(name = "se_matricula")
    private String seMatricula;

    public Integer getPessoa() {
        return pessoa;
    }

    public void setPessoa(Integer pessoa) {
        this.pessoa = pessoa;
    }

    public String getSeMatricula() {
        return seMatricula;
    }

    public void setSeMatricula(String seMatricula) {
        this.seMatricula = seMatricula;
    }
}