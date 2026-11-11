package com.example.PortoesDoCeu.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.example.PortoesDoCeu.model.enums.EstagioEmergencia;
import com.example.PortoesDoCeu.model.enums.Prioridade;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Entity
public class Emergencia extends Atendimento {

    @Enumerated(EnumType.STRING)
    private Prioridade prioridade;

    @Enumerated(EnumType.STRING)
    private EstagioEmergencia estagio = EstagioEmergencia.AGUARDANDO;

    protected Emergencia() {
    }

    public Emergencia(
            UUID id,
            Paciente paciente,
            Profissional profissional,
            String observacoes,
            LocalDateTime horario,
            Prioridade prioridade) {

        super(id, paciente, profissional, observacoes, horario);
        if (prioridade == null) {
            throw new IllegalArgumentException("A prioridade é obrigatória.");
        }
        this.prioridade = prioridade;
    }

    private void mudarEstagio(EstagioEmergencia novo) {
        if (!estagio.podeMudarPara(novo)) {
            throw new IllegalStateException(
                    "Transição inválida de estágio: " + estagio + " para " + novo);
        }
        this.estagio = novo;
    }

    public void iniciarAtendimento() {
        if (getProfissional() == null) {
            throw new IllegalStateException(
                    "Defina um profissional antes de iniciar o atendimento.");
        }
        mudarEstagio(EstagioEmergencia.INICIADO);
    }

    public void concluirAtendimento() {
        mudarEstagio(EstagioEmergencia.CONCLUIDO);
    }

    public Prioridade getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(Prioridade prioridade) {
        if (prioridade == null) {
            throw new IllegalArgumentException("A prioridade é obrigatória.");
        }
        this.prioridade = prioridade;
    }

    public EstagioEmergencia getEstagio() {
        return estagio;
    }
}
