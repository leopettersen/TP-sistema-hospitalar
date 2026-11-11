package com.example.PortoesDoCeu.controller.api;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.example.PortoesDoCeu.dto.request.ConsultaRequest;
import com.example.PortoesDoCeu.dto.request.TransicaoConsultaRequest;
import com.example.PortoesDoCeu.dto.response.ConsultaResponse;
import com.example.PortoesDoCeu.service.ConsultaService;
import jakarta.validation.Valid;

@RestController @RequestMapping("/api/consultas")
public class ConsultaController {
    private final ConsultaService service;
    public ConsultaController(ConsultaService service) { this.service=service; }
    @PostMapping public ResponseEntity<ConsultaResponse> criar(@Valid @RequestBody ConsultaRequest r) { ConsultaResponse c=service.criar(r); URI uri=ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(c.id()).toUri(); return ResponseEntity.created(uri).body(c); }
    @GetMapping public List<ConsultaResponse> listar() { return service.listar(); }
    @GetMapping("/{id}") public ConsultaResponse buscar(@PathVariable UUID id) { return service.buscar(id); }
    @PatchMapping("/{id}/status") public ConsultaResponse transicionar(@PathVariable UUID id,@Valid @RequestBody TransicaoConsultaRequest r) { return service.transicionar(id,r.acao()); }
}
