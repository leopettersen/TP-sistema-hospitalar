package com.example.PortoesDoCeu.model;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Entity;

@Entity
public class Consulta extends Atendimento {
//alteracoes feitas em pro da consulta das subclasses consulta rotina e especializada

    protected Consulta() {
        super();
    }

    // id vai null porque é gerado pelo banco
    protected Consulta(Paciente paciente, Profissional profissional,
            String observacoes, LocalDateTime horario) {
        super(null, paciente, profissional, observacoes, horario);
    }  
}
