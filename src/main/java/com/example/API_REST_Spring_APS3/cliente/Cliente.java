package com.example.API_REST_Spring_APS3.cliente;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;



@Entity
@Table(name = "clientes")
public class Cliente {

    @Id
    private String nome;
    private String cpf;
    private LocalDate dataNascimento;
    private Float salario;

    public Cliente() {}

    public Cliente(String nome, String cpf, LocalDate dataNascimento, Float salario) {
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.salario = salario;
    }

    // Métodos get
    public String getNome() {return this.nome;}
    public String getCpf() {return this.cpf;}
    public LocalDate getDataNascimento() {return this.dataNascimento;}
    public Float getSalario() {return this.salario;}


    // Métodos set
    public void setNome(String nome) {this.nome = nome;}
    public void setCpf(String cpf) {this.cpf = cpf;}
    public void setDataNascimento(LocalDate dataNascimento) {this.dataNascimento = dataNascimento;}
    public void setSalario(Float salario) {this.salario = salario;}
}
