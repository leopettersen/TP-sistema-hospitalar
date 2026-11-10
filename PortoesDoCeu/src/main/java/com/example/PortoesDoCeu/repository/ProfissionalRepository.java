
package com.example.PortoesDoCeu.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.PortoesDoCeu.model.Profissional;
import com.example.PortoesDoCeu.model.MedicoClinicoGeral;
import com.example.PortoesDoCeu.model.MedicoEspecialista;
import com.example.PortoesDoCeu.model.Cirurgiao;

public interface ProfissionalRepository
        extends JpaRepository<Profissional, String> {

    Optional<Profissional> findByRegistro(String registro);

    List<Profissional> findByNomeContainingIgnoreCase(
        String nome
    );

    @Query("SELECT p FROM Profissional p "
         + "WHERE TYPE(p) = MedicoClinicoGeral")
    List<MedicoClinicoGeral> buscarClinicosGerais();

    @Query("SELECT p FROM Profissional p "
         + "WHERE TYPE(p) = MedicoEspecialista")
    List<MedicoEspecialista> buscarEspecialistas();

    @Query("SELECT p FROM Profissional p "
         + "WHERE TYPE(p) = Cirurgiao")
    List<Cirurgiao> buscarCirurgioes();
}
