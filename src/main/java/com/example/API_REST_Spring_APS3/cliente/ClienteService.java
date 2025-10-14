package com.example.API_REST_Spring_APS3.cliente;

import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;



@Service
public class ClienteService {
    private final Map<String, Cliente> clientes = new HashMap<>();


    public Collection<Cliente> listarClientes() {return clientes.values();}


    public Cliente buscarPorCpf(String cpf) {
        return clientes.get(cpf);
    }

    public Cliente salvarCliente(Cliente cliente) {
        if (cliente.getCpf() == null || cliente.getCpf().isEmpty()) {
            throw new IllegalArgumentException("O CPF do cliente não pode ser nulo ou vazio");
        }

        if (clientes.containsKey(cliente.getCpf())) {
            throw new IllegalArgumentException("Já existe um cliente com esse CPF");

        }

        clientes.put(cliente.getCpf(), cliente);
        return cliente;
    }

    public Cliente editarCliente(String cpf, Cliente novosDados) {
        if (!clientes.containsKey(cpf)) {
            throw new IllegalArgumentException("Cliente não encontrado com CPF: " + cpf);
        }
        clientes.put(cpf, novosDados);
        return novosDados;
    }

}