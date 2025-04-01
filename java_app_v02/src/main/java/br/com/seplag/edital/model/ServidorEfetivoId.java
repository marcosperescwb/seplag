package br.com.seplag.edital.model;

import java.io.Serializable;
import java.util.Objects;

public class ServidorEfetivoId implements Serializable {

    private Integer pessoa;  // Correspondente ao tipo da chave primária em Pessoa

    public ServidorEfetivoId() {
    }

    public ServidorEfetivoId(Integer pessoa) {
        this.pessoa = pessoa;
    }

    public Integer getPessoa() {
        return pessoa;
    }

    public void setPessoa(Integer pessoa) {
        this.pessoa = pessoa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServidorEfetivoId that = (ServidorEfetivoId) o;
        return Objects.equals(pessoa, that.pessoa);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pessoa);
    }
}