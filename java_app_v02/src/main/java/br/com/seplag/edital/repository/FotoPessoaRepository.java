package br.com.seplag.edital.repository;

import br.com.seplag.edital.model.FotoPessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FotoPessoaRepository extends JpaRepository<FotoPessoa, Integer> {

    // Assumindo que FotoPessoa tem @ManyToOne Pessoa pessoa; e Pessoa.id é Integer
    @Query("SELECT fp.fpHash FROM FotoPessoa fp WHERE fp.pessoa.id = :pessoaId ORDER BY fp.fpData DESC LIMIT 1")
    Optional<String> findLatestFotoHashByPessoaId(@Param("pessoaId") Integer pessoaId);

    // Se FotoPessoa tiver 'private Integer pesId;' em vez de '@ManyToOne Pessoa pessoa;'
    // @Query("SELECT fp.fpHash FROM FotoPessoa fp WHERE fp.pesId = :pessoaId ORDER BY fp.fpData DESC LIMIT 1")
    // Optional<String> findLatestFotoHashByPessoaId(@Param("pessoaId") Integer pessoaId);
}