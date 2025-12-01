package com.silasnovaes.indica.controller;

import com.silasnovaes.indica.domain.Usuario;
import com.silasnovaes.indica.repository.interfaces.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> listarTodosParaLogin() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarUsuarioPorId(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }
}