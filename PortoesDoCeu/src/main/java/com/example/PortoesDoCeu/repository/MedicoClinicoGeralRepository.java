
package com.example.PortoesDoCeu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.PortoesDoCeu.model.MedicoClinicoGeral;

public interface MedicoClinicoGeralRepository
        extends JpaRepository<MedicoClinicoGeral, String> {
}
