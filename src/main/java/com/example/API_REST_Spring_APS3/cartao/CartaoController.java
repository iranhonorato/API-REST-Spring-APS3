package com.example.API_REST_Spring_APS3.cartao;


//Cliente → envia requisição HTTP → Controller
//Controller → chama métodos → Service
//Service → usa/gera dados → Model (Autor)
//Service → retorna resultado → Controller
//Controller → devolve JSON → Cliente


import com.example.API_REST_Spring_APS3.usuario.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

//Controller = entrada/saída da API.
@RestController // Indica que a classe é um controller e que todos os métodos retornarão um JSON
@RequestMapping("/cartoes")  // Define o caminho base da API: todos os endpoints começam com /cartoes.
public class CartaoController {

    @Autowired
    private CartaoService cartaoService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public Collection<Cartao> listarCartoesController() {
        return cartaoService.listarCartoes();
    }

    @GetMapping("/{numeroCartao}")
    public Cartao buscarCartaoController(@PathVariable String numeroCartao) {
        return cartaoService.buscarPorNumero(numeroCartao);
    }

    @PostMapping
    public ResponseEntity<?> salvarCartaoController(
            @RequestBody Cartao cartao,
            @RequestHeader("Authorization") String token) {
        try {
            usuarioService.validarToken(token);
            Cartao novoCartao = cartaoService.salvarCartao(cartao);
            return ResponseEntity.ok(novoCartao);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @DeleteMapping("/{numeroCartao}")
    public ResponseEntity<?> deletarCartaoController(
            @PathVariable String numeroCartao,
            @RequestHeader("Authorization") String token) {
        usuarioService.validarToken(token);
        cartaoService.deletar(numeroCartao);
        return ResponseEntity.noContent().build();
    }


}