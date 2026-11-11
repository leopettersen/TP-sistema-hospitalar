package com.example.PortoesDoCeu.dto.response;

import java.time.LocalDate;
import java.util.UUID;

public record PacienteResponse(String cpf, String nome, LocalDate dataNascimento,
        String telefone, String endereco, String email, UUID historicoId) {
}
