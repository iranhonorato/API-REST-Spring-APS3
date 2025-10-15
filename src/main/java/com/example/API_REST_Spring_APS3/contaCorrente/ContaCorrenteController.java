package com.example.API_REST_Spring_APS3.contaCorrente;

import com.example.API_REST_Spring_APS3.movimentacao.Movimentacao;
import com.example.API_REST_Spring_APS3.usuario.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/conta")
public class ContaCorrenteController {

    private final ContaCorrenteService contaCorrenteService;
    private final UsuarioService usuarioService;

    public ContaCorrenteController(ContaCorrenteService contaCorrenteService, UsuarioService usuarioService) {
        this.contaCorrenteService = contaCorrenteService;
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<Collection<ContaCorrente>> listarContasController() {
        return ResponseEntity.ok(contaCorrenteService.listarContas());
    }

    @GetMapping("/{conta}")
    public ResponseEntity<?> listarMovimentacoesController(@PathVariable String conta) {
        try {
            List<Movimentacao> movimentos = contaCorrenteService.listarMovimentacoes(conta);
            return ResponseEntity.ok(movimentos);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        }
    }

    @PostMapping
    public ResponseEntity<?> cadastrarContaController(
            @RequestBody ContaCorrente contaCorrente,
            @RequestHeader("Authorization") String token) {

        // Mantive a lógica do token exatamente como solicitado
        usuarioService.validarToken(token);

        try {
            ContaCorrente novaConta = contaCorrenteService.cadastrarConta(contaCorrente);
            return ResponseEntity.ok().body(novaConta);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{conta}/saque")
    public ResponseEntity<?> saqueController(
            @PathVariable String conta,
            @RequestBody Float valor,
            @RequestHeader("Authorization") String token) {

        // Mantive a lógica do token exatamente como solicitado
        usuarioService.validarToken(token);

        try {
            contaCorrenteService.sacar(conta, valor);
            return ResponseEntity.ok().body("Saque realizado com sucesso");
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{conta}/deposito")
    public ResponseEntity<?> depositarController(
            @PathVariable String conta,
            @RequestBody Float valor,
            @RequestHeader("Authorization") String token) {

        // Mantive a lógica do token exatamente como solicitado
        usuarioService.validarToken(token);

        try {
            contaCorrenteService.depositar(conta, valor);
            return ResponseEntity.ok().body("Depósito realizado com sucesso");
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
