package com.example.PortoesDoCeu.controller.api;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.example.PortoesDoCeu.dto.request.ProfissionalRequest;
import com.example.PortoesDoCeu.dto.response.ProfissionalResponse;
import com.example.PortoesDoCeu.service.ProfissionalService;
import com.example.PortoesDoCeu.model.enums.TipoCirurgia;
import jakarta.validation.Valid;

@RestController @RequestMapping("/api/profissionais")
public class ProfissionalController {
    private final ProfissionalService service;
    public ProfissionalController(ProfissionalService service) { this.service=service; }
    @PostMapping public ResponseEntity<ProfissionalResponse> criar(@Valid @RequestBody ProfissionalRequest r) { ProfissionalResponse p=service.criar(r); URI uri=ServletUriComponentsBuilder.fromCurrentRequest().path("/{registro}").buildAndExpand(p.registro()).toUri(); return ResponseEntity.created(uri).body(p); }
    @GetMapping public List<ProfissionalResponse> listar(@RequestParam(required=false) String nome) { return service.listar(nome); }
    @GetMapping("/especialidade/{especialidade}") public List<ProfissionalResponse> listarPorEspecialidade(@PathVariable String especialidade) { return service.listarPorEspecialidade(especialidade); }
    @GetMapping("/cirurgioes/area/{area}") public List<ProfissionalResponse> listarCirurgioesPorArea(@PathVariable TipoCirurgia area) { return service.listarCirurgioesPorArea(area); }
    @GetMapping("/{registro}") public ProfissionalResponse buscar(@PathVariable String registro) { return service.buscar(registro); }
    @PutMapping("/{registro}") public ProfissionalResponse atualizar(@PathVariable String registro,@Valid @RequestBody ProfissionalRequest r) { return service.atualizar(registro,r); }
    @DeleteMapping("/{registro}") public ResponseEntity<Void> excluir(@PathVariable String registro) { service.excluir(registro); return ResponseEntity.noContent().build(); }
}
