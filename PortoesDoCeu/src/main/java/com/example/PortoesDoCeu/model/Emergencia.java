package com.example.PortoesDoCeu.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Emergencia extends Atendimento{
    private Prioridade prioridade;

    public Emergencia(UUID id, Paciente paciente, Profissional profissional, String observacoes, LocalDateTime horario, Prioridade prioridade) {
        super(id, paciente, profissional, observacoes, horario);
        this.prioridade = prioridade;
    }
}
