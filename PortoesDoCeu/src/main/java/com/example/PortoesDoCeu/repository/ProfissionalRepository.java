
package com.example.PortoesDoCeu.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.PortoesDoCeu.model.Cirurgiao;
import com.example.PortoesDoCeu.model.Especialista;
import com.example.PortoesDoCeu.model.Profissional;

public interface ProfissionalRepository
        extends JpaRepository<Profissional, String> {

    Optional<Profissional> findByRegistro(String registro);

    List<Profissional> findByNomeContainingIgnoreCase(
        String nome
    );

    @Query("SELECT p FROM Especialista p")
    List<Especialista> buscarEspecialistas();

    @Query("SELECT p FROM Profissional p "
         + "WHERE TYPE(p) = Cirurgiao")
    List<Cirurgiao> buscarCirurgioes();
}
