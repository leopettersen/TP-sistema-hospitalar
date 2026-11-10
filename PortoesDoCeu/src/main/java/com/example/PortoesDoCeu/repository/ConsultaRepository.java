package com.example.PortoesDoCeu.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.PortoesDoCeu.model.Consulta;
import com.example.PortoesDoCeu.model.enums.StatusConsulta;

public interface ConsultaRepository extends JpaRepository<Consulta, UUID> {

    List<Consulta> findByStatusConsulta(StatusConsulta status);

    List<Consulta> findByPacienteCpf(String cpf);

    List<Consulta> findByProfissionalRegistro(String registro);

    List<Consulta> findByHorarioBetween(LocalDateTime inicio, LocalDateTime fim);

    // Conflito de agenda: passe CANCELADA em statusIgnorado
    boolean existsByProfissionalRegistroAndHorarioAndStatusConsultaNot(
            String registro, LocalDateTime horario, StatusConsulta statusIgnorado);
}