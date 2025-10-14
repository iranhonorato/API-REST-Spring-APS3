package com.example.API_REST_Spring_APS3.cliente;

import com.example.API_REST_Spring_APS3.usurario.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;



@RestController
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public Collection<Cliente> listarClientesController() {
        return clienteService.listarClientes();
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<?> buscarPorCpfController(@PathVariable String cpf) {
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
        try {
            usuarioService.validarToken(token);
            Cliente clienteEditado = clienteService.editarCliente(cpf, cliente);
            return ResponseEntity.ok(clienteEditado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}