package com.example.PortoesDoCeu.model;

import java.util.UUID;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("CLINICO")
public class Clinico extends Profissional {

    protected Clinico() {
        super();
    }

    public Clinico(
            String registro,
            String nome,
            String telefone,
            String email,
            UUID agendaId) {

        super(registro, nome, telefone, email, agendaId);
    }
}
