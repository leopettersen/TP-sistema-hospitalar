package com.example.PortoesDoCeu.model;

import java.time.LocalDateTime;

import org.hibernate.Hibernate;

import jakarta.persistence.Entity;

@Entity
public class ConsultaEspecializada extends Consulta {

    private String especialidade;

    protected ConsultaEspecializada() {
        super();
    }

    public ConsultaEspecializada(Paciente paciente, Profissional profissional, String observacoes, LocalDateTime horario, String especialidade) {
        super(paciente, profissional, observacoes, horario);
        validarCompatibilidade(profissional, especialidade);
        this.especialidade = especialidade;
    }

    //o profissional precisa ser Especialista e ter a mesma especialidade.
    // É static para poder ser chamado também no construtor
    private static void validarCompatibilidade(Profissional profissional, String especialidade) {
        if (especialidade == null || especialidade.isBlank()) {
            throw new IllegalArgumentException("A especialidade é obrigatória");
        }
        Object real = Hibernate.unproxy(profissional);
        if (!(real instanceof Especialista especialista)) {
            throw new IllegalArgumentException(
                    "Consulta especializada exige um profissional Especialista");
        }
        if (!especialista.getEspecialidade().equalsIgnoreCase(especialidade)) {
            throw new IllegalArgumentException("Especialidade incompatível: o profissional é de "
                    + especialista.getEspecialidade() + ", a consulta é de " + especialidade);
        }
    }

    // Revalida ao trocar o profissional, senão o setter herdado burlaria a regra
    @Override
    public void setProfissional(Profissional profissional) {
        validarCompatibilidade(profissional, especialidade);
        super.setProfissional(profissional);
    }

    public void setEspecialidade(String especialidade) {
        validarCompatibilidade(getProfissional(), especialidade);
        this.especialidade = especialidade;
    }

    public String getEspecialidade() {
        return especialidade;
    }
}