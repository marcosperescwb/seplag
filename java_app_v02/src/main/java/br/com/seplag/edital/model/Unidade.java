package br.com.seplag.edital.model;

import jakarta.persistence.*;

@Entity
@Table(name = "unidade")
public class Unidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "unid_id")
    private Integer unidId;

    @Column(name = "unid_nome")
    private String unidNome;

    @Column(name = "unid_sigla")
    private String unidSigla;

    public Integer getUnidId() {
        return unidId;
    }

    public void setUnidId(Integer unidId) {
        this.unidId = unidId;
    }

    public String getUnidNome() {
        return unidNome;
    }

    public void setUnidNome(String unidNome) {
        this.unidNome = unidNome;
    }

    public String getUnidSigla() {
        return unidSigla;
    }

    public void setUnidSigla(String unidSigla) {
        this.unidSigla = unidSigla;
    }
}