package com.example.PortoesDoCeu.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;
import com.example.PortoesDoCeu.model.enums.Prioridade;
import com.example.PortoesDoCeu.model.enums.StatusConsulta;

public record ConsultaResponse(UUID id, String modalidade, String cpfPaciente,
        String nomePaciente, String registroProfissional, String nomeProfissional,
        LocalDateTime horario, String observacoes, StatusConsulta status,
        String motivo, String especialidade, Prioridade prioridade, String estagioEmergencia) { }
