package com.example.API_REST_Spring_APS3.contaCorrente;

import com.example.API_REST_Spring_APS3.movimentacao.Movimentacao;
import com.example.API_REST_Spring_APS3.usuario.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;

@RestController
@RequestMapping("/conta")
public class ContaCorrenteController {

    @Autowired
    private ContaCorrenteService contaCorrenteService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public Collection<ContaCorrente> listarContasController(){ return  contaCorrenteService.listarContas(); }


    @GetMapping("/{conta}")
    public ArrayList<Movimentacao> listarMovimentacoesController(@PathVariable String conta){
        return  contaCorrenteService.listarMovimentacoes(conta);
    }


    @PostMapping
    public ResponseEntity<?> cadastrarContaController(
            @RequestBody ContaCorrente contaCorrente,
            @RequestHeader("Authorization") String token){
        try {
            usuarioService.validarToken(token);
            ContaCorrente novaConta = contaCorrenteService.cadastrarConta(contaCorrente);
            return ResponseEntity.ok().body(novaConta);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PostMapping("/{conta}/saque")
    public ResponseEntity<?> saqueController(
            @PathVariable String conta,
            @RequestBody Float valor,
            @RequestHeader("Authorization") String token){
        try {
            usuarioService.validarToken(token);
            contaCorrenteService.sacar(conta, valor);
            return ResponseEntity.ok().body("Saque realizado com sucesso");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PostMapping("/{conta}/deposito")
    public ResponseEntity<?> depositarController(
            @PathVariable String conta,
            @RequestBody Float valor,
            @RequestHeader("Authorization") String token){
        try {
            usuarioService.validarToken(token);
            contaCorrenteService.depositar(conta, valor);
            return ResponseEntity.ok().body("Depósito realizado com sucesso");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}