package com.example.PortoesDoCeu.controller.api;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.example.PortoesDoCeu.dto.request.QuartoRequest;
import com.example.PortoesDoCeu.dto.response.QuartoResponse;
import com.example.PortoesDoCeu.model.enums.SituacaoQuarto;
import com.example.PortoesDoCeu.model.enums.TipoQuarto;
import com.example.PortoesDoCeu.service.QuartoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/quartos")
public class QuartoController {
    private final QuartoService service;
    public QuartoController(QuartoService service) { this.service = service; }

    @PostMapping public ResponseEntity<QuartoResponse> criar(@Valid @RequestBody QuartoRequest request) {
        QuartoResponse salvo = service.criar(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{numero}").buildAndExpand(salvo.numero()).toUri();
        return ResponseEntity.created(location).body(salvo);
    }
    @GetMapping public List<QuartoResponse> listar(@RequestParam(required=false) SituacaoQuarto situacao,
            @RequestParam(required=false) TipoQuarto tipo) { return service.listar(situacao, tipo); }
    @GetMapping("/disponiveis") public List<QuartoResponse> listarDisponiveis(@RequestParam(required=false) TipoQuarto tipo) { return service.listarDisponiveis(tipo); }
    @GetMapping("/{numero}") public QuartoResponse buscar(@PathVariable int numero) { return service.buscar(numero); }
    @PutMapping("/{numero}") public QuartoResponse atualizar(@PathVariable int numero, @Valid @RequestBody QuartoRequest request) { return service.atualizar(numero, request); }
    @DeleteMapping("/{numero}") public ResponseEntity<Void> excluir(@PathVariable int numero) { service.excluir(numero); return ResponseEntity.noContent().build(); }
    @PatchMapping("/{numero}/concluir-limpeza") public QuartoResponse concluirLimpeza(@PathVariable int numero) { return service.concluirLimpeza(numero); }
    @PatchMapping("/{numero}/iniciar-manutencao") public QuartoResponse iniciarManutencao(@PathVariable int numero) { return service.iniciarManutencao(numero); }
    @PatchMapping("/{numero}/concluir-manutencao") public QuartoResponse concluirManutencao(@PathVariable int numero) { return service.concluirManutencao(numero); }
}
