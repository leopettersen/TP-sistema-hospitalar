package com.example.PortoesDoCeu.model;

import java.util.UUID;

import jakarta.persistence.Entity;

@Entity
public class Especialista extends Profissional{

    private String especialidade;

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