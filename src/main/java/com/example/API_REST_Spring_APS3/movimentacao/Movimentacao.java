package com.example.API_REST_Spring_APS3.movimentacao;

import com.example.API_REST_Spring_APS3.contaCorrente.ContaCorrente;
import jakarta.persistence.*;

import java.time.LocalDate;


@Entity
@Table(name = "movimentacoes")
public class Movimentacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public enum TipoMovimentacao {SAQUE, DEPOSITO}
    private Float valor;
    private TipoMovimentacao tipo;
    private LocalDate data;


    @ManyToOne(optional = false)
    @JoinColumn(name = "conta_corrente_numero")
    private ContaCorrente contaCorrente;


    public Movimentacao(Float valor, LocalDate data, TipoMovimentacao tipo) {
        this.valor = valor;
        this.data = data;
        this.tipo = tipo;
    }

    // Métodos get
    public Float getValor() {return valor;}
    public LocalDate getData() {return data;}
    public TipoMovimentacao getTipo() {return tipo;}
}