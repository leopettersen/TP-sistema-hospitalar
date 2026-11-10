
package com.example.PortoesDoCeu.model;

import com.example.PortoesDoCeu.model.enums.StatusProcedimento;
import com.example.PortoesDoCeu.model.enums.TipoCirurgia;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Cirurgia extends Atendimento {

    private String descricao;

    @Enumerated(EnumType.STRING)
    private TipoCirurgia tipoCirurgia;

    private String observacoesPreOperatorias;

    private String observacoesPosOperatorias;

    private LocalDateTime dataHoraEfetivaInicio;

    private LocalDateTime dataHoraEfetivaFim;

    @Enumerated(EnumType.STRING)
    private StatusProcedimento status = StatusProcedimento.AGENDADO;

    // Construtor exigido pelo JPA
    public Cirurgia() {
        super();
    }

    public Cirurgia(
            UUID id,
            Paciente paciente,
            Profissional profissional,
            String observacoes,
            LocalDateTime horario,
            String descricao,
            TipoCirurgia tipoCirurgia,
            String observacoesPreOperatorias
    ) {
        super(id, paciente, profissional, observacoes, horario);

        this.descricao = descricao;
        this.tipoCirurgia = tipoCirurgia;
        this.observacoesPreOperatorias = observacoesPreOperatorias;
        this.status = StatusProcedimento.AGENDADO;
    }

    // Valida os dados do agendamento
    public void agendar() {

        if (getPaciente() == null) {
            throw new IllegalArgumentException("O paciente é obrigatório.");
        }

        if (getProfissional() == null) {
            throw new IllegalArgumentException("O cirurgião é obrigatório.");
        }

        if (getHorario() == null) {
            throw new IllegalArgumentException("O horário previsto é obrigatório.");
        }

        if (descricao == null || descricao.isBlank()) {
            throw new IllegalArgumentException("A descrição é obrigatória.");
        }

        if (tipoCirurgia == null) {
            throw new IllegalArgumentException("O tipo da cirurgia é obrigatório.");
        }

        if (status != StatusProcedimento.AGENDADO) {
            throw new IllegalStateException("O procedimento não pode ser agendado neste estado.");
        }
    }

    // Inicia o procedimento
    public void iniciar() {

        if (status != StatusProcedimento.AGENDADO) {
            throw new IllegalStateException(
                    "Somente procedimentos agendados podem ser iniciados."
            );
        }

        this.dataHoraEfetivaInicio = LocalDateTime.now();
        this.status = StatusProcedimento.EM_ANDAMENTO;
    }

    // Finaliza o procedimento
    public void concluir() {

        if (status != StatusProcedimento.EM_ANDAMENTO) {
            throw new IllegalStateException(
                    "O procedimento precisa ser iniciado antes de ser concluído."
            );
        }

        this.dataHoraEfetivaFim = LocalDateTime.now();
        this.status = StatusProcedimento.CONCLUIDO;
    }

    // Cancela o procedimento
    public void cancelar() {

        if (status != StatusProcedimento.AGENDADO) {
            throw new IllegalStateException(
                    "Somente procedimentos agendados podem ser cancelados."
            );
        }

        this.status = StatusProcedimento.CANCELADO;
    }

    // Getters

    public String getDescricao() {
        return descricao;
    }

    public TipoCirurgia getTipoCirurgia() {
        return tipoCirurgia;
    }

    public String getObservacoesPreOperatorias() {
        return observacoesPreOperatorias;
    }

    public String getObservacoesPosOperatorias() {
        return observacoesPosOperatorias;
    }

    public LocalDateTime getDataHoraEfetivaInicio() {
        return dataHoraEfetivaInicio;
    }

    public LocalDateTime getDataHoraEfetivaFim() {
        return dataHoraEfetivaFim;
    }

    public StatusProcedimento getStatus() {
        return status;
    }

    public void setObservacoesPosOperatorias(String observacoesPosOperatorias) {
        this.observacoesPosOperatorias = observacoesPosOperatorias;
    }
}
