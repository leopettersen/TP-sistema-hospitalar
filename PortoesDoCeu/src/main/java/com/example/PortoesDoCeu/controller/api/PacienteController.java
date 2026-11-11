package com.example.PortoesDoCeu.controller.api;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.example.PortoesDoCeu.dto.request.PacienteRequest;
import com.example.PortoesDoCeu.dto.response.PacienteResponse;
import com.example.PortoesDoCeu.service.PacienteService;
import jakarta.validation.Valid;

@RestController @RequestMapping("/api/pacientes")
public class PacienteController {
    private final PacienteService service;
    public PacienteController(PacienteService service) { this.service=service; }
    @PostMapping public ResponseEntity<PacienteResponse> criar(@Valid @RequestBody PacienteRequest r) { PacienteResponse p=service.criar(r); URI uri=ServletUriComponentsBuilder.fromCurrentRequest().path("/{cpf}").buildAndExpand(p.cpf()).toUri(); return ResponseEntity.created(uri).body(p); }
    @GetMapping public List<PacienteResponse> listar() { return service.listar(); }
    @GetMapping("/{cpf}") public PacienteResponse buscar(@PathVariable String cpf) { return service.buscar(cpf); }
    @PutMapping("/{cpf}") public PacienteResponse atualizar(@PathVariable String cpf,@Valid @RequestBody PacienteRequest r) { return service.atualizar(cpf,r); }
    @DeleteMapping("/{cpf}") public ResponseEntity<Void> excluir(@PathVariable String cpf) { service.excluir(cpf); return ResponseEntity.noContent().build(); }
}
