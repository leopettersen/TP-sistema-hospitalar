package com.example.PortoesDoCeu.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.PortoesDoCeu.dto.request.PacienteRequest;
import com.example.PortoesDoCeu.dto.response.PacienteResponse;
import com.example.PortoesDoCeu.exception.EntityNotFoundException;
import com.example.PortoesDoCeu.exception.RegraNegocioException;
import com.example.PortoesDoCeu.model.Paciente;
import com.example.PortoesDoCeu.repository.InternacaoRepository;
import com.example.PortoesDoCeu.repository.PacienteRepository;

@Service
public class PacienteService {
    private final PacienteRepository pacientes;
    private final InternacaoRepository internacoes;
    public PacienteService(PacienteRepository pacientes, InternacaoRepository internacoes) { this.pacientes = pacientes; this.internacoes = internacoes; }

    @Transactional public PacienteResponse criar(PacienteRequest r) {
        if (pacientes.existsById(r.cpf())) throw new RegraNegocioException("Já existe paciente cadastrado com este CPF.");
        return resposta(pacientes.save(new Paciente(r.cpf(), r.nome(), r.dataNascimento(), r.telefone(), r.endereco(), r.email(), null)));
    }
    @Transactional(readOnly=true) public List<PacienteResponse> listar() { return pacientes.findAll().stream().map(this::resposta).toList(); }
    @Transactional(readOnly=true) public PacienteResponse buscar(String cpf) { return resposta(entidade(cpf)); }
    @Transactional public PacienteResponse atualizar(String cpf, PacienteRequest r) {
        if (!cpf.equals(r.cpf())) throw new RegraNegocioException("O CPF da URL não pode ser alterado.");
        Paciente p=entidade(cpf); p.setNome(r.nome()); p.setDataNascimento(r.dataNascimento()); p.setTelefone(r.telefone()); p.setEndereco(r.endereco()); p.setEmail(r.email()); return resposta(p);
    }
    @Transactional public void excluir(String cpf) {
        Paciente p=entidade(cpf);
        if (internacoes.existsByPacienteCpfAndDataAltaIsNull(cpf)) throw new RegraNegocioException("Não é possível excluir paciente com internação ativa.");
        pacientes.delete(p);
    }
    private Paciente entidade(String cpf) { return pacientes.findById(cpf).orElseThrow(() -> new EntityNotFoundException("Paciente não encontrado: "+cpf)); }
    private PacienteResponse resposta(Paciente p) { return new PacienteResponse(p.getCpf(),p.getNome(),p.getDataNascimento(),p.getTelefone(),p.getEndereco(),p.getEmail(),p.getHistoricoiD()); }
}
