package com.silasnovaes.indica.domain;

import com.silasnovaes.indica.domain.enums.StatusIndicacao;

public class Indicacao {

    private Long id;
    private ClienteIndicante clienteIndicante;
    private Vendedor vendedor;
    private String nomeClienteComprador;
    private String telefoneClienteComprador;
    private StatusIndicacao status;

    public Indicacao() {
    }

    public Indicacao(ClienteIndicante clienteIndicante, Vendedor vendedor, String nomeClienteComprador, String telefoneClienteComprador) {
        this.clienteIndicante = clienteIndicante;
        this.vendedor = vendedor;
        this.nomeClienteComprador = nomeClienteComprador;
        this.telefoneClienteComprador = telefoneClienteComprador;
        this.status = StatusIndicacao.NOVO;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ClienteIndicante getClienteIndicante() {
        return clienteIndicante;
    }

    public void setClienteIndicante(ClienteIndicante clienteIndicante) {
        this.clienteIndicante = clienteIndicante;
    }

    public Vendedor getVendedor() {
        return vendedor;
    }

    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
    }

    public String getNomeClienteComprador() {
        return nomeClienteComprador;
    }

    public void setNomeClienteComprador(String nomeClienteComprador) {
        this.nomeClienteComprador = nomeClienteComprador;
    }

    public String getTelefoneClienteComprador() {
        return telefoneClienteComprador;
    }

    public void setTelefoneClienteComprador(String telefoneClienteComprador) {
        this.telefoneClienteComprador = telefoneClienteComprador;
    }

    public StatusIndicacao getStatus() {
        return status;
    }

    public void setStatus(StatusIndicacao status) {
        this.status = status;
    }
}