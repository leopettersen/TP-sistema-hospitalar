package com.example.PortoesDoCeu.dto.request;

import java.time.LocalDate;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

public record PacienteRequest(
        @NotBlank @Size(min=11, max=14) String cpf,
        @NotBlank @Size(max=120) String nome,
        @NotNull @Past LocalDate dataNascimento,
        @Size(max=30) String telefone,
        @Size(max=255) String endereco,
        @Email @Size(max=150) String email) {
}
