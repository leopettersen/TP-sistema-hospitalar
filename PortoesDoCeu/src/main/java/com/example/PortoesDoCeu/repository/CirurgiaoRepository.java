
package com.example.PortoesDoCeu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.PortoesDoCeu.model.Cirurgiao;
import com.example.PortoesDoCeu.model.enums.TipoCirurgia;

public interface CirurgiaoRepository
        extends JpaRepository<Cirurgiao, String> {

    List<Cirurgiao> findByAreaCirurgica(
        TipoCirurgia areaCirurgica
    );
}
