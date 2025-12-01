package com.silasnovaes.indica.controller;

import com.silasnovaes.indica.domain.ClienteIndicante;
import com.silasnovaes.indica.domain.Indicacao;
import com.silasnovaes.indica.domain.Vendedor;
import com.silasnovaes.indica.domain.enums.StatusIndicacao;
import com.silasnovaes.indica.service.interfaces.VendedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class VendedorController {

    @Autowired
    private VendedorService vendedorService;

    public Vendedor autoCadastro(String nome, String email, String senha) {
        return vendedorService.cadastrarVendedor(nome, email, senha);
    }

    public List<Indicacao> verMeuCRM(Long vendedorId) {
        return vendedorService.listarMinhasIndicacoes(vendedorId);
    }

    public void moverCardCRM(Long indicacaoId, StatusIndicacao status) {
        vendedorService.moverIndicacaoCRM(indicacaoId, status);
    }

    public ClienteIndicante cadastrarIndicante(Long vendedorId, String nome, String email, String senha, String pix) {
        return vendedorService.cadastrarClienteIndicante(vendedorId, nome, email, senha, pix);
    }
}