package com.example.PortoesDoCeu.model;

import java.util.ArrayList;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Historico {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @OneToMany
    private ArrayList<Atendimento> atendimentos;
    private ArrayList<String> informacoes_relevantes;

    public Historico(UUID id) {
        this.id = id;
        atendimentos = new ArrayList<>();
        informacoes_relevantes = new ArrayList<>();
    }

    public UUID getId() {
        return id;
    }

    public ArrayList<Atendimento> getAtendimentos() {
        return atendimentos;
    }

    public void setAtendimentos(ArrayList<Atendimento> atendimentos) {
        this.atendimentos = atendimentos;
    }

    public ArrayList<String> getInformacoes_relevantes() {
        return informacoes_relevantes;
    }

    public void setInformacoes_relevantes(ArrayList<String> informacoes_relevantes) {
        this.informacoes_relevantes = informacoes_relevantes;
    }
    
}
