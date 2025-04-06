package br.com.seplag.edital.repository;

import br.com.seplag.edital.model.Cidade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Integer> {

    Page<Cidade> findByNomeIgnoreCaseAndEstadoIgnoreCase(String nome, String estado, Pageable pageable);
    Page<Cidade> findByNomeIgnoreCase(String nome, Pageable pageable);
    Page<Cidade> findByEstadoIgnoreCase(String estado, Pageable pageable);
    // Nota: findAll(Pageable) já existe e será usado se nome e estado forem nulos/vazios

    // Opção 2: Usando @Query (Mais flexível e explícito, recomendado para múltiplos opcionais)
    // Esta query lida com parâmetros opcionais: se nome/estado forem null ou vazios, a condição é ignorada.
    @Query("SELECT c FROM Cidade c WHERE " +
            "(:nome IS NULL OR :nome = '' OR lower(c.nome) = lower(:nome)) " + // Se nome for nulo/vazio, ignora; senão compara case-insensitive
            "AND (:estado IS NULL OR :estado = '' OR lower(c.estado) = lower(:estado))") // Se estado for nulo/vazio, ignora; senão compara case-insensitive
    Page<Cidade> findByNomeAndEstadoOptional( // Nome mais descritivo
                                              @Param("nome") String nome,
                                              @Param("estado") String estado,
                                              Pageable pageable); // Pageable aplica paginação e ordenação à query

}