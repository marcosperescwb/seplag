package br.com.seplag.edital.model;

import java.io.Serializable;
import java.util.Objects;

public class UnidadeEnderecoId implements Serializable {

    private Integer unidade;
    private Integer endereco;

    public UnidadeEnderecoId() {}

    public UnidadeEnderecoId(Integer unidade, Integer endereco) {
        this.unidade = unidade;
        this.endereco = endereco;
    }

    public Integer getUnidade() {
        return unidade;
    }

    public void setUnidade(Integer unidade) {
        this.unidade = unidade;
    }

    public Integer getEndereco() {
        return endereco;
    }

    public void setEndereco(Integer endereco) {
        this.endereco = endereco;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UnidadeEnderecoId that = (UnidadeEnderecoId) o;
        return Objects.equals(unidade, that.unidade) && Objects.equals(endereco, that.endereco);
    }

    @Override
    public int hashCode() {
        return Objects.hash(unidade, endereco);
    }
}