
package com.example.PortoesDoCeu.model;

import java.util.UUID;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("CIRURGIAO")
public class Cirurgiao extends Profissional {

    protected Cirurgiao() {
        super();
    }

    public Cirurgiao(String registro, String nome, String telefone,
                     String email, UUID agendaId) {
        super(registro, nome, telefone, email, agendaId);
    }
}
