package com.example.PortoesDoCeu.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@DiscriminatorValue("INTERNACAO")
public class Internacao extends Atendimento {

    @ManyToOne
    @JoinColumn(name = "numero_quarto")
    private Quarto quarto;

    @Column(name = "alta_prevista")
    private LocalDate altaPrevista;

    @Column(name = "data_alta")
    private LocalDateTime dataAlta;

    protected Internacao() {
    }

    public Internacao(
            UUID id,
            Paciente paciente,
            Profissional profissional,
            String observacoes,
            LocalDateTime horario,
            Quarto quarto,
            LocalDate altaPrevista) {

        super(id, paciente, profissional, observacoes, horario);
        validarDados(paciente, horario, quarto, altaPrevista);

        this.quarto = quarto;
        this.altaPrevista = altaPrevista;
    }

    public Internacao(
            Paciente paciente,
            Profissional profissional,
            String observacoes,
            LocalDateTime horario,
            Quarto quarto,
            LocalDate altaPrevista) {

        this(null, paciente, profissional, observacoes, horario, quarto, altaPrevista);
    }

    private static void validarDados(
            Paciente paciente,
            LocalDateTime horario,
            Quarto quarto,
            LocalDate altaPrevista) {

        if (paciente == null) {
            throw new IllegalArgumentException("Paciente é obrigatório para internação.");
        }
        if (horario == null) {
            throw new IllegalArgumentException("Horário de entrada é obrigatório.");
        }
        if (quarto == null) {
            throw new IllegalArgumentException("Quarto é obrigatório para internação.");
        }
        if (altaPrevista == null) {
            throw new IllegalArgumentException("Alta prevista é obrigatória.");
        }
        if (altaPrevista.isBefore(horario.toLocalDate())) {
            throw new IllegalArgumentException(
                    "Alta prevista não pode ser anterior à data de entrada.");
        }
    }

    public void internar() {
        if (!estaAtiva()) {
            throw new IllegalStateException("Internação já encerrada.");
        }
        if (!quarto.estaDisponivel()) {
            throw new IllegalStateException(
                    "Quarto " + quarto.getNumero() + " não está disponível para internação.");
        }
        quarto.ocuparQuarto();
    }

    public void registrarAlta(LocalDateTime dataAlta) {
        if (dataAlta == null) {
            throw new IllegalArgumentException("Data de alta é obrigatória.");
        }
        if (!estaAtiva()) {
            throw new IllegalStateException("Internação já foi encerrada.");
        }
        if (dataAlta.isBefore(getHorario())) {
            throw new IllegalArgumentException(
                    "Data de alta não pode ser anterior à data de entrada.");
        }

        this.dataAlta = dataAlta;
        quarto.liberarQuarto();
    }

    public void transferirQuarto(Quarto novoQuarto) {
        if (novoQuarto == null) {
            throw new IllegalArgumentException("Novo quarto é obrigatório.");
        }
        if (!estaAtiva()) {
            throw new IllegalStateException(
                    "Não é possível transferir uma internação encerrada.");
        }
        if (novoQuarto.getNumero() == quarto.getNumero()) {
            throw new IllegalArgumentException("O paciente já está neste quarto.");
        }
        if (!novoQuarto.estaDisponivel()) {
            throw new IllegalStateException(
                    "Quarto " + novoQuarto.getNumero() + " não está disponível.");
        }

        Quarto quartoAnterior = this.quarto;
        novoQuarto.ocuparQuarto();
        this.quarto = novoQuarto;
        quartoAnterior.liberarQuarto();
    }

    public void reagendarAlta(LocalDate novaAltaPrevista) {
        if (novaAltaPrevista == null) {
            throw new IllegalArgumentException("Alta prevista é obrigatória.");
        }
        if (!estaAtiva()) {
            throw new IllegalStateException("Internação já encerrada.");
        }
        if (novaAltaPrevista.isBefore(getHorario().toLocalDate())) {
            throw new IllegalArgumentException(
                    "Alta prevista não pode ser anterior à data de entrada.");
        }
        this.altaPrevista = novaAltaPrevista;
    }

    public boolean estaAtiva() {
        return dataAlta == null;
    }

    public Quarto getQuarto() {
        return quarto;
    }

    public LocalDate getAltaPrevista() {
        return altaPrevista;
    }

    public LocalDateTime getDataAlta() {
        return dataAlta;
    }
}
