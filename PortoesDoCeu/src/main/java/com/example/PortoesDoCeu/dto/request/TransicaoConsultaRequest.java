package com.example.PortoesDoCeu.dto.request;

import jakarta.validation.constraints.NotBlank;

public record TransicaoConsultaRequest(@NotBlank String acao) { }
