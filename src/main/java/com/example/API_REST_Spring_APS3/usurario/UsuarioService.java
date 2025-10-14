package com.example.API_REST_Spring_APS3.usurario;

import org.mindrot.jbcrypt.BCrypt;
// Para utilizar o import acima você precisa fazer:
//<dependency>
//        <groupId>org.mindrot</groupId>
//        <artifactId>jbcrypt</artifactId>
//        <version>0.4</version>
//</dependency>




import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;



@Service
public class UsuarioService {
    private final HashMap<String, Usuario> usuariosDB = new HashMap<>();
    private final HashMap<String, Usuario> tokensDB = new HashMap<>();

    public Usuario cadastrarUsuario(Usuario usuario) {
        String password = usuario.getPassword();
//        gensalt() gera um salt aleatório. O salt é um valor aleatório acrescentado à senha antes do hash
//        hashpw() aplica o hash e já armazena a senha de forma segura.
        usuario.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
        usuariosDB.put(usuario.getEmail(), usuario);
        return usuario;
    };

    public Collection<Usuario> listarUsuarios() {
        return usuariosDB.values();
    };


    public String login(Usuario usuario) {
        Usuario userDB = usuariosDB.get(usuario.getEmail());


//        BCrypt.checkpw(senhaDigitada, senhaArmazenada);
        if (BCrypt.checkpw(usuario.getPassword(), userDB.getPassword())) {
//            O BCrypt sabe verificar se a senha digitada gera o mesmo hash (considerando o salt).
//            Se bater, a senha está correta.

            String token = UUID.randomUUID().toString();
            tokensDB.put(token, usuario);
            return token;
        }
        throw new RuntimeException("Usuário ou senha inválidos");
    }


    public Usuario validarToken(String token) {
        Usuario usuario = tokensDB.get(token);

        if (usuario == null) {
            throw new RuntimeException("Token Inválido");
        }
        return usuario;
    }



}
