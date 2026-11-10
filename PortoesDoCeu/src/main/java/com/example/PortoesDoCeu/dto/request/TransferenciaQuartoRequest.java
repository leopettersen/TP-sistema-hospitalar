package com.example.PortoesDoCeu.dto.request;

import jakarta.validation.constraints.Positive;

public record TransferenciaQuartoRequest(
        @Positive int novoNumeroQuarto) {
}
