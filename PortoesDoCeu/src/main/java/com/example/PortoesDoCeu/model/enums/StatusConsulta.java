package com.example.PortoesDoCeu.model.enums;
//status da consulta solicitado no readme com todos os status que eu consegui pensar
public enum StatusConsulta {
    PENDENTE,
    AGENDADA,
    CONFIRMADA,
    EM_ANDAMENTO,
    CONCLUIDA,
    CANCELADA,
    NAO_COMPARECEU;

    //faz a verificacao de status anterior para futuro
    public boolean podeMudarStatus(StatusConsulta statusFinal){
        if (this == StatusConsulta.PENDENTE) {
            return statusFinal == StatusConsulta.AGENDADA || statusFinal == StatusConsulta.CANCELADA ;
        }
        else if (this == StatusConsulta.AGENDADA) {
            return statusFinal == StatusConsulta.CONFIRMADA || statusFinal == StatusConsulta.CANCELADA ;
        }
        if (this == StatusConsulta.CONFIRMADA) {
            return statusFinal == StatusConsulta.EM_ANDAMENTO || statusFinal == StatusConsulta.CANCELADA || statusFinal == StatusConsulta.NAO_COMPARECEU ;
        }
        if (this == StatusConsulta.EM_ANDAMENTO) {
            return statusFinal == StatusConsulta.CONCLUIDA ;
        }
        return false;
    }
}
