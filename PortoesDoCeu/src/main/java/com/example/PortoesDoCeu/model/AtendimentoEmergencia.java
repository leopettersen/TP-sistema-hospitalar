package com.example.PortoesDoCeu.model;

import java.time.LocalDateTime;

import com.example.PortoesDoCeu.model.enums.EstagioEmergencia;
import com.example.PortoesDoCeu.model.enums.PrioridadeEmergencia;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Entity
public class AtendimentoEmergencia extends Atendimento {

    @Enumerated(EnumType.STRING)
    private PrioridadeEmergencia prioridade;

    @Enumerated(EnumType.STRING)
    private EstagioEmergencia estagio = EstagioEmergencia.AGUARDANDO;

    protected AtendimentoEmergencia() {
        super();
    }

    // Emergência não é agendada: o horário é o momento da chegada.
    // O profissional pode ser null enquanto o paciente aguarda
    public AtendimentoEmergencia(Paciente paciente, Profissional profissional,
            String observacoes, PrioridadeEmergencia prioridade) {
        super(null, paciente, profissional, observacoes, LocalDateTime.now());
        if (prioridade == null) {
            throw new IllegalArgumentException("A prioridade é obrigatória");
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
        // Não dá para iniciar sem alguém para atender
        if (getProfissional() == null) {
            throw new IllegalStateException("Defina um profissional antes de iniciar");
        }
        mudarEstagio(EstagioEmergencia.INICIADO);
    }

    public void concluirAtendimento() {
        mudarEstagio(EstagioEmergencia.CONCLUIDO);
    }

    public PrioridadeEmergencia getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(PrioridadeEmergencia prioridade) {
        this.prioridade = prioridade;
    }

    public EstagioEmergencia getEstagio() {
        return estagio;
    }
}