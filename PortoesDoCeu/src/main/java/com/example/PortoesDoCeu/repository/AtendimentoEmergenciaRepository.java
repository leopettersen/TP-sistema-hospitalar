package com.example.PortoesDoCeu.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.PortoesDoCeu.model.AtendimentoEmergencia;
import com.example.PortoesDoCeu.model.enums.EstagioEmergencia;
import com.example.PortoesDoCeu.model.enums.PrioridadeEmergencia;

public interface AtendimentoEmergenciaRepository
        extends JpaRepository<AtendimentoEmergencia, UUID> {

    List<AtendimentoEmergencia> findByEstagio(EstagioEmergencia estagio);

    List<AtendimentoEmergencia> findByPrioridade(PrioridadeEmergencia prioridade);
}