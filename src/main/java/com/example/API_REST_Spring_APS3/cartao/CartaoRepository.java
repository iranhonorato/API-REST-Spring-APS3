package com.example.API_REST_Spring_APS3.cartao;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CartaoRepository extends JpaRepository<Cartao, String> {
    // Aqui você pode adicionar consultas personalizadas, se precisar depois
}