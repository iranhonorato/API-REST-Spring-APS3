package com.example.API_REST_Spring_APS3.contaCorrente;

import com.example.API_REST_Spring_APS3.cartao.Cartao;
import com.example.API_REST_Spring_APS3.cliente.Cliente;
import com.example.API_REST_Spring_APS3.movimentacao.Movimentacao;

import java.time.LocalDate;
import java.util.ArrayList;

import static com.example.API_REST_Spring_APS3.movimentacao.Movimentacao.TipoMovimentacao.DEPOSITO;
import static com.example.API_REST_Spring_APS3.movimentacao.Movimentacao.TipoMovimentacao.SAQUE;

public class ContaCorrente {
    private String agencia;
    private String conta;
    private Float saldo;
    private Float limite;
    private Cliente cliente;
    private final ArrayList<Movimentacao> movimentacoes = new ArrayList<>(); // N Movimentações para cada Conta Corrente
    private final ArrayList<Cartao> cartoes = new ArrayList<>(); // N Cartões para cada Conta Corrente



    public ContaCorrente() {}


    public ContaCorrente(String agencia, String conta, Float saldo, Float limite, Cliente cliente) {
        this.agencia = agencia;
        this.conta = conta;
        this.saldo = saldo;
        this.limite = limite;
        this.cliente = cliente;
    }




    // Métodos get
    public String getAgencia() {return agencia;}
    public String getConta() {return conta;}
    public Cliente getCliente() {return cliente;}

    // Métodos set
    public void setAgencia(String agencia) {this.agencia = agencia;}
    public void setConta(String conta) {this.conta = conta;}




    public void saque(Float valor) {
        Float novoSaldo = this.saldo - valor;

        // Verifica se o valor é válido
        if (valor == null || valor < 0) {

            // Verifica se o novo saldo está dentro do limite permitido da conta
        } else if (novoSaldo >= -this.limite) {
            // Adiciona movimentacao à lista
            Movimentacao movimentacao = new Movimentacao(valor, LocalDate.now(), SAQUE);
            this.movimentacoes.add(movimentacao);

            // Atualiza saldo
            this.saldo = novoSaldo;
        }
    }

    public void deposito(Float valor)    {

        // Verifica se o valor é válido
        if (valor == null || valor < 0) {
            return;

        } else {
            // Adiciona movimentação à lista
            Movimentacao movimentacao = new Movimentacao(valor, LocalDate.now(), DEPOSITO);
            this.movimentacoes.add(movimentacao);

            // Atualiza saldo
            this.saldo += valor;
        }
    }

    public ArrayList<Movimentacao> listaMovimentacoes() {return movimentacoes;}
    public ArrayList<Cartao> listaCartoes() {return cartoes;}
}
