package com.silasnovaes.indica.service.interfaces;

import com.silasnovaes.indica.domain.Comissao;
import com.silasnovaes.indica.domain.Indicacao;
import com.silasnovaes.indica.domain.Vendedor;

import java.util.List;

public interface CalculoComissaoService {
    List<Comissao> calcularEArmazenarComissao(Vendedor vendedor, Indicacao indicacao, double valorPago, String produtoContratado);
}