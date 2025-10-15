package com.example.API_REST_Spring_APS3.cliente;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "clientes")
public class Cliente {

    // CPF como chave primária (única e natural)
    @Id
    @Column(name = "cpf", nullable = false, length = 20)
    private String cpf;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    @Column(name = "salario")
    private Float salario;

    public Cliente() {}

    public Cliente(String cpf, String nome, LocalDate dataNascimento, Float salario) {
        this.cpf = cpf;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.salario = salario;
    }

    // getters
    public String getCpf() { return cpf; }
    public String getNome() { return nome; }
    public LocalDate getDataNascimento() { return dataNascimento; }
    public Float getSalario() { return salario; }

    // setters
    public void setCpf(String cpf) { this.cpf = cpf; }
    public void setNome(String nome) { this.nome = nome; }
    public void setDataNascimento(LocalDate dataNascimento) { this.dataNascimento = dataNascimento; }
    public void setSalario(Float salario) { this.salario = salario; }

    // equals/hashCode baseados no CPF (identificador)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cliente)) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(cpf, cliente.cpf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cpf);
    }
}
