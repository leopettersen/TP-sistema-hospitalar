package com.example.PortoesDoCeu.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.PortoesDoCeu.dto.request.QuartoRequest;
import com.example.PortoesDoCeu.dto.response.QuartoResponse;
import com.example.PortoesDoCeu.exception.EntityNotFoundException;
import com.example.PortoesDoCeu.exception.RegraNegocioException;
import com.example.PortoesDoCeu.model.Quarto;
import com.example.PortoesDoCeu.model.enums.SituacaoQuarto;
import com.example.PortoesDoCeu.model.enums.TipoQuarto;
import com.example.PortoesDoCeu.repository.InternacaoRepository;
import com.example.PortoesDoCeu.repository.QuartoRepository;

@Service
public class QuartoService {
    private final QuartoRepository quartos;
    private final InternacaoRepository internacoes;

    public QuartoService(QuartoRepository quartos, InternacaoRepository internacoes) {
        this.quartos = quartos;
        this.internacoes = internacoes;
    }

    @Transactional
    public QuartoResponse criar(QuartoRequest request) {
        if (quartos.existsById(request.numero())) {
            throw new RegraNegocioException("Já existe um quarto com o número " + request.numero() + ".");
        }
        Quarto quarto = new Quarto(request.numero(), request.capacidadeMaxima(),
                request.situacao(), request.tipoQuarto(), request.andar());
        return resposta(quartos.save(quarto));
    }

    @Transactional(readOnly = true)
    public List<QuartoResponse> listar(SituacaoQuarto situacao, TipoQuarto tipo) {
        List<Quarto> encontrados;
        if (situacao != null && tipo != null) encontrados = quartos.findBySituacaoAndTipoQuarto(situacao, tipo);
        else if (situacao != null) encontrados = quartos.findBySituacao(situacao);
        else if (tipo != null) encontrados = quartos.findByTipoQuarto(tipo);
        else encontrados = quartos.findAll();
        return encontrados.stream().map(this::resposta).toList();
    }

    @Transactional(readOnly = true)
    public List<QuartoResponse> listarDisponiveis(TipoQuarto tipo) {
        List<Quarto> candidatos = tipo == null ? quartos.findAll() : quartos.findByTipoQuarto(tipo);
        return candidatos.stream().filter(q ->
                (q.getSituacao() == SituacaoQuarto.LIVRE || q.getSituacao() == SituacaoQuarto.OCUPADO)
                && internacoes.countByQuartoNumeroAndDataAltaIsNull(q.getNumero()) < q.getCapacidadeMaxima())
                .map(this::resposta).toList();
    }

    @Transactional(readOnly = true)
    public QuartoResponse buscar(int numero) { return resposta(entidade(numero)); }

    @Transactional
    public QuartoResponse atualizar(int numero, QuartoRequest request) {
        if (numero != request.numero()) throw new RegraNegocioException("O número informado na URL deve ser igual ao número do quarto.");
        Quarto quarto = quartos.buscarPorNumeroComBloqueio(numero)
                .orElseThrow(() -> new EntityNotFoundException("Quarto não encontrado: " + numero));
        long ocupados = internacoes.countByQuartoNumeroAndDataAltaIsNull(numero);
        if (request.capacidadeMaxima() < ocupados) throw new RegraNegocioException("A capacidade não pode ser menor que a quantidade de internações ativas.");
        if (ocupados > 0 && request.situacao() != SituacaoQuarto.OCUPADO) throw new RegraNegocioException("Um quarto com pacientes internados deve permanecer OCUPADO.");
        quarto.setCapacidadeMaxima(request.capacidadeMaxima());
        quarto.setTipoQuarto(request.tipoQuarto());
        quarto.setAndar(request.andar());
        quarto.setSituacao(request.situacao());
        return resposta(quarto);
    }

    @Transactional
    public void excluir(int numero) {
        Quarto quarto = entidade(numero);
        if (internacoes.countByQuartoNumeroAndDataAltaIsNull(numero) > 0) throw new RegraNegocioException("Não é possível excluir um quarto com internações ativas.");
        if (internacoes.existsByQuartoNumero(numero)) throw new RegraNegocioException("Não é possível excluir quarto que possui histórico de internações.");
        quartos.delete(quarto);
    }

    @Transactional
    public QuartoResponse concluirLimpeza(int numero) { Quarto q = entidade(numero); q.terminarLimpeza(); return resposta(q); }
    @Transactional
    public QuartoResponse iniciarManutencao(int numero) { Quarto q = entidade(numero); q.iniciarManutencao(); return resposta(q); }
    @Transactional
    public QuartoResponse concluirManutencao(int numero) { Quarto q = entidade(numero); q.terminarManutencao(); return resposta(q); }

    private Quarto entidade(int numero) { return quartos.findById(numero).orElseThrow(() -> new EntityNotFoundException("Quarto não encontrado: " + numero)); }
    private QuartoResponse resposta(Quarto q) {
        long ocupados = internacoes.countByQuartoNumeroAndDataAltaIsNull(q.getNumero());
        return new QuartoResponse(q.getNumero(), q.getCapacidadeMaxima(), ocupados,
                Math.max(0, q.getCapacidadeMaxima() - ocupados), q.getAndar(), q.getTipoQuarto(), q.getSituacao());
    }
}
