package com.example.PortoesDoCeu.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record InternacaoRequest(
        @NotBlank String cpfPaciente,
        @Positive int numeroQuarto,
        String observacoes,
        @NotNull @FutureOrPresent LocalDate altaPrevista) {
}
