package domain;

import domain.enums.PerfilUsuario;

public class Administrador extends Usuario {

    public Administrador(String nome, String email, String senha) {
        super(nome, email, senha, PerfilUsuario.ADMIN);
        this.setAtivo(true); // Admin já nasce ativo
    }
}



