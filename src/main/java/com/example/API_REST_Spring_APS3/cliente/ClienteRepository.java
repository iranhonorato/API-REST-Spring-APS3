package com.example.API_REST_Spring_APS3.cliente;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, String> {
    // String = tipo do ID (CPF)
}
