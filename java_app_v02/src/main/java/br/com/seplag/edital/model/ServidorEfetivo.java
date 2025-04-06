package br.com.seplag.edital.model;

import jakarta.persistence.*;

@Entity
@Table(name = "servidor_efetivo")
@IdClass(ServidorEfetivoId.class)
public class ServidorEfetivo {

    @Id
    @Column(name = "pes_id")
    private Integer pesId; // <-- RENOMEADO de 'pessoa' para 'pesId'

    @Column(name = "se_matricula")
    private String seMatricula;

    // Atualizar Getters e Setters
    public Integer getPesId() { // <-- RENOMEADO
        return pesId;
    }

    public void setPesId(Integer pesId) { // <-- RENOMEADO
        this.pesId = pesId;
    }

    public String getSeMatricula() {
        return seMatricula;
    }

    public void setSeMatricula(String seMatricula) {
        this.seMatricula = seMatricula;
    }
}