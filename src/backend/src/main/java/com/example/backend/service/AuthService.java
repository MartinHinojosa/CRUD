package com.example.backend.service;

import com.example.backend.dto.LoginRequest;
import com.example.backend.dto.LoginResponse;
import com.example.backend.dto.RegisterRequest;
import com.example.backend.dto.UsuarioDTO;
import com.example.backend.model.Usuario;
import com.example.backend.repository.UsuarioRepository;
import com.example.backend.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    public LoginResponse login(LoginRequest request) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByCorreo(request.getCorreo());
        
        if (usuarioOpt.isEmpty()) {
            throw new RuntimeException("Credenciales inválidas");
        }
        
        Usuario usuario = usuarioOpt.get();
        
        if (!usuario.getActivo()) {
            throw new RuntimeException("Usuario inactivo");
        }
        
        if (!passwordEncoder.matches(request.getPassword(), usuario.getPasswordHash())) {
            throw new RuntimeException("Credenciales inválidas");
        }
        
        String token = jwtUtil.generateToken(usuario.getCorreo(), usuario.getRol());
        
        return new LoginResponse(token, usuario.getCorreo(), usuario.getNombre(), usuario.getRol());
    }
    
    @Transactional
    public LoginResponse register(RegisterRequest request) {
        if (usuarioRepository.existsByCorreo(request.getCorreo())) {
            throw new RuntimeException("El correo ya está registrado");
        }
        
        Usuario usuario = new Usuario();
        usuario.setNombre(request.getNombre());
        usuario.setCorreo(request.getCorreo());
        usuario.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        usuario.setRol(request.getRol() != null && !request.getRol().isEmpty() ? request.getRol() : "empleado");
        usuario.setActivo(true);
        
        usuarioRepository.save(usuario);
        
        String token = jwtUtil.generateToken(usuario.getCorreo(), usuario.getRol());
        
        return new LoginResponse(token, usuario.getCorreo(), usuario.getNombre(), usuario.getRol());
    }
    
    public List<UsuarioDTO> findAllUsuarios() {
        return usuarioRepository.findAll().stream()
                .filter(Usuario::getActivo)
                .map(usuario -> new UsuarioDTO(
                    usuario.getIdUsuario(),
                    usuario.getNombre(),
                    usuario.getCorreo(),
                    usuario.getRol()
                ))
                .collect(Collectors.toList());
    }
}

