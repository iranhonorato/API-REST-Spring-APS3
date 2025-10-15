package com.example.API_REST_Spring_APS3.usuario;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<Usuario> cadastrarUsuarioController(@RequestBody Usuario usuario) {
        Usuario criado = usuarioService.cadastrarUsuario(usuario);
        return ResponseEntity.status(201).body(criado);
    }

    @GetMapping
    public ResponseEntity<Collection<Usuario>> listarUsuarioController() {
        return ResponseEntity.ok(usuarioService.listarUsuarios());
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Usuario usuario) {
        String token = usuarioService.login(usuario);
        return ResponseEntity.ok(token);
    }



}
