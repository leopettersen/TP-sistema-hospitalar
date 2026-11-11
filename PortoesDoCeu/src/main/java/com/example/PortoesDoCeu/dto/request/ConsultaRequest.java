package com.example.PortoesDoCeu.dto.request;

import java.time.LocalDateTime;
import com.example.PortoesDoCeu.model.enums.Prioridade;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ConsultaRequest(
        @NotBlank String cpfPaciente,
        String registroProfissional,
        @NotNull LocalDateTime horario,
        String observacoes,
        @NotBlank String modalidade,
        String motivo,
        String especialidade,
        Prioridade prioridade) {
}
