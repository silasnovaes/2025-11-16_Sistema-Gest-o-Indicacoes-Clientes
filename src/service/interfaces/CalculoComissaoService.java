package service.interfaces;

import domain.Indicacao;
import domain.Vendedor;

public interface CalculoComissaoService {
    void calcularEArmazenarComissao(Vendedor vendedor, Indicacao indicacao, double valorPago, String produtoContratado);
}