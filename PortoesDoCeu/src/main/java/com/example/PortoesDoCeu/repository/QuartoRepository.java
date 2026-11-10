package com.example.PortoesDoCeu.repository;

import com.example.PortoesDoCeu.model.Quarto;
import com.example.PortoesDoCeu.model.enums.SituacaoQuarto;
import com.example.PortoesDoCeu.model.enums.TipoQuarto;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface QuartoRepository extends JpaRepository<Quarto, Integer> {

    List<Quarto> findBySituacao(SituacaoQuarto situacao);

    List<Quarto> findByTipoQuarto(TipoQuarto tipoQuarto);

    List<Quarto> findBySituacaoAndTipoQuarto(SituacaoQuarto situacao, TipoQuarto tipoQuarto);

    boolean existsByNumero(int numero);
}