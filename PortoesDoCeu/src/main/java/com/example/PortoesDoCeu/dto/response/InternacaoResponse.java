package com.example.PortoesDoCeu.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import com.example.PortoesDoCeu.model.enums.SituacaoQuarto;
import com.example.PortoesDoCeu.model.enums.TipoQuarto;

public record InternacaoResponse(
        UUID id,
        String cpfPaciente,
        String nomePaciente,
        String registroProfissional,
        int numeroQuarto,
        TipoQuarto tipoQuarto,
        SituacaoQuarto situacaoQuarto,
        LocalDateTime horario,
        LocalDate altaPrevista,
        LocalDateTime dataAlta,
        String observacoes,
        boolean ativa) {
}
