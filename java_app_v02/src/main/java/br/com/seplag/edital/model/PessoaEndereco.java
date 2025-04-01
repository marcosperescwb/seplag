package br.com.seplag.edital.model;

import jakarta.persistence.*;

@Entity
@Table(name = "pessoa_endereco")
@IdClass(PessoaEnderecoId.class) // Use a classe IdClass para chaves compostas
public class PessoaEndereco {

    @Id
    @ManyToOne
    @JoinColumn(name = "pes_id")
    private Pessoa pessoa;

    @Id
    @ManyToOne
    @JoinColumn(name = "end_id")
    private Endereco endereco;

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
}