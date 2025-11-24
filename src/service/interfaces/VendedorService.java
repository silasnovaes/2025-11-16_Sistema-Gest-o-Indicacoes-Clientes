package service.interfaces;

import domain.ClienteIndicante;
import domain.Regulamento;
import domain.Vendedor;
import domain.enums.StatusIndicacao;

public interface VendedorService {
    Vendedor cadastrarVendedor(String nome, String email, String senha);
    ClienteIndicante cadastrarClienteIndicante(Long vendedorId, String nome, String email, String senha, String chavePix);
    void definirRegulamento(Long vendedorId, Regulamento regulamento);
    void moverIndicacaoCRM(Long indicacaoId, StatusIndicacao novoStatus);
    void alertarNovaComissao(Long indicacaoId, double valorPago, String produtoContratado);
}