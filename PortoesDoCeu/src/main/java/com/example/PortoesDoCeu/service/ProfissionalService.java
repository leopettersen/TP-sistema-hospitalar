package com.example.PortoesDoCeu.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.PortoesDoCeu.dto.request.ProfissionalRequest;
import com.example.PortoesDoCeu.dto.response.ProfissionalResponse;
import com.example.PortoesDoCeu.exception.EntityNotFoundException;
import com.example.PortoesDoCeu.exception.RegraNegocioException;
import com.example.PortoesDoCeu.model.*;
import com.example.PortoesDoCeu.repository.ProfissionalRepository;
import com.example.PortoesDoCeu.repository.EspecialistaRepository;
import com.example.PortoesDoCeu.repository.CirurgiaoRepository;
import com.example.PortoesDoCeu.model.enums.TipoCirurgia;

@Service
public class ProfissionalService {
    private final ProfissionalRepository profissionais;
    private final EspecialistaRepository especialistas;
    private final CirurgiaoRepository cirurgioes;
    public ProfissionalService(ProfissionalRepository profissionais, EspecialistaRepository especialistas,
            CirurgiaoRepository cirurgioes) {
        this.profissionais=profissionais; this.especialistas=especialistas; this.cirurgioes=cirurgioes;
    }
    @Transactional public ProfissionalResponse criar(ProfissionalRequest r) {
        if (profissionais.existsById(r.registro())) throw new RegraNegocioException("Já existe profissional com este registro.");
        Profissional p=switch(r.categoria()) {
            case "CLINICO" -> new Clinico(r.registro(),r.nome(),r.telefone(),r.email(),r.agendaId());
            case "ESPECIALISTA" -> {
                if (r.especialidade()==null || r.especialidade().isBlank()) throw new RegraNegocioException("Especialidade é obrigatória para especialista.");
                yield new Especialista(r.registro(),r.nome(),r.especialidade(),r.telefone(),r.email(),r.agendaId());
            }
            case "CIRURGIAO" -> {
                if (r.areaCirurgica()==null) throw new RegraNegocioException("Área cirúrgica é obrigatória para cirurgião.");
                yield new Cirurgiao(r.registro(),r.nome(),r.telefone(),r.email(),r.agendaId(),r.areaCirurgica());
            }
            default -> throw new RegraNegocioException("Categoria de profissional inválida.");
        };
        return resposta(profissionais.save(p));
    }
    @Transactional(readOnly=true) public List<ProfissionalResponse> listar(String nome) {
        List<Profissional> lista=(nome==null || nome.isBlank())?profissionais.findAll():profissionais.findByNomeContainingIgnoreCase(nome);
        return lista.stream().map(this::resposta).toList();
    }
    @Transactional(readOnly=true) public ProfissionalResponse buscar(String registro) { return resposta(entidade(registro)); }
    @Transactional(readOnly=true) public List<ProfissionalResponse> listarPorEspecialidade(String especialidade) {
        return especialistas.findByEspecialidadeIgnoreCase(especialidade).stream().map(this::resposta).toList();
    }
    @Transactional(readOnly=true) public List<ProfissionalResponse> listarCirurgioesPorArea(TipoCirurgia area) {
        return cirurgioes.findByAreaCirurgica(area).stream().map(this::resposta).toList();
    }
    @Transactional public ProfissionalResponse atualizar(String registro, ProfissionalRequest r) {
        if (!registro.equals(r.registro())) throw new RegraNegocioException("O registro profissional da URL não pode ser alterado.");
        Profissional p=entidade(registro);
        String categoria=p instanceof Clinico?"CLINICO":p instanceof Especialista?"ESPECIALISTA":p instanceof Cirurgiao?"CIRURGIAO":"";
        if (!categoria.equals(r.categoria())) throw new RegraNegocioException("Não é permitido alterar a categoria de um profissional cadastrado.");
        p.setNome(r.nome()); p.setTelefone(r.telefone()); p.setEmail(r.email()); p.setAgendaId(r.agendaId());
        if (p instanceof Especialista e) {
            if (r.especialidade()==null || r.especialidade().isBlank()) throw new RegraNegocioException("Especialidade é obrigatória.");
            e.setEspecialidade(r.especialidade());
        }
        if (p instanceof Cirurgiao c) {
            if (r.areaCirurgica()==null) throw new RegraNegocioException("Área cirúrgica é obrigatória.");
            c.setAreaCirurgica(r.areaCirurgica());
        }
        return resposta(p);
    }
    @Transactional public void excluir(String registro) { profissionais.delete(entidade(registro)); }
    private Profissional entidade(String registro) { return profissionais.findById(registro).orElseThrow(()->new EntityNotFoundException("Profissional não encontrado: "+registro)); }
    private ProfissionalResponse resposta(Profissional p) {
        String categoria; String especialidade=null; com.example.PortoesDoCeu.model.enums.TipoCirurgia area=null;
        if (p instanceof Clinico) categoria="CLINICO";
        else if (p instanceof Especialista e) { categoria="ESPECIALISTA"; especialidade=e.getEspecialidade(); }
        else if (p instanceof Cirurgiao c) { categoria="CIRURGIAO"; area=c.getAreaCirurgica(); }
        else categoria=p.getClass().getSimpleName().toUpperCase();
        return new ProfissionalResponse(p.getRegistro(),p.getNome(),p.getTelefone(),p.getEmail(),p.getAgendaId(),categoria,especialidade,area);
    }
}
