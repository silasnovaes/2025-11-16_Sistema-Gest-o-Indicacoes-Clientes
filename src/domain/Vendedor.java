package domain;

import domain.enums.PerfilUsuario;
import java.util.ArrayList;
import java.util.List;

public class Vendedor extends Usuario {

    private List<ClienteIndicante> indicantesAssociados;
    private Regulamento regulamentoProprio;

    public Vendedor(String nome, String email, String senha) {
        super(nome, email, senha, PerfilUsuario.VENDEDOR);
        this.indicantesAssociados = new ArrayList<>();
    }

    // Getters e Setters
    public List<ClienteIndicante> getIndicantesAssociados() {
        return indicantesAssociados;
    }

    public void setIndicantesAssociados(List<ClienteIndicante> indicantesAssociados) {
        this.indicantesAssociados = indicantesAssociados;
    }

    public Regulamento getRegulamentoProprio() {
        return regulamentoProprio;
    }

    public void setRegulamentoProprio(Regulamento regulamentoProprio) {
        this.regulamentoProprio = regulamentoProprio;
    }
}