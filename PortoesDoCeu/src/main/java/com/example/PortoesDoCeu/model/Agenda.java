package com.example.PortoesDoCeu.model;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;

@Entity
public class Agenda {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Transient
    private Map<LocalDateTime, Atendimento> agenda = new HashMap<>();

    public Agenda(UUID id) {
        this.id = id;
        agenda = new HashMap<>();
    }

    public UUID getId() {
        return id;
    }

    public Map<LocalDateTime, Atendimento> getAgenda() {
        return agenda;
    }

    public void setAgenda(Map<LocalDateTime, Atendimento> agenda) {
        this.agenda = agenda;
    }

    public void addAtendimento(LocalDateTime data, Atendimento atendimento) {
        this.agenda.put(data, atendimento);
    }
}