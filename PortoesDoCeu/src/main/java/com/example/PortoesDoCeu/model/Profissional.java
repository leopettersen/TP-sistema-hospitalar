
package com.example.PortoesDoCeu.model;

import java.util.UUID;

import jakarta.persistence.*;

@Entity
@Table(name = "profissional")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
    name = "tipo_profissional",
    discriminatorType = DiscriminatorType.STRING
)
public abstract class Profissional {

    @Id
    private String registro;

    private String nome;
    private String telefone;
    private String email;
    private UUID agendaId;

    protected Profissional() {
    }

    public Profissional(
            String registro,
            String nome,
            String telefone,
            String email,
            UUID agendaId) {

        if (registro == null || registro.isBlank()) {
            throw new IllegalArgumentException(
                "O registro profissional é obrigatório."
            );
        }

        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException(
                "O nome é obrigatório."
            );
        }

        this.registro = registro;
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.agendaId = agendaId;
    }

    public String getRegistro() {
        return registro;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UUID getAgendaId() {
        return agendaId;
    }

    public void setAgendaId(UUID agendaId) {
        this.agendaId = agendaId;
    }
}
