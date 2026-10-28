package com.example.PortoesDoCeu.model;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;


@Entity
public class Paciente {

    @Id
    private String cpf;
    private String nome;
    private LocalDate dataNascimento;
    private String telefone;
    private String endereco;
    private String email;

    private UUID historicoiD;

    public Paciente(String cpf, String nome, LocalDate dataNascimento, String telefone, String endereco, String email,
            UUID historicoiD) {
        this.cpf = cpf;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.telefone = telefone;
        this.endereco = endereco;
        this.email = email;
        this.historicoiD = historicoiD;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UUID getHistoricoiD() {
        return historicoiD;
    }

    public void setHistoricoiD(UUID historicoiD) {
        this.historicoiD = historicoiD;
    }
}