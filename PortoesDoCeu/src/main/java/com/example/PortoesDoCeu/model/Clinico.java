package com.example.PortoesDoCeu.model;

import java.util.UUID;

import jakarta.persistence.Entity;

@Entity 
public class Clinico extends Profissional {

    public Clinico(String registro, String nome, String telefone, String email, UUID agendaId) {
        super(registro, nome, telefone, email, agendaId);
    }
    
}
