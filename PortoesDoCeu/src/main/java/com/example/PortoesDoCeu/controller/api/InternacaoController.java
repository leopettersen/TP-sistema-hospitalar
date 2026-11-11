package com.example.PortoesDoCeu.controller.api;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.PortoesDoCeu.dto.request.AltaInternacaoRequest;
import com.example.PortoesDoCeu.dto.request.InternacaoRequest;
import com.example.PortoesDoCeu.dto.request.TransferenciaQuartoRequest;
import com.example.PortoesDoCeu.dto.response.InternacaoResponse;
import com.example.PortoesDoCeu.service.InternacaoService;

import jakarta.validation.Valid;

@Validated
@RestController
@RequestMapping("/api/internacoes")
public class InternacaoController {

    private final InternacaoService internacaoService;

    public InternacaoController(InternacaoService internacaoService) {
        this.internacaoService = internacaoService;
    }

    @PostMapping
    public ResponseEntity<InternacaoResponse> internar(
            @Valid @RequestBody InternacaoRequest request) {

        InternacaoResponse response = internacaoService.internar(request);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();

        return ResponseEntity.created(location).body(response);
    }

    @GetMapping
    public List<InternacaoResponse> listarAtivas() {
        return internacaoService.listarAtivas();
    }

    @GetMapping("/{id}")
    public InternacaoResponse buscar(@PathVariable UUID id) {
        return internacaoService.buscar(id);
    }

    @GetMapping("/paciente/{cpf}")
    public List<InternacaoResponse> listarPorPaciente(@PathVariable String cpf) {
        return internacaoService.listarPorPaciente(cpf);
    }

    @PatchMapping("/{id}/alta")
    public InternacaoResponse registrarAlta(
            @PathVariable UUID id,
            @Valid @RequestBody AltaInternacaoRequest request) {

        return internacaoService.registrarAlta(id, request.dataAlta());
    }

    @PatchMapping("/{id}/quarto")
    public InternacaoResponse transferirQuarto(
            @PathVariable UUID id,
            @Valid @RequestBody TransferenciaQuartoRequest request) {

        return internacaoService.transferirQuarto(id, request.novoNumeroQuarto());
    }
}
