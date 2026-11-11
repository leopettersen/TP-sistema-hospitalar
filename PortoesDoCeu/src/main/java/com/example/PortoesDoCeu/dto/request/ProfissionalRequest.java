package com.example.PortoesDoCeu.dto.request;

import java.util.UUID;
import com.example.PortoesDoCeu.model.enums.TipoCirurgia;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ProfissionalRequest(
        @NotBlank @Size(max=30) String registro,
        @NotBlank @Size(max=120) String nome,
        @Size(max=30) String telefone,
        @Email @Size(max=150) String email,
        UUID agendaId,
        @NotBlank @Pattern(regexp="CLINICO|ESPECIALISTA|CIRURGIAO") String categoria,
        String especialidade,
        TipoCirurgia areaCirurgica) {
}
