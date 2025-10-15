package com.example.API_REST_Spring_APS3.usuario;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UsuarioService {

    // armazenamento em memória (para produção troque por JPA + repositorio)
    private final Map<String, Usuario> usuariosDB = new ConcurrentHashMap<>(); // key = email -> Usuario (password hashed)
    private final Map<String, Usuario> tokensDB = new ConcurrentHashMap<>();   // key = token -> Usuario (sem senha)

    public Usuario cadastrarUsuario(Usuario usuario) {
        if (usuario == null || usuario.getEmail() == null || usuario.getPassword() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email e senha são obrigatórios");
        }

        if (usuariosDB.containsKey(usuario.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Usuário já cadastrado");
        }

        String plain = usuario.getPassword();
        String hashed = BCrypt.hashpw(plain, BCrypt.gensalt());
        Usuario stored = new Usuario(usuario.getEmail(), hashed);
        usuariosDB.put(stored.getEmail(), stored);

        // Nunca retornamos a senha (mesmo hash) na resposta: retornamos um DTO com password = null
        return new Usuario(stored.getEmail(), null);
    }

    public Collection<Usuario> listarUsuarios() {
        // retorna cópias sem senha para não vazar hashes
        return usuariosDB.values().stream()
                .map(u -> new Usuario(u.getEmail(), null))
                .toList();
    }

    public String login(Usuario usuario) {
        if (usuario == null || usuario.getEmail() == null || usuario.getPassword() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email e senha são obrigatórios");
        }

        Usuario userDB = usuariosDB.get(usuario.getEmail());
        if (userDB == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuário ou senha inválidos");
        }

        boolean ok = BCrypt.checkpw(usuario.getPassword(), userDB.getPassword());
        if (!ok) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuário ou senha inválidos");
        }

        String token = UUID.randomUUID().toString();
        // guarde apenas info não-sensível (email) associada ao token
        tokensDB.put(token, new Usuario(userDB.getEmail(), null));
        return token;
    }

    public Usuario validarToken(String token) {
        if (token == null || token.isBlank()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token ausente");
        }
        Usuario usuario = tokensDB.get(token);
        if (usuario == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token inválido");
        }
        return usuario; // retorna email (sem senha)
    }


}
