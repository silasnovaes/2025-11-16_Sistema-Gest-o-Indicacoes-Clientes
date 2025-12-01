package com.silasnovaes.indica.controller;

import com.silasnovaes.indica.domain.Usuario;
import com.silasnovaes.indica.domain.Vendedor;
import com.silasnovaes.indica.service.interfaces.AdminService;
import com.silasnovaes.indica.service.interfaces.GamificacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class AdminController {

    @Autowired private AdminService adminService;
    @Autowired private GamificacaoService gamificacaoService;

    public List<Vendedor> listarVendedoresPendentes() {
        return adminService.listarVendedoresPendentes();
    }

    public List<Usuario> listarTodosUsuarios() {
        return adminService.listarTodosUsuarios();
    }

    public void aprovarVendedor(Long id) {
        adminService.aprovarVendedor(id);
    }

    public void excluirUsuario(Long id) {
        adminService.excluirUsuario(id);
    }

    public void rodarGamificacao() {
        gamificacaoService.avaliarNiveisTrimestralmente();
    }
}