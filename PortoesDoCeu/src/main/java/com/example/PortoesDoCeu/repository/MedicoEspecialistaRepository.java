
package com.example.PortoesDoCeu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.PortoesDoCeu.model.MedicoEspecialista;
import com.example.PortoesDoCeu.model.enums.EspecialidadeMedica;

public interface MedicoEspecialistaRepository
        extends JpaRepository<MedicoEspecialista, String> {

    List<MedicoEspecialista> findByEspecialidade(
        EspecialidadeMedica especialidade
    );
}
