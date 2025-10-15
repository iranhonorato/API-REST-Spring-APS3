package com.example.API_REST_Spring_APS3.contaCorrente;

import com.example.API_REST_Spring_APS3.cliente.ClienteService;
import com.example.API_REST_Spring_APS3.movimentacao.Movimentacao;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.List;

@Service
public class ContaCorrenteService {

    private final ContaCorrenteRepository contaRepository;
    private final ClienteService clienteService;

    public ContaCorrenteService(ContaCorrenteRepository contaRepository, ClienteService clienteService) {
        this.contaRepository = contaRepository;
        this.clienteService = clienteService;
    }

    public Collection<ContaCorrente> listarContas() {
        return contaRepository.findAll();
    }

    @Transactional
    public ContaCorrente cadastrarConta(ContaCorrente conta) {
        if (conta == null || conta.getConta() == null || conta.getConta().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O número da conta não pode ser nulo ou vazio");
        }

        if (contaRepository.existsById(conta.getConta())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Já existe uma conta com esse número corrente");
        }

        // garante que o cliente exista (ou salva)
        clienteService.salvarCliente(conta.getCliente());

        // ao salvar a conta, cascade (se configurado) persiste cliente/cartoes/movimentacoes
        return contaRepository.save(conta);
    }

    public List<Movimentacao> listarMovimentacoes(String contaId) {
        ContaCorrente conta = contaRepository.findById(contaId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Conta não encontrada"));
        return conta.listaMovimentacoes();
    }

    @Transactional
    public void depositar(String contaId, Float valor) {
        if (valor == null || valor <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Valor de depósito inválido");
        }

        ContaCorrente conta = contaRepository.findById(contaId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Conta não encontrada"));

        // chama lógica de negócio da entidade (que cria movimentacao e atualiza saldo)
        conta.deposito(valor);

        // salva alterações
        contaRepository.save(conta);
    }

    @Transactional
    public void sacar(String contaId, Float valor) {
        if (valor == null || valor <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Valor de saque inválido");
        }

        ContaCorrente conta = contaRepository.findById(contaId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Conta não encontrada"));

        // retirada - método da entidade lança IllegalArgumentException se saldo insuficiente
        try {
            conta.saque(valor);
        } catch (IllegalArgumentException e) {
            // converte para 400 para manter compatibilidade com seu controller atual
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        contaRepository.save(conta);
    }
}
