
package com.example.PortoesDoCeu.model;

import java.time.LocalDateTime;

import com.example.PortoesDoCeu.model.enums.StatusProcedimento;
import com.example.PortoesDoCeu.model.enums.TipoCirurgia;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Entity
@DiscriminatorValue("PROCEDIMENTO_CIRURGICO")
public class ProcedimentoCirurgico extends Atendimento {

    @Column(nullable = false)
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoCirurgia tipoCirurgia;

    private String observacoesPreOperatorias;

    private String observacoesPosOperatorias;

    private LocalDateTime dataHoraEfetivaInicio;

    private LocalDateTime dataHoraEfetivaFim;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusProcedimento status = StatusProcedimento.AGENDADO;

    // Construtor vazio exigido pelo JPA
    protected ProcedimentoCirurgico() {
        super();
    }

    // Construtor com os dados do procedimento
    public ProcedimentoCirurgico(
            Paciente paciente,
            Cirurgiao cirurgiao,
            String descricao,
            TipoCirurgia tipoCirurgia,
            LocalDateTime dataHoraPrevista
    ) {
        super(null, paciente, cirurgiao, null, dataHoraPrevista);

        if (paciente == null) {
            throw new IllegalArgumentException("O paciente é obrigatório.");
        }

        if (cirurgiao == null) {
            throw new IllegalArgumentException("O cirurgião é obrigatório.");
        }

        if (descricao == null || descricao.isBlank()) {
            throw new IllegalArgumentException("A descrição é obrigatória.");
        }

        if (tipoCirurgia == null) {
            throw new IllegalArgumentException("O tipo de cirurgia é obrigatório.");
        }

        if (dataHoraPrevista == null) {
            throw new IllegalArgumentException("A data prevista é obrigatória.");
        }

        this.descricao = descricao;
        this.tipoCirurgia = tipoCirurgia;
        this.status = StatusProcedimento.AGENDADO;
    }

    // Agenda ou atualiza a data prevista
    public void agendar(LocalDateTime dataHoraPrevista) {

        if (status == StatusProcedimento.CANCELADO ||
                status == StatusProcedimento.CONCLUIDO ||
                status == StatusProcedimento.EM_ANDAMENTO) {

            throw new IllegalStateException(
                    "Não é possível agendar novamente este procedimento."
            );
        }

        if (dataHoraPrevista == null) {
            throw new IllegalArgumentException("A data prevista é obrigatória.");
        }

        setHorario(dataHoraPrevista);
        status = StatusProcedimento.AGENDADO;
    }

    // Inicia o procedimento
    public void iniciar() {

        if (status != StatusProcedimento.AGENDADO) {
            throw new IllegalStateException(
                    "Somente procedimentos agendados podem ser iniciados."
            );
        }

        dataHoraEfetivaInicio = LocalDateTime.now();
        status = StatusProcedimento.EM_ANDAMENTO;
    }

    // Conclui o procedimento
    public void concluir() {

        if (status != StatusProcedimento.EM_ANDAMENTO) {
            throw new IllegalStateException(
                    "Não é possível concluir um procedimento que não foi iniciado."
            );
        }

        dataHoraEfetivaFim = LocalDateTime.now();

        if (dataHoraEfetivaFim.isBefore(dataHoraEfetivaInicio)) {
            throw new IllegalStateException(
                    "A data de conclusão não pode ser anterior ao início."
            );
        }

        status = StatusProcedimento.CONCLUIDO;
    }

    // Cancela o procedimento
    public void cancelar() {

        if (status != StatusProcedimento.AGENDADO) {
            throw new IllegalStateException(
                    "Somente procedimentos agendados podem ser cancelados."
            );
        }

        status = StatusProcedimento.CANCELADO;
    }

    // Getters e setters

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public TipoCirurgia getTipoCirurgia() {
        return tipoCirurgia;
    }

    public void setTipoCirurgia(TipoCirurgia tipoCirurgia) {
        this.tipoCirurgia = tipoCirurgia;
    }

    public String getObservacoesPreOperatorias() {
        return observacoesPreOperatorias;
    }

    public void setObservacoesPreOperatorias(String observacoesPreOperatorias) {
        this.observacoesPreOperatorias = observacoesPreOperatorias;
    }

    public String getObservacoesPosOperatorias() {
        return observacoesPosOperatorias;
    }

    public void setObservacoesPosOperatorias(String observacoesPosOperatorias) {
        this.observacoesPosOperatorias = observacoesPosOperatorias;
    }

    public Cirurgiao getCirurgiao() {
        return (Cirurgiao) getProfissional();
    }

    public LocalDateTime getDataHoraPrevista() {
        return getHorario();
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
}
