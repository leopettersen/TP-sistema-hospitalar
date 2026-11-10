package com.example.PortoesDoCeu.model;

import jakarta.persistence.Entity;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Cirurgia extends  Atendimento{
    private String descricao;
    private String tipo;
    private String obsPre;
    private LocalDateTime horarioEfetivo;

    public Cirurgia(UUID id, Paciente paciente, Profissional profissional, String observacoes, LocalDateTime horario, String descricao, String tipo, String obsPre, LocalDateTime horarioEfetivo) {
        super(id, paciente, profissional, observacoes, horario);
        this.descricao = descricao;
        this.tipo = tipo;
        this.obsPre = obsPre;
        this.horarioEfetivo = horarioEfetivo;
    }
}
