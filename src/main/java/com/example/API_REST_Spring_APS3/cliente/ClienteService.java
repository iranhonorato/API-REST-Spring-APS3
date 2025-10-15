package com.example.API_REST_Spring_APS3.cliente;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    // 🔹 Injeção via construtor (boa prática)
    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Collection<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    public Cliente buscarPorCpf(String cpf) {
        return clienteRepository.findById(cpf)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado com CPF: " + cpf));
    }

    @Transactional
    public Cliente salvarCliente(Cliente cliente) {
        if (cliente.getCpf() == null || cliente.getCpf().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O CPF do cliente não pode ser nulo ou vazio");
        }

        if (clienteRepository.existsById(cliente.getCpf())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Já existe um cliente com esse CPF");
        }

        return clienteRepository.save(cliente);
    }

    @Transactional
    public Cliente editarCliente(String cpf, Cliente novosDados) {
        Cliente existente = clienteRepository.findById(cpf)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado com CPF: " + cpf));

        existente.setNome(novosDados.getNome());
        existente.setDataNascimento(novosDados.getDataNascimento());
        existente.setSalario(novosDados.getSalario());

        return clienteRepository.save(existente);
    }
}
