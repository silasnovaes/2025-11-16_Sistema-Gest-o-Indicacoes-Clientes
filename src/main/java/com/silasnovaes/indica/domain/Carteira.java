package com.silasnovaes.indica.domain;

import java.util.ArrayList;
import java.util.List;

public class Carteira {

    private Long id;
    private ClienteIndicante proprietario;
    private List<Comissao> comissoes;

    public Carteira(ClienteIndicante proprietario) {
        this.proprietario = proprietario;
        this.comissoes = new ArrayList<>();
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public ClienteIndicante getProprietario() { return proprietario; }
    public void setProprietario(ClienteIndicante proprietario) { this.proprietario = proprietario; }

    public List<Comissao> getComissoes() { return comissoes; }
    public void setComissoes(List<Comissao> comissoes) { this.comissoes = comissoes; }
}