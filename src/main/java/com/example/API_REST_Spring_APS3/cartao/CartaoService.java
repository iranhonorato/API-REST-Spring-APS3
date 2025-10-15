package com.example.API_REST_Spring_APS3.cartao;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;

@Service
public class CartaoService {

    private final CartaoRepository cartaoRepository;

    public CartaoService(CartaoRepository cartaoRepository) {
        this.cartaoRepository = cartaoRepository;
    }

    public Collection<Cartao> listarCartoes() {
        return cartaoRepository.findAll();
    }

    public Cartao buscarPorNumero(String numeroCartao) {
        return cartaoRepository.findById(numeroCartao)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cartão não encontrado"));
    }

    @Transactional
    public Cartao salvarCartao(Cartao cartao) {
        if (cartao.getNumeroCartao() == null || cartao.getNumeroCartao().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O número do cartão não pode ser nulo ou vazio");
        }

        if (cartaoRepository.existsById(cartao.getNumeroCartao())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Já existe um cartão com esse número");
        }

        return cartaoRepository.save(cartao);
    }

    @Transactional
    public void deletar(String numeroCartao) {
        if (!cartaoRepository.existsById(numeroCartao)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cartão não encontrado");
        }
        cartaoRepository.deleteById(numeroCartao);
    }
}
