package com.silasnovaes.indica.service.interfaces;

import com.silasnovaes.indica.domain.Usuario;
import com.silasnovaes.indica.domain.Vendedor;
import java.util.List;

public interface AdminService {
    Vendedor aprovarVendedor(Long vendedorId);
    void bloquearUsuario(Long usuarioId);
    void excluirUsuario(Long usuarioId);
    // Estes são os novos métodos obrigatórios:
    List<Usuario> listarTodosUsuarios();
    List<Vendedor> listarVendedoresPendentes();
}