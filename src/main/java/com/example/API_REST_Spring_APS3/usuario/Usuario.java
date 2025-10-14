package com.example.API_REST_Spring_APS3.usuario;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Usuario {

    private String email;


    // Faz com que a senha seja apenas escrita (WRITE_ONLY) — nunca será serializada nas respostas JSON
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    public Usuario() {}

    public Usuario(String email, String password) {
        this.email = email;
        this.password = password;
    }


    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}
    public String getPassword() { return password; }
    public void setPassword(String password) {this.password = password;}
}
