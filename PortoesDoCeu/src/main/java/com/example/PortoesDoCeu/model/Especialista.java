package com.example.PortoesDoCeu.model;

import java.util.UUID;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("ESPECIALISTA")
public class Especialista extends Profissional{

    private String especialidade;

    protected Especialista() {
    }

    public Especialista(String registro, String nome, String especialidade, String telefone, String email,
            UUID agendaId) {
        super(registro, nome, telefone, email, agendaId);
        this.especialidade = especialidade;
    }

        public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }
    
}