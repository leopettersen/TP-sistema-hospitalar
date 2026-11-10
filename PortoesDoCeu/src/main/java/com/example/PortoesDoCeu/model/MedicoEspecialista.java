
package com.example.PortoesDoCeu.model;

import java.util.UUID;

import com.example.PortoesDoCeu.model.enums.EspecialidadeMedica;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("ESPECIALISTA")
public class MedicoEspecialista extends Profissional {

    @Enumerated(EnumType.STRING)
    @Column(name = "especialidade")
    private EspecialidadeMedica especialidade;

    protected MedicoEspecialista() {
        super();
    }

    public MedicoEspecialista(
            String registro,
            String nome,
            String telefone,
            String email,
            UUID agendaId,
            EspecialidadeMedica especialidade) {

        super(registro, nome, telefone, email, agendaId);
        setEspecialidade(especialidade);
    }

    public EspecialidadeMedica getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(
            EspecialidadeMedica especialidade) {

        if (especialidade == null) {
            throw new IllegalArgumentException(
                "A especialidade médica é obrigatória."
            );
        }

        this.especialidade = especialidade;
    }

    public boolean podeRealizarConsultaEspecializada(
            Especializada consulta) {

        if (consulta == null
                || consulta.getProfissional() == null
                || consulta.getEspecializacao() == null) {
            return false;
        }

        boolean mesmoProfissional =
            getRegistro().equals(
                consulta.getProfissional().getRegistro()
            );

        boolean especialidadeCompativel =
            especialidade.name().equalsIgnoreCase(
                consulta.getEspecializacao().trim()
            );

        return mesmoProfissional
            && especialidadeCompativel;
    }
}
