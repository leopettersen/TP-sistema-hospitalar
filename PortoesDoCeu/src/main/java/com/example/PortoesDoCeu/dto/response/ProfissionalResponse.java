package com.example.PortoesDoCeu.dto.response;

import java.util.UUID;
import com.example.PortoesDoCeu.model.enums.TipoCirurgia;

public record ProfissionalResponse(String registro, String nome, String telefone,
        String email, UUID agendaId, String categoria, String especialidade,
        TipoCirurgia areaCirurgica) {
}
