package com.example.PortoesDoCeu.dto.request;

import com.example.PortoesDoCeu.model.enums.SituacaoQuarto;
import com.example.PortoesDoCeu.model.enums.TipoQuarto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record QuartoRequest(
        @Positive int numero,
        @Positive int capacidadeMaxima,
        @NotNull TipoQuarto tipoQuarto,
        @NotNull SituacaoQuarto situacao,
        @PositiveOrZero Integer andar) {
}
