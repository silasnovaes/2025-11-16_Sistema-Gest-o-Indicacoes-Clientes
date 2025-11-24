package domain;

import domain.enums.NivelIndicante;
import domain.enums.PerfilUsuario;

public class ClienteIndicante extends Usuario {

    private Vendedor vendedor;
    private Carteira carteira;
    private NivelIndicante nivel;
    private String chavePix;

    public ClienteIndicante(String nome, String email, String senha, Vendedor vendedor) {
        super(nome, email, senha, PerfilUsuario.INDICANTE);
        this.vendedor = vendedor;
        this.nivel = NivelIndicante.BRONZE;
        this.setAtivo(true);
        this.carteira = new Carteira(this);
    }

    // Getters e Setters
    public Vendedor getVendedor() {
        return vendedor;
    }

    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
    }

    public Carteira getCarteira() {
        return carteira;
    }

    public void setCarteira(Carteira carteira) {
        this.carteira = carteira;
    }

    public NivelIndicante getNivel() {
        return nivel;
    }

    public void setNivel(NivelIndicante nivel) {
        this.nivel = nivel;
    }

    public String getChavePix() {
        return chavePix;
    }

    public void setChavePix(String chavePix) {
        this.chavePix = chavePix;
    }
}