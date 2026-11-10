package com.example.PortoesDoCeu.model;

import java.util.UUID;

public class Cirurgiao extends Profissional {

    public Cirurgiao(String registro, String nome, String telefone, String email, UUID agendaId) {
        super(registro, nome, telefone, email, agendaId);
    }
    
}
