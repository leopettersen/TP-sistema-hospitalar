package com.example.PortoesDoCeu.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Internacao extends Atendimento{

    @ManyToOne
    @JoinColumn(name = "numero_quarto")
    private Quarto quarto;
    private LocalDate alta_prevista; 
    private LocalDateTime data_alta;

    public Internacao(UUID id, Paciente paciente, Profissional profissional, String observacoes, LocalDateTime horario,
            Quarto quarto, LocalDate alta_prevista, LocalDateTime data_alta) {
        super(id, paciente, profissional, observacoes, horario);
        this.quarto = quarto;
        this.alta_prevista = alta_prevista;
        this.data_alta = data_alta;
    }

    public Quarto getQuarto() {
        return quarto;
    }

    public void setQuarto(Quarto quarto) {
        this.quarto = quarto;
    }

    public LocalDate getAlta_prevista() {
        return alta_prevista;
    }

    public void setAlta_prevista(LocalDate alta_prevista) {
        this.alta_prevista = alta_prevista;
    }

    public LocalDateTime getData_alta() {
        return data_alta;
    }

    public void setData_alta(LocalDateTime data_alta) {
        this.data_alta = data_alta;
    }
}
