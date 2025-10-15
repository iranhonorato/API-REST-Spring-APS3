package com.example.API_REST_Spring_APS3.cliente;

import com.example.API_REST_Spring_APS3.usuario.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    private final ClienteService clienteService;
    private final UsuarioService usuarioService;

    // Constructor injection (melhor prática)
    public ClienteController(ClienteService clienteService, UsuarioService usuarioService) {
        this.clienteService = clienteService;
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<Collection<Cliente>> listarClientesController() {
        Collection<Cliente> clientes = clienteService.listarClientes();
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<Cliente> buscarPorCpfController(@PathVariable String cpf) {
        Cliente cliente = clienteService.buscarPorCpf(cpf);
        if (cliente == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cliente);
    }

    @PutMapping("/{cpf}")
    public ResponseEntity<?> editarClienteController(
            @PathVariable String cpf,
            @RequestBody Cliente cliente,
            @RequestHeader("Authorization") String token) {

        // Mantive a lógica do token exatamente como você pediu
        usuarioService.validarToken(token);

        try {
            Cliente clienteEditado = clienteService.editarCliente(cpf, cliente);
            return ResponseEntity.ok(clienteEditado);
        } catch (IllegalArgumentException e) {
            // Mantive o comportamento: 400 Bad Request com a mensagem do service
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
