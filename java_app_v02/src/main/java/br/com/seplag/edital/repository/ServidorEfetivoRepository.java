package br.com.seplag.edital.repository;

import br.com.seplag.edital.model.ServidorEfetivo;
import br.com.seplag.edital.model.ServidorEfetivoId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServidorEfetivoRepository extends JpaRepository<ServidorEfetivo, ServidorEfetivoId> {
}