package com.example.PortoesDoCeu.model;

import jakarta.persistence.Entity;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Especializada extends  Atendimento{
    private String especializacao;

    public Especializada(UUID id, Paciente paciente, Profissional profissional, String observacoes, LocalDateTime horario, String especializacao) {
        super(id, paciente, profissional, observacoes, horario);
        this.especializacao = especializacao;
    }
}
