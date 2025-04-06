package br.com.seplag.edital.model;

import java.io.Serializable;
import java.util.Objects;

public class ServidorEfetivoId implements Serializable {

    private Integer pesId; // <-- RENOMEADO de 'pessoa' para 'pesId'

    public ServidorEfetivoId() {
    }

    public ServidorEfetivoId(Integer pesId) { // <-- RENOMEADO
        this.pesId = pesId;
    }

    public Integer getPesId() { // <-- RENOMEADO
        return pesId;
    }

    public void setPesId(Integer pesId) { // <-- RENOMEADO
        this.pesId = pesId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServidorEfetivoId that = (ServidorEfetivoId) o;
        return Objects.equals(pesId, that.pesId); // <-- RENOMEADO
    }

    @Override
    public int hashCode() {
        return Objects.hash(pesId); // <-- RENOMEADO
    }
}