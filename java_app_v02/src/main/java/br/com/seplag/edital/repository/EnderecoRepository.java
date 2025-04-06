package br.com.seplag.edital.repository;

import br.com.seplag.edital.model.Endereco;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {

    @Query("SELECT DISTINCT end " + // Seleciona o alias 'end' para Endereco
            "FROM Pessoa p " +
            "JOIN ServidorEfetivo se ON se.pesId = p.id " +
            "JOIN Lotacao l ON l.pessoa.id = p.id " +
            "JOIN UnidadeEndereco ue ON ue.unidade.id = l.unidade.id " +
            "JOIN ue.endereco end " + // JOIN explícito para obter o alias 'end'
            "WHERE lower(p.nome) LIKE lower(concat('%', :nomeServidor, '%')) " +
            "AND l.lotDataRemocao IS NULL")
    Page<Endereco> findEnderecoUnidadeByServidorNomeContaining(
            @Param("nomeServidor") String nomeServidor,
            Pageable pageable); // O Pageable agora pode usar 'sort=logradouro', 'sort=bairro', etc.

}