package com.example.API_REST_Spring_APS3.contaCorrente;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ContaCorrenteRepository extends JpaRepository<ContaCorrente, String> {
    // Se quiser procurar por agencia+conta depois, adicione métodos customizados aqui.
}
