package com.example.API_REST_Spring_APS3.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Collection;



@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public Usuario cadastrarUsuarioController(@RequestBody Usuario usuario) {
        Usuario criado = usuarioService.cadastrarUsuario(usuario);
        return ResponseEntity.status(201).body(criado).getBody();
    }

    @GetMapping
    public Collection<Usuario> listarUsuarioController() {
        return usuarioService.listarUsuarios();
    }

    public ResponseEntity<String> login(@RequestBody Usuario usuario) {
        String token = usuarioService.login(usuario);
        return ResponseEntity.ok(token);
    }
}
