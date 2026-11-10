package com.example.PortoesDoCeu.model;

import jakarta.persistence.Entity;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Rotina extends Atendimento{
    private String motivo;

    protected Rotina() {
    }

    public Rotina(UUID id, Paciente paciente, Profissional profissional, String observacoes, LocalDateTime horario, String motivo) {
        super(id, paciente, profissional, observacoes, horario);
        this.motivo = motivo;
    }
}
