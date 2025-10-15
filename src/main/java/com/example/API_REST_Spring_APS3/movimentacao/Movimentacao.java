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

    public enum TipoMovimentacao { SAQUE, DEPOSITO }

    @Column(nullable = false)
    private Float valor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoMovimentacao tipo;

    @Column(nullable = false)
    private LocalDate data;

    @ManyToOne(optional = false)
    @JoinColumn(name = "conta_corrente_numero", nullable = false)
    private ContaCorrente contaCorrente;

    // 🔹 Construtor padrão exigido pelo JPA
    public Movimentacao() {}

    // 🔹 Construtor prático usado no código
    public Movimentacao(Float valor, LocalDate data, TipoMovimentacao tipo) {
        this.valor = valor;
        this.data = data;
        this.tipo = tipo;
    }

    // Getters
    public Long getId() { return id; }
    public Float getValor() { return valor; }
    public TipoMovimentacao getTipo() { return tipo; }
    public LocalDate getData() { return data; }
    public ContaCorrente getContaCorrente() { return contaCorrente; }

    // Setter necessário para manter o vínculo bidirecional
    public void setContaCorrente(ContaCorrente contaCorrente) {
        this.contaCorrente = contaCorrente;
    }
}
