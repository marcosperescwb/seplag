package br.com.seplag.edital.repository;

import br.com.seplag.edital.dto.PessoaLotadaInfoDTO;
import br.com.seplag.edital.model.Lotacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface LotacaoRepository extends JpaRepository<Lotacao, Integer> {

    /**
     * Busca DTOs paginados com informações básicas de servidores efetivos
     * atualmente lotados em uma unidade específica.
     */
    @Query(value = "SELECT new br.com.seplag.edital.dto.PessoaLotadaInfoDTO( " +
            "  p.id, p.nome, p.dataNascimento, u.unidNome " +
            ") " +
            "FROM Lotacao l " +
            "JOIN l.pessoa p " +
            "JOIN l.unidade u " +
            "JOIN ServidorEfetivo se ON se.pesId = p.id " +
            "WHERE u.unidId = :unidadeId " +
            "AND l.lotDataRemocao IS NULL",
            // Query separada para contar o total de elementos (otimização para paginação)
            countQuery = "SELECT COUNT(DISTINCT l.pessoa.id) " +
                    "FROM Lotacao l " +
                    "JOIN l.pessoa p " +
                    "JOIN l.unidade u " +
                    "JOIN ServidorEfetivo se ON se.pesId = p.id " +
                    "WHERE u.unidId = :unidadeId " +
                    "AND l.lotDataRemocao IS NULL")
    Page<PessoaLotadaInfoDTO> findServidoresEfetivosLotadosInfo( // <-- Retorna Page
                                                                 @Param("unidadeId") Integer unidadeId,
                                                                 Pageable pageable); // <-- Adiciona Pageable

}