package com.example.PortoesDoCeu.dto.request;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

public record AltaInternacaoRequest(
        @NotNull @PastOrPresent LocalDateTime dataAlta) {
}
