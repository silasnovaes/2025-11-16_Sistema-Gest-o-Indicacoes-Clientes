package com.silasnovaes.indica.controller;

import com.silasnovaes.indica.domain.*;
import com.silasnovaes.indica.service.interfaces.CalculoComissaoService;
import com.silasnovaes.indica.service.interfaces.IndicanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class IndicanteController {

    @Autowired private IndicanteService indicanteService;
    @Autowired private CalculoComissaoService calculoComissaoService;

    public Indicacao criarIndicacao(Long indicanteId, String nome, String telefone) {
        return indicanteService.criarIndicacao(indicanteId, nome, telefone);
    }

    public List<Comissao> gerarPropostaComissao(ClienteIndicante indicante, Indicacao indicacao, double valor, String produto) {
        return calculoComissaoService.calcularEArmazenarComissao(indicante.getVendedor(), indicacao, valor, produto);
    }

    public Carteira verMinhaCarteira(Long indicanteId) {
        return indicanteService.verMinhaCarteira(indicanteId);
    }
}