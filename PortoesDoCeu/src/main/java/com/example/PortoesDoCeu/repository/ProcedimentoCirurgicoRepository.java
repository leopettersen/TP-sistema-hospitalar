
package com.example.PortoesDoCeu.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.PortoesDoCeu.model.ProcedimentoCirurgico;
import com.example.PortoesDoCeu.model.enums.StatusProcedimento;

@Repository
public interface ProcedimentoCirurgicoRepository
        extends JpaRepository<ProcedimentoCirurgico, UUID> {

    List<ProcedimentoCirurgico> findByPaciente_Cpf(String cpf);

    List<ProcedimentoCirurgico> findByProfissional_Registro(String registro);

    List<ProcedimentoCirurgico> findByStatus(StatusProcedimento status);
}
