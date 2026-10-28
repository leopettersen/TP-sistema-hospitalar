package com.example.PortoesDoCeu.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Quarto {
    @Id 
    private int numero;

    private int capacidade_maxima;
    private boolean situacao_atual;
    
    public Quarto(int numero, int capacidade_maxima, boolean situacao_atual) {
        this.numero = numero;
        this.capacidade_maxima = capacidade_maxima;
        this.situacao_atual = situacao_atual;
    }

    public int getNumero() {
        return numero;
    }

    public int getCapacidade_maxima() {
        return capacidade_maxima;
    }

    public void setCapacidade_maxima(int capacidade_maxima) {
        this.capacidade_maxima = capacidade_maxima;
    }

    public boolean isSituacao_atual() {
        return situacao_atual;
    }

    public void setSituacao_atual(boolean situacao_atual) {
        this.situacao_atual = situacao_atual;
    }
    
}
