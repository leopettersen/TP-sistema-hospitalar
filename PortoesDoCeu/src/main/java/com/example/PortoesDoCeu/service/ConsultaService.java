package com.example.PortoesDoCeu.service;

import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.PortoesDoCeu.dto.request.ConsultaRequest;
import com.example.PortoesDoCeu.dto.response.ConsultaResponse;
import com.example.PortoesDoCeu.exception.EntityNotFoundException;
import com.example.PortoesDoCeu.exception.RegraNegocioException;
import com.example.PortoesDoCeu.model.*;
import com.example.PortoesDoCeu.model.enums.StatusConsulta;
import com.example.PortoesDoCeu.repository.*;

@Service
public class ConsultaService {
    private final AtendimentoRepository atendimentos;
    private final PacienteRepository pacientes;
    private final ProfissionalRepository profissionais;
    private final RotinaRepository rotinas;
    private final ConsultaEspecializadaRepository especializadas;
    private final EmergenciaRepository emergencias;

    public ConsultaService(AtendimentoRepository atendimentos, PacienteRepository pacientes,
            ProfissionalRepository profissionais, RotinaRepository rotinas,
            ConsultaEspecializadaRepository especializadas, EmergenciaRepository emergencias) {
        this.atendimentos=atendimentos; this.pacientes=pacientes; this.profissionais=profissionais;
        this.rotinas=rotinas; this.especializadas=especializadas; this.emergencias=emergencias;
    }

    @Transactional public ConsultaResponse criar(ConsultaRequest r) {
        Paciente paciente=pacientes.findById(r.cpfPaciente()).orElseThrow(()->new EntityNotFoundException("Paciente não encontrado: "+r.cpfPaciente()));
        Profissional profissional=null;
        if (r.registroProfissional()!=null && !r.registroProfissional().isBlank()) profissional=profissionais.findById(r.registroProfissional()).orElseThrow(()->new EntityNotFoundException("Profissional não encontrado: "+r.registroProfissional()));
        String modalidade=r.modalidade().trim().toUpperCase();
        Atendimento salvo;
        switch(modalidade) {
            case "ROTINA" -> {
                if (!(profissional instanceof Clinico)) throw new RegraNegocioException("Consulta de rotina exige médico clínico geral.");
                Rotina c=new Rotina(null,paciente,profissional,r.observacoes(),r.horario(),r.motivo());
                c.agendarConsulta(); salvo=rotinas.save(c);
            }
            case "ESPECIALIZADA" -> {
                if (!(profissional instanceof Especialista e)) throw new RegraNegocioException("Consulta especializada exige médico especialista.");
                if (r.especialidade()==null || e.getEspecialidade()==null || !e.getEspecialidade().equalsIgnoreCase(r.especialidade())) throw new RegraNegocioException("A especialidade informada não corresponde ao profissional.");
                ConsultaEspecializada c=new ConsultaEspecializada(null,paciente,profissional,r.observacoes(),r.horario(),r.especialidade());
                c.agendarConsulta(); salvo=especializadas.save(c);
            }
            case "EMERGENCIA" -> {
                if (r.prioridade()==null) throw new RegraNegocioException("Prioridade é obrigatória para emergência.");
                salvo=emergencias.save(new Emergencia(null,paciente,profissional,r.observacoes(),r.horario(),r.prioridade()));
            }
            default -> throw new RegraNegocioException("Modalidade inválida. Use ROTINA, ESPECIALIZADA ou EMERGENCIA.");
        }
        return resposta(salvo);
    }
    @Transactional(readOnly=true) public List<ConsultaResponse> listar() {
        return atendimentos.findAll().stream().filter(a -> a instanceof Consulta || a instanceof Emergencia).map(this::resposta).toList();
    }
    @Transactional(readOnly=true) public ConsultaResponse buscar(UUID id) {
        Atendimento a=atendimentos.findById(id).orElseThrow(()->new EntityNotFoundException("Consulta não encontrada: "+id));
        if (!(a instanceof Consulta) && !(a instanceof Emergencia)) throw new EntityNotFoundException("Consulta não encontrada: "+id);
        return resposta(a);
    }
    @Transactional public ConsultaResponse transicionar(UUID id,String acao) {
        Atendimento a=atendimentos.findById(id).orElseThrow(()->new EntityNotFoundException("Consulta não encontrada: "+id));
        String operacao=acao.trim().toUpperCase();
        if (a instanceof Consulta c) {
            switch(operacao) {
                case "CONFIRMAR" -> c.confirmarConsulta(); case "INICIAR" -> c.comecarConsulta();
                case "CONCLUIR" -> c.concluirConsulta(); case "CANCELAR" -> c.cancelarConsulta();
                case "NAO_COMPARECEU" -> c.desistiuConsulta();
                default -> throw new RegraNegocioException("Ação inválida para consulta: "+acao);
            }
        } else if (a instanceof Emergencia e) {
            switch(operacao) { case "INICIAR" -> e.iniciarAtendimento(); case "CONCLUIR" -> e.concluirAtendimento();
                default -> throw new RegraNegocioException("Ação inválida para emergência: "+acao); }
        } else throw new EntityNotFoundException("Consulta não encontrada: "+id);
        return resposta(a);
    }
    private ConsultaResponse resposta(Atendimento a) {
        String modalidade; StatusConsulta status=null; String motivo=null; String especialidade=null; com.example.PortoesDoCeu.model.enums.Prioridade prioridade=null; String estagio=null;
        if (a instanceof Rotina r) { modalidade="ROTINA"; status=r.getStatusConsulta(); motivo=r.getMotivo(); }
        else if (a instanceof ConsultaEspecializada c) { modalidade="ESPECIALIZADA"; status=c.getStatusConsulta(); especialidade=c.getEspecializacao(); }
        else if (a instanceof Emergencia e) { modalidade="EMERGENCIA"; prioridade=e.getPrioridade(); estagio=e.getEstagio().name(); }
        else modalidade="ATENDIMENTO";
        return new ConsultaResponse(a.getId(),modalidade,a.getPaciente()==null?null:a.getPaciente().getCpf(),a.getPaciente()==null?null:a.getPaciente().getNome(),a.getProfissional()==null?null:a.getProfissional().getRegistro(),a.getProfissional()==null?null:a.getProfissional().getNome(),a.getHorario(),a.getObservacoes(),status,motivo,especialidade,prioridade,estagio);
    }
}
