package com.example.PortoesDoCeu.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;

@Entity
public class ConsultaRotina extends Atendimento {

    private String motivo;

    // Preenchido só na conclusão da consulta
    private String observacoesPosConsulta;

    // Construtor vazio exigido pelo JPA
    protected ConsultaRotina() {
        super();
    }

    // O id é gerado pelo banco, então vai null para o construtor da superclasse
    public ConsultaRotina(Paciente paciente, Profissional profissional,
            String observacoes, LocalDateTime horario, String motivo) {
        super(null, paciente, profissional, observacoes, horario);
        validarMotivo(motivo);
        this.motivo = motivo;
    }

    // Regra específica: consulta de rotina sempre precisa de um motivo
    private void validarMotivo(String motivo) {
        if (motivo == null || motivo.isBlank()) {
            throw new IllegalArgumentException("O motivo da consulta é obrigatório");
        }
    }

    // Sobrecarga: concluir exigindo as observações pós-consulta
    public void concluirConsulta(String observacoesPosConsulta) {
        if (observacoesPosConsulta == null || observacoesPosConsulta.isBlank()) {
            throw new IllegalArgumentException(
                    "As observações pós-consulta são obrigatórias para concluir");
        }
        // Primeiro faz a transição: se for inválida, lança exceção
        // e as observações não chegam a ser gravadas
        super.concluirConsulta();
        this.observacoesPosConsulta = observacoesPosConsulta;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        validarMotivo(motivo);
        this.motivo = motivo;
    }

    public String getObservacoesPosConsulta() {
        return observacoesPosConsulta;
    }
}