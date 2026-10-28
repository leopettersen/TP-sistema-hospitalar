package com.example.PortoesDoCeu.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Entity;

@Entity
public class Internacao extends Atendimento{

    private int numeroQuarto;
    private LocalDate alta_prevista; 
    private LocalDateTime data_alta;

    public Internacao(UUID id, Paciente paciente, Profissional profissional, String observacoes, LocalDateTime horario,
            int numeroQuarto, LocalDate alta_prevista, LocalDateTime data_alta) {
        super(id, paciente, profissional, observacoes, horario);
        this.numeroQuarto = numeroQuarto;
        this.alta_prevista = alta_prevista;
        this.data_alta = data_alta;
    }

    public int getQuarto() {
        return numeroQuarto;
    }

    public void setQuarto(int numeroQuarto) {
        this.numeroQuarto = numeroQuarto;
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
