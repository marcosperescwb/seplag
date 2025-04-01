package br.com.seplag.edital.model;

import java.io.Serializable;
import java.util.Objects;

public class PessoaEnderecoId implements Serializable {

    private Integer pessoa;
    private Integer endereco;

    public PessoaEnderecoId() {}

    public PessoaEnderecoId(Integer pessoa, Integer endereco) {
        this.pessoa = pessoa;
        this.endereco = endereco;
    }

    public Integer getPessoa() {
        return pessoa;
    }

    public void setPessoa(Integer pessoa) {
        this.pessoa = pessoa;
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
        PessoaEnderecoId that = (PessoaEnderecoId) o;
        return Objects.equals(pessoa, that.pessoa) && Objects.equals(endereco, that.endereco);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pessoa, endereco);
    }
}