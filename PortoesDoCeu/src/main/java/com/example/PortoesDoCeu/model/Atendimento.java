
package com.example.PortoesDoCeu.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.example.PortoesDoCeu.model.enums.StatusConsulta;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public abstract class Atendimento {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    private Paciente paciente;

    @ManyToOne
    private Profissional profissional;

    private String observacoes;

    private LocalDateTime horario;

    @Enumerated(EnumType.STRING)//para o bd
    private StatusConsulta statusConsulta = StatusConsulta.PENDENTE;//seta o status inicial como pendente

    // Construtor vazio exigido pelo JPA
    protected Atendimento() {
    }

    // Construtor com os dados do atendimento
    public Atendimento(
            UUID id,
            Paciente paciente,
            Profissional profissional,
            String observacoes,
            LocalDateTime horario
        ) {
        this.id = id;
        this.paciente = paciente;
        this.profissional = profissional;
        this.observacoes = observacoes;
        this.horario = horario;
    }
    //metodos do enum status
    //chama a verificao e faz a transicao
    private void transicionarStatus(StatusConsulta statusFinal){
        if(!statusConsulta.podeMudarStatus(statusFinal)){
            throw new IllegalStateException("Transicao invalida de "+ statusConsulta + "para" + statusFinal);
        }
        this.statusConsulta = statusFinal;
    }
    //mudanca de status
    public void agendarConsulta(){
        if (horario.isBefore(LocalDateTime.now())){
            throw new IllegalStateException("Horario invalido (passado)");
        }
        if (paciente == null || profissional == null || horario == null) {
            throw new IllegalStateException("Paciente, profissional e horário são obrigatórios");
        }
        transicionarStatus(StatusConsulta.AGENDADA);
    } 
    public void cancelarConsulta(){
        transicionarStatus(StatusConsulta.CANCELADA);
    }
    public void confirmarConsulta(){
        transicionarStatus(StatusConsulta.CONFIRMADA);
    }
    public void comecarConsulta(){
        transicionarStatus(StatusConsulta.EM_ANDAMENTO);
    }
    public void concluirConsulta(){
        transicionarStatus(StatusConsulta.CONCLUIDA);
    }
    public void desistiuConsulta(){
        transicionarStatus(StatusConsulta.NAO_COMPARECEU);
    }
    

    public UUID getId() {
        return id;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Profissional getProfissional() {
        return profissional;
    }

    public void setProfissional(Profissional profissional) {
        this.profissional = profissional;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public LocalDateTime getHorario() {
        return horario;
    }

    public void setHorario(LocalDateTime horario) {
        this.horario = horario;
    }
    //o "get" do status funciona a partir de metodos separados
    public StatusConsulta getStatusConsulta() {
        return statusConsulta;
    }
}
