
package com.example.PortoesDoCeu.model;

import java.util.UUID;

import com.example.PortoesDoCeu.model.enums.TipoCirurgia;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Entity
@DiscriminatorValue("CIRURGIAO")
public class Cirurgiao extends Profissional {

    @Enumerated(EnumType.STRING)
    private TipoCirurgia areaCirurgica;

    protected Cirurgiao() {
        super();
    }

    public Cirurgiao(
            String registro,
            String nome,
            String telefone,
            String email,
            UUID agendaId,
            TipoCirurgia areaCirurgica) {

        super(registro, nome, telefone, email, agendaId);

        if (areaCirurgica == null) {
            throw new IllegalArgumentException(
                    "A área cirúrgica é obrigatória."
            );
        }

        this.areaCirurgica = areaCirurgica;
    }

    public TipoCirurgia getAreaCirurgica() {
        return areaCirurgica;
    }

    public void setAreaCirurgica(TipoCirurgia areaCirurgica) {
        if (areaCirurgica == null) {
            throw new IllegalArgumentException(
                    "A área cirúrgica é obrigatória."
            );
        }

        this.areaCirurgica = areaCirurgica;
    }
}
