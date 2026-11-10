package com.example.PortoesDoCeu.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import com.example.PortoesDoCeu.model.enums.SituacaoQuarto;
import com.example.PortoesDoCeu.model.enums.TipoQuarto;

@Entity
public class Quarto {
    @Id
    private int numero;

    private int capacidadeMaxima;

    @Enumerated(EnumType.STRING)
    private SituacaoQuarto situacao;

    @Enumerated(EnumType.STRING)
    private TipoQuarto tipoQuarto;

    public Quarto() {
    }

    public Quarto(int numero, int capacidadeMaxima, SituacaoQuarto situacao, TipoQuarto tipoQuarto) {
        if (numero <= 0) {
            throw new IllegalArgumentException("O número do quarto deve ser positivo.");
        }
        if (capacidadeMaxima <= 0) {
            throw new IllegalArgumentException("A capacidade máxima deve ser positiva.");
        }
        if (situacao == null) {
            throw new IllegalArgumentException("A situação do quarto é obrigatória.");
        }
        if (tipoQuarto == null) {
            throw new IllegalArgumentException("O tipo do quarto é obrigatório.");
        }

        this.numero = numero;
        this.capacidadeMaxima = capacidadeMaxima;
        this.situacao = situacao;
        this.tipoQuarto = tipoQuarto;
    }

    public int getNumero() {
        return numero;
    }

    public int getCapacidadeMaxima() {
        return capacidadeMaxima;
    }

    public void setCapacidadeMaxima(int capacidadeMaxima) {
        if (capacidadeMaxima <= 0) {
            throw new IllegalArgumentException("A capacidade máxima deve ser positiva.");
        }
        this.capacidadeMaxima = capacidadeMaxima;
    }

    public SituacaoQuarto getSituacao() {
        return situacao;
    }

    public void setSituacao(SituacaoQuarto situacao) {
        if (situacao == null) {
            throw new IllegalArgumentException("A situação do quarto é obrigatória.");
        }
        this.situacao = situacao;
    }

    public TipoQuarto getTipoQuarto() {
        return tipoQuarto;
    }

    public void setTipoQuarto(TipoQuarto tipoQuarto) {
        if (tipoQuarto == null) {
            throw new IllegalArgumentException("O tipo do quarto é obrigatório.");
        }
        this.tipoQuarto = tipoQuarto;
    }

    public boolean estaDisponivel() {
        return situacao == SituacaoQuarto.LIVRE;
    }

    public boolean temCapacidade(long ocupados) {
        return ocupados >= 0 && ocupados < capacidadeMaxima;
    }

    public void ocuparQuarto() {
        if (!estaDisponivel()) {
            throw new IllegalStateException(
                    "O quarto " + numero + " não está disponível para ocupação.");
        }
        setSituacao(SituacaoQuarto.OCUPADO);
    }

    public void liberarQuarto() {
        if (situacao != SituacaoQuarto.OCUPADO) {
            throw new IllegalStateException(
                    "Só é possível liberar um quarto ocupado.");
        }
        setSituacao(SituacaoQuarto.EM_LIMPEZA);
    }

    public void terminarLimpeza() {
        if (situacao != SituacaoQuarto.EM_LIMPEZA) {
            throw new IllegalStateException(
                    "Só é possível concluir a limpeza de um quarto em limpeza.");
        }
        setSituacao(SituacaoQuarto.LIVRE);
    }

    public void iniciarManutencao() {
        if (!estaDisponivel()) {
            throw new IllegalStateException(
                    "Só é possível iniciar manutenção em um quarto livre.");
        }
        setSituacao(SituacaoQuarto.MANUTENCAO);
    }

    public void terminarManutencao() {
        if (situacao != SituacaoQuarto.MANUTENCAO) {
            throw new IllegalStateException(
                    "Só é possível concluir a manutenção de um quarto em manutenção.");
        }
        setSituacao(SituacaoQuarto.LIVRE);
    }
}
