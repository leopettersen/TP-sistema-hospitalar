
package com.example.PortoesDoCeu.model;

import java.util.UUID;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("CLINICO")
public class MedicoClinicoGeral extends Profissional {

    protected MedicoClinicoGeral() {
        super();
    }

    public MedicoClinicoGeral(
            String registro,
            String nome,
            String telefone,
            String email,
            UUID agendaId) {

        super(registro, nome, telefone, email, agendaId);
    }

    public MedicoEspecialista encaminharPaciente(
            Paciente paciente,
            MedicoEspecialista especialista) {

        if (paciente == null) {
            throw new IllegalArgumentException(
                "O paciente é obrigatório."
            );
        }

        if (especialista == null) {
            throw new IllegalArgumentException(
                "O médico especialista é obrigatório."
            );
        }

        return especialista;
    }

    public boolean podeRealizarConsultaGeral(
            Consulta consulta) {

        return consulta != null
            && consulta.getProfissional() != null
            && getRegistro().equals(
                consulta.getProfissional().getRegistro()
            );
    }
}
