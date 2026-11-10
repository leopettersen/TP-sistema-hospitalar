
package com.example.PortoesDoCeu.model;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Entity;

@Entity
public class Especializada extends Atendimento {

    private String especializacao;

    protected Especializada() {
        super();
    }

    public Especializada(
            UUID id,
            Paciente paciente,
            Profissional profissional,
            String observacoes,
            LocalDateTime horario,
            String especializacao) {

        super(id, paciente, profissional, observacoes, horario);
        this.especializacao = especializacao;
    }

    public String getEspecializacao() {
        return especializacao;
    }

    public void setEspecializacao(String especializacao) {
        this.especializacao = especializacao;
    }
}
