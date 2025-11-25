package com.silasnovaes.indica.domain;

import com.silasnovaes.indica.domain.enums.StatusComissao;

public class Comissao {

    private Long id;
    private Indicacao indicacao;
    private double valorBasePagoPeloCliente;
    private double percentualAplicado;
    private double valorComissao;
    private StatusComissao status;
    private String motivoContestacao;

    private int parcelaAtual;
    private int totalParcelas;

    public Comissao(Indicacao indicacao, double valorBase, double percentual, int parcelaAtual, int totalParcelas) {
        this.indicacao = indicacao;
        this.valorBasePagoPeloCliente = valorBase;
        this.percentualAplicado = percentual;
        this.valorComissao = valorBase * percentual;
        this.parcelaAtual = parcelaAtual;
        this.totalParcelas = totalParcelas;
        this.status = StatusComissao.PENDENTE_APROVACAO;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Indicacao getIndicacao() { return indicacao; }
    public void setIndicacao(Indicacao indicacao) { this.indicacao = indicacao; }

    public double getValorBasePagoPeloCliente() { return valorBasePagoPeloCliente; }
    public void setValorBasePagoPeloCliente(double valorBasePagoPeloCliente) { this.valorBasePagoPeloCliente = valorBasePagoPeloCliente; }

    public double getPercentualAplicado() { return percentualAplicado; }
    public void setPercentualAplicado(double percentualAplicado) { this.percentualAplicado = percentualAplicado; }

    public double getValorComissao() { return valorComissao; }
    public void setValorComissao(double valorComissao) { this.valorComissao = valorComissao; }

    public StatusComissao getStatus() { return status; }
    public void setStatus(StatusComissao status) { this.status = status; }

    public String getMotivoContestacao() { return motivoContestacao; }
    public void setMotivoContestacao(String motivoContestacao) { this.motivoContestacao = motivoContestacao; }

    public int getParcelaAtual() { return parcelaAtual; }
    public void setParcelaAtual(int parcelaAtual) { this.parcelaAtual = parcelaAtual; }

    public int getTotalParcelas() { return totalParcelas; }
    public void setTotalParcelas(int totalParcelas) { this.totalParcelas = totalParcelas; }
}