package br.com.seplag.edital.repository;

import br.com.seplag.edital.model.ServidorTemporario;
import br.com.seplag.edital.model.ServidorTemporarioId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServidorTemporarioRepository extends JpaRepository<ServidorTemporario, ServidorTemporarioId> {
}