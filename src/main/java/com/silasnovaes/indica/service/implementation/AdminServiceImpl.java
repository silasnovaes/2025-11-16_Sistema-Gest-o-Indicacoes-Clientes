package com.silasnovaes.indica.service.implementation;

import com.silasnovaes.indica.domain.Usuario;
import com.silasnovaes.indica.domain.Vendedor;
import com.silasnovaes.indica.repository.interfaces.UsuarioRepository;
import com.silasnovaes.indica.service.interfaces.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public Vendedor aprovarVendedor(Long vendedorId) {
        Usuario usuario = usuarioRepository.findById(vendedorId)
                .orElseThrow(() -> new RuntimeException("Vendedor não encontrado"));

        if (usuario instanceof Vendedor) {
            usuario.setAtivo(true);
            return (Vendedor) usuarioRepository.save(usuario);
        } else {
            throw new RuntimeException("O ID informado não pertence a um vendedor");
        }
    }

    @Override
    public void bloquearUsuario(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        usuario.setAtivo(false);
        usuarioRepository.save(usuario);
    }

    @Override
    public void excluirUsuario(Long usuarioId) {
        if (usuarioId == 1L) {
            throw new RuntimeException("Não é possível excluir o Administrador principal");
        }
        usuarioRepository.deleteById(usuarioId);
    }

    @Override
    public List<Usuario> listarTodosUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    public List<Vendedor> listarVendedoresPendentes() {
        List<Vendedor> pendentes = new ArrayList<>();
        for (Usuario u : usuarioRepository.findAll()) {
            // Verifica se é Vendedor E se está inativo (ativo == false)
            if (u instanceof Vendedor && !u.isAtivo()) {
                pendentes.add((Vendedor) u);
            }
        }
        return pendentes;
    }
}