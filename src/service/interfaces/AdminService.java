package service.interfaces;

import domain.Vendedor;

public interface AdminService {
    Vendedor aprovarVendedor(Long vendedorId);
    void bloquearUsuario(Long usuarioId);
    void excluirUsuario(Long usuarioId);
}