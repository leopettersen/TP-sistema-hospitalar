package com.example.PortoesDoCeu.repository;

import com.example.PortoesDoCeu.model.Internacao;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InternacaoRepository extends JpaRepository<Internacao, UUID> {

    List<Internacao> findByDataAltaIsNull();

    List<Internacao> findByPacienteCpfAndDataAltaIsNull(String cpf);

    List<Internacao> findByQuartoNumeroAndDataAltaIsNull(int numeroQuarto);

    long countByQuartoNumeroAndDataAltaIsNull(int numeroQuarto);
}