package com.example.PortoesDoCeu.dto.response;

import com.example.PortoesDoCeu.model.enums.SituacaoQuarto;
import com.example.PortoesDoCeu.model.enums.TipoQuarto;

public record QuartoResponse(
        int numero,
        int capacidadeMaxima,
        long leitosOcupados,
        long leitosDisponiveis,
        Integer andar,
        TipoQuarto tipoQuarto,
        SituacaoQuarto situacao) {
}
