package com.example.PortoesDoCeu.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.example.PortoesDoCeu.model.enums.StatusConsulta;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Entity
public abstract class Consulta extends Atendimento {

    @Enumerated(EnumType.STRING)
    private StatusConsulta statusConsulta = StatusConsulta.PENDENTE;

    protected Consulta() {
    }

    protected Consulta(
            UUID id,
            Paciente paciente,
            Profissional profissional,
            String observacoes,
            LocalDateTime horario) {

        super(id, paciente, profissional, observacoes, horario);
    }

    private void transicionarStatus(StatusConsulta statusFinal) {
        if (!statusConsulta.podeMudarStatus(statusFinal)) {
            throw new IllegalStateException(
                    "Transição inválida de " + statusConsulta + " para " + statusFinal);
        }
        this.statusConsulta = statusFinal;
    }

    public void agendarConsulta() {
        if (getPaciente() == null || getProfissional() == null || getHorario() == null) {
            throw new IllegalStateException(
                    "Paciente, profissional e horário são obrigatórios");
        }
        if (getHorario().isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Horário inválido (passado)");
        }
        transicionarStatus(StatusConsulta.AGENDADA);
    }

    public void cancelarConsulta() {
        transicionarStatus(StatusConsulta.CANCELADA);
    }

    public void confirmarConsulta() {
        transicionarStatus(StatusConsulta.CONFIRMADA);
    }

    public void comecarConsulta() {
        transicionarStatus(StatusConsulta.EM_ANDAMENTO);
    }

    public void concluirConsulta() {
        transicionarStatus(StatusConsulta.CONCLUIDA);
    }

    public void desistiuConsulta() {
        transicionarStatus(StatusConsulta.NAO_COMPARECEU);
    }

    public StatusConsulta getStatusConsulta() {
        return statusConsulta;
    }
}
