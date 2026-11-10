package com.example.PortoesDoCeu.model;

import jakarta.persistence.Entity;

import java.time.LocalDateTime;
import java.util.UUID;

import com.example.PortoesDoCeu.model.enums.Prioridade;

@Entity
public class Emergencia extends Atendimento{
    private Prioridade prioridade;

    protected Emergencia() {
    }

    public Emergencia(UUID id, Paciente paciente, Profissional profissional, String observacoes, LocalDateTime horario, Prioridade prioridade) {
        super(id, paciente, profissional, observacoes, horario);
        this.prioridade = prioridade;
    }

    public Prioridade getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(Prioridade prioridade) {
        this.prioridade = prioridade;
    }
}
