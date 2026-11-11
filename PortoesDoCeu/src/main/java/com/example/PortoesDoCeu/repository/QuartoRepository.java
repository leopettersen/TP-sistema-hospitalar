package com.example.PortoesDoCeu.repository;

import com.example.PortoesDoCeu.model.Quarto;
import com.example.PortoesDoCeu.model.enums.SituacaoQuarto;
import com.example.PortoesDoCeu.model.enums.TipoQuarto;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import jakarta.persistence.LockModeType;
import java.util.Optional;

public interface QuartoRepository extends JpaRepository<Quarto, Integer> {

    List<Quarto> findBySituacao(SituacaoQuarto situacao);

    List<Quarto> findByTipoQuarto(TipoQuarto tipoQuarto);

    List<Quarto> findBySituacaoAndTipoQuarto(SituacaoQuarto situacao, TipoQuarto tipoQuarto);

    boolean existsByNumero(int numero);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select q from Quarto q where q.numero = :numero")
    Optional<Quarto> buscarPorNumeroComBloqueio(@Param("numero") int numero);
}