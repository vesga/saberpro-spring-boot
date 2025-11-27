
package com.uts.saberpro.service;

import com.uts.saberpro.model.Usuario;
import com.uts.saberpro.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder){
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario crearUsuario(String username, String password, String rol){
        Usuario u = new Usuario();
        u.setUsername(username);
        u.setPassword(passwordEncoder.encode(password));
        u.setRol(rol);
        return usuarioRepository.save(u);
    }

    public Optional<Usuario> findByUsername(String username){
        return usuarioRepository.findByUsername(username);
    }
}
