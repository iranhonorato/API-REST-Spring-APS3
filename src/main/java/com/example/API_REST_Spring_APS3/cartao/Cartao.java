package com.example.API_REST_Spring_APS3.cartao;

import java.time.LocalDate;

public class Cartao {
    public enum CartaoStatus {ATIVO, CANCELADO}
    private String numeroCartao;
    private String tipo;
    private LocalDate validade;
    private CartaoStatus status;


    public Cartao() {}


    public Cartao(String numeroCartao, String tipo, LocalDate validade, CartaoStatus status) {
        this.numeroCartao = numeroCartao;
        this.tipo = tipo;
        this.validade = validade;
        this.status = status;
    }


    // Métodos get
    public String getNumeroCartao() {return this.numeroCartao;}
    public String getTipo() {return this.tipo;}
    public LocalDate getValidade() {return this.validade;}
    public CartaoStatus getStatus() {return this.status;}

    // Métodos set
    public void setNumeroCartao(String numeroCartao) {this.numeroCartao = numeroCartao;}
    public void setTipo(String tipo) {this.tipo = tipo;}
    public void setValidade(LocalDate validade) {this.validade = validade;}
    public void setStatus(CartaoStatus status) {this.status = status;}


    public Boolean isExpired() {
        return LocalDate.now().isAfter(this.validade);
    };

    public Boolean cancelaCartao() {
        if (this.status == CartaoStatus.ATIVO) {
            this.status = CartaoStatus.CANCELADO;
            return true;
        }
        return false;
    };
}
