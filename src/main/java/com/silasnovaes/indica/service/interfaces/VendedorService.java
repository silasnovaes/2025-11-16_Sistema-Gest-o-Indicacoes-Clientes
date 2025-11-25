package com.silasnovaes.indica.service.interfaces;

import com.silasnovaes.indica.domain.ClienteIndicante;
import com.silasnovaes.indica.domain.Indicacao;
import com.silasnovaes.indica.domain.Regulamento;
import com.silasnovaes.indica.domain.Vendedor;
import com.silasnovaes.indica.domain.enums.StatusIndicacao;
import java.util.List;

public interface VendedorService {
    Vendedor cadastrarVendedor(String nome, String email, String senha);
    ClienteIndicante cadastrarClienteIndicante(Long vendedorId, String nome, String email, String senha, String chavePix);
    void definirRegulamento(Long vendedorId, Regulamento regulamento);
    void moverIndicacaoCRM(Long indicacaoId, StatusIndicacao novoStatus);
    void alertarNovaComissao(Long indicacaoId, double valorPago, String produtoContratado);
    // Novo método obrigatório:
    List<Indicacao> listarMinhasIndicacoes(Long vendedorId);
}