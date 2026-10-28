package com.example.PortoesDoCeu.model;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Entity;

@Entity
public class Consulta extends Atendimento {

    private String motivo;

    public Consulta(UUID id, Paciente paciente, Profissional profissional, String observacoes, LocalDateTime horario,
            String motivo) {
        super(id, paciente, profissional, observacoes, horario);
        this.motivo = motivo;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }    
}
