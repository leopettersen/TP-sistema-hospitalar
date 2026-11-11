package com.example.PortoesDoCeu.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.PortoesDoCeu.dto.request.InternacaoRequest;
import com.example.PortoesDoCeu.dto.response.InternacaoResponse;
import com.example.PortoesDoCeu.exception.EntityNotFoundException;
import com.example.PortoesDoCeu.exception.RegraNegocioException;
import com.example.PortoesDoCeu.model.Internacao;
import com.example.PortoesDoCeu.model.Paciente;
import com.example.PortoesDoCeu.model.Quarto;
import com.example.PortoesDoCeu.repository.InternacaoRepository;
import com.example.PortoesDoCeu.repository.PacienteRepository;
import com.example.PortoesDoCeu.repository.QuartoRepository;

@Service
public class InternacaoService {

    private final InternacaoRepository internacaoRepository;
    private final PacienteRepository pacienteRepository;
    private final QuartoRepository quartoRepository;

    public InternacaoService(
            InternacaoRepository internacaoRepository,
            PacienteRepository pacienteRepository,
            QuartoRepository quartoRepository) {

        this.internacaoRepository = internacaoRepository;
        this.pacienteRepository = pacienteRepository;
        this.quartoRepository = quartoRepository;
    }

    @Transactional
    public InternacaoResponse internar(InternacaoRequest request) {
        Paciente paciente = pacienteRepository.findById(request.cpfPaciente())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Paciente não encontrado: " + request.cpfPaciente()));

        Quarto quarto = quartoRepository.buscarPorNumeroComBloqueio(request.numeroQuarto())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Quarto não encontrado: " + request.numeroQuarto()));

        if (internacaoRepository.existsByPacienteCpfAndDataAltaIsNull(paciente.getCpf())) {
            throw new RegraNegocioException("Paciente já possui internação ativa.");
        }

        long ocupados = internacaoRepository
                .countByQuartoNumeroAndDataAltaIsNull(quarto.getNumero());

        if (!quarto.temCapacidade(ocupados)
                || (quarto.getSituacao() != com.example.PortoesDoCeu.model.enums.SituacaoQuarto.LIVRE
                    && quarto.getSituacao() != com.example.PortoesDoCeu.model.enums.SituacaoQuarto.OCUPADO)) {
            throw new RegraNegocioException(
                    "Quarto " + quarto.getNumero() + " sem capacidade disponível.");
        }

        Internacao internacao = new Internacao(
                paciente,
                null,
                request.observacoes(),
                LocalDateTime.now(),
                quarto,
                request.altaPrevista());

        internacao.internar(ocupados);

        return toResponse(internacaoRepository.save(internacao));
    }

    @Transactional(readOnly = true)
    public InternacaoResponse buscar(UUID id) {
        return toResponse(buscarEntidade(id));
    }

    @Transactional(readOnly = true)
    public List<InternacaoResponse> listarAtivas() {
        return internacaoRepository.findByDataAltaIsNull()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<InternacaoResponse> listarPorPaciente(String cpf) {
        return internacaoRepository.findByPacienteCpfAndDataAltaIsNull(cpf)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public InternacaoResponse registrarAlta(UUID id, LocalDateTime dataAlta) {
        Internacao internacao = buscarEntidade(id);
        long restantes = internacaoRepository
                .countByQuartoNumeroAndDataAltaIsNull(internacao.getQuarto().getNumero()) - 1;
        internacao.registrarAlta(dataAlta, Math.max(0, restantes));
        return toResponse(internacao);
    }

    @Transactional
    public InternacaoResponse transferirQuarto(UUID id, int novoNumeroQuarto) {
        Internacao internacao = buscarEntidade(id);

        Quarto novoQuarto = quartoRepository.buscarPorNumeroComBloqueio(novoNumeroQuarto)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Quarto não encontrado: " + novoNumeroQuarto));

        long ocupados = internacaoRepository
                .countByQuartoNumeroAndDataAltaIsNull(novoQuarto.getNumero());

        if (!novoQuarto.temCapacidade(ocupados)
                || (novoQuarto.getSituacao() != com.example.PortoesDoCeu.model.enums.SituacaoQuarto.LIVRE
                    && novoQuarto.getSituacao() != com.example.PortoesDoCeu.model.enums.SituacaoQuarto.OCUPADO)) {
            throw new RegraNegocioException(
                    "Quarto " + novoQuarto.getNumero() + " sem capacidade disponível.");
        }

        long restantesNoAntigo = internacaoRepository
                .countByQuartoNumeroAndDataAltaIsNull(internacao.getQuarto().getNumero()) - 1;
        internacao.transferirQuarto(novoQuarto, ocupados, Math.max(0, restantesNoAntigo));

        return toResponse(internacao);
    }

    private Internacao buscarEntidade(UUID id) {
        return internacaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Internação não encontrada: " + id));
    }

    private InternacaoResponse toResponse(Internacao internacao) {
        Paciente paciente = internacao.getPaciente();
        Quarto quarto = internacao.getQuarto();

        return new InternacaoResponse(
                internacao.getId(),
                paciente != null ? paciente.getCpf() : null,
                paciente != null ? paciente.getNome() : null,
                internacao.getProfissional() != null
                        ? internacao.getProfissional().getRegistro()
                        : null,
                quarto.getNumero(),
                quarto.getTipoQuarto(),
                quarto.getSituacao(),
                internacao.getHorario(),
                internacao.getAltaPrevista(),
                internacao.getDataAlta(),
                internacao.getObservacoes(),
                internacao.estaAtiva());
    }
}
