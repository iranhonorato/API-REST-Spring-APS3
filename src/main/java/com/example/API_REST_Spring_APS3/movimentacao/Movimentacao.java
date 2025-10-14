package com.example.API_REST_Spring_APS3.movimentacao;

import java.time.LocalDate;

public class Movimentacao {
    public enum TipoMovimentacao {SAQUE, DEPOSITO}
    private Float valor;
    private LocalDate data;
    private TipoMovimentacao tipo;

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