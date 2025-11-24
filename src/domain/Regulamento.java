package domain;

import domain.enums.NivelIndicante;
import java.util.HashMap;
import java.util.Map;

public class Regulamento {

    private Long id;
    private Vendedor vendedor;
    private String textoRegulamento;

    // Maps para regras
    private Map<String, Map<NivelIndicante, Double>> regrasComissao;
    private Map<String, Integer> regrasParcelamento;

    public Regulamento(Vendedor vendedor) {
        this.vendedor = vendedor;
        this.regrasComissao = new HashMap<>();
        this.regrasParcelamento = new HashMap<>();
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Vendedor getVendedor() { return vendedor; }
    public void setVendedor(Vendedor vendedor) { this.vendedor = vendedor; }

    public String getTextoRegulamento() { return textoRegulamento; }
    public void setTextoRegulamento(String textoRegulamento) { this.textoRegulamento = textoRegulamento; }

    public Map<String, Map<NivelIndicante, Double>> getRegrasComissao() { return regrasComissao; }
    public void setRegrasComissao(Map<String, Map<NivelIndicante, Double>> regrasComissao) { this.regrasComissao = regrasComissao; }

    public Map<String, Integer> getRegrasParcelamento() { return regrasParcelamento; }
    public void setRegrasParcelamento(Map<String, Integer> regrasParcelamento) { this.regrasParcelamento = regrasParcelamento; }
}