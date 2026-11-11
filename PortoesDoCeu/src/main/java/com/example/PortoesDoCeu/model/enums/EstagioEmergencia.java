package com.example.PortoesDoCeu.model.enums;

public enum EstagioEmergencia {
    AGUARDANDO,
    INICIADO,
    CONCLUIDO;

    public boolean podeMudarPara(EstagioEmergencia destino) {
        if (this == AGUARDANDO) {
            return destino == INICIADO;
        }
        if (this == INICIADO) {
            return destino == CONCLUIDO;
        }
        return false;
    }
}
