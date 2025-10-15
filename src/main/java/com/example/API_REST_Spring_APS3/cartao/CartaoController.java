package com.example.API_REST_Spring_APS3.cartao;

import com.example.API_REST_Spring_APS3.usuario.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping("/cartoes")
public class CartaoController {

    private final CartaoService cartaoService;
    private final UsuarioService usuarioService;

    public CartaoController(CartaoService cartaoService, UsuarioService usuarioService) {
        this.cartaoService = cartaoService;
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public Collection<Cartao> listarCartoesController() {
        return cartaoService.listarCartoes();
    }

    @GetMapping("/{numeroCartao}")
    public Cartao buscarCartaoController(@PathVariable String numeroCartao) {
        return cartaoService.buscarPorNumero(numeroCartao);
    }

    @PostMapping
    public ResponseEntity<Cartao> salvarCartaoController(
            @RequestBody Cartao cartao,
            @RequestHeader("Authorization") String authHeader) {

        String token = extractToken(authHeader);
        // validarToken lança ResponseStatusException(HttpStatus.UNAUTHORIZED) se inválido
        usuarioService.validarToken(token);

        Cartao novoCartao = cartaoService.salvarCartao(cartao);
        // retorno 201 Created + Location apontando para o recurso criado
        URI location = URI.create("/cartoes/" + novoCartao.getNumeroCartao());
        return ResponseEntity.created(location).body(novoCartao);
    }

    @DeleteMapping("/{numeroCartao}")
    public ResponseEntity<Void> deletarCartaoController(
            @PathVariable String numeroCartao,
            @RequestHeader("Authorization") String authHeader) {

        String token = extractToken(authHeader);
        usuarioService.validarToken(token);

        cartaoService.deletar(numeroCartao);
        return ResponseEntity.noContent().build();
    }

    // Utilitário: aceita "Bearer <token>" ou o token puro
    private String extractToken(String authHeader) {
        if (authHeader == null || authHeader.isBlank()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authorization header ausente");
        }
        String header = authHeader.trim();
        if (header.toLowerCase().startsWith("bearer ")) {
            return header.substring(7).trim();
        }
        return header;
    }
}
