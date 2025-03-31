package br.com.seplag.edital.model;

import jakarta.persistence.*;

@Entity
@Table(name = "unidade_endereco")
@IdClass(UnidadeEnderecoId.class) // Use a classe IdClass para chaves compostas
public class UnidadeEndereco {

    @Id
    @ManyToOne
    @JoinColumn(name = "unid_id")
    private Unidade unidade;

    @Id
    @ManyToOne
    @JoinColumn(name = "end_id")
    private Endereco endereco;

    public Unidade getUnidade() {
        return unidade;
    }

    public void setUnidade(Unidade unidade) {
        this.unidade = unidade;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
}