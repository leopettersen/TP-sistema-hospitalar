package com.example.PortoesDoCeu.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import com.example.PortoesDoCeu.model.enums.SituacaoQuarto;

@Entity
public class Quarto {
    @Id 
    private int numero;

    private int capacidade_maxima;
    private SituacaoQuarto situacao;
    
    public Quarto(int numero, int capacidade_maxima, SituacaoQuarto situacao) {
        this.numero = numero;
        this.capacidade_maxima = capacidade_maxima;
        this.situacao = situacao;
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

    public SituacaoQuarto getSituacao() {
        return situacao;
    }

    public void setSituacao(SituacaoQuarto situacao) {
        this.situacao = situacao;
    }
    
}
