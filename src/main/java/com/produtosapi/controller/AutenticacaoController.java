package com.produtosapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.produtosapi.model.AutenticacaoDTO;
import com.produtosapi.model.LoginResponse;
import com.produtosapi.model.RegistrarUsuario;
import com.produtosapi.model.Usuario;
import com.produtosapi.repository.UsuarioRepository;
import com.produtosapi.service.TokenService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/auth")
public class AutenticacaoController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;
    
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login (@RequestBody @Valid AutenticacaoDTO dto) {
        
        var usernamePassword = new UsernamePasswordAuthenticationToken(dto.login(), dto.senha());
        var auth = authenticationManager.authenticate(usernamePassword);
        
        var token = tokenService.gerarToken((Usuario) auth.getPrincipal());

        return ResponseEntity.status(HttpStatus.OK).body(new LoginResponse(token));
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrar(@RequestBody @Valid RegistrarUsuario user) {
        
        if(usuarioRepository.findByLogin(user.login()) != null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        String encryptedPassword = new BCryptPasswordEncoder().encode(user.senha());
        Usuario newUser = new Usuario(user.login(), encryptedPassword, user.perfil());

        this.usuarioRepository.save(newUser);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    
}
