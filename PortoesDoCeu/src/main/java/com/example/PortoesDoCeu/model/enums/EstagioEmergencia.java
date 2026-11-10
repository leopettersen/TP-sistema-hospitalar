package com.example.PortoesDoCeu.model.enums;

public enum EstagioEmergencia {
    AGUARDANDO,
    INICIADO,
    CONCLUIDO;

    // Mesmo padrão do StatusConsulta: o enum diz para onde pode ir
    public boolean podeMudarPara(EstagioEmergencia destino) {
        if (this == AGUARDANDO) {
            return destino == INICIADO;
        } else if (this == INICIADO) {
            return destino == CONCLUIDO;
        }
        return false; // CONCLUIDO é final
    }
}