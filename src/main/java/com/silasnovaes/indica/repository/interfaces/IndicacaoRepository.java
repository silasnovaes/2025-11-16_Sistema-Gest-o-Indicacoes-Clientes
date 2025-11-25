package com.silasnovaes.indica.repository.interfaces;

import com.silasnovaes.indica.domain.ClienteIndicante;
import com.silasnovaes.indica.domain.Indicacao;
import com.silasnovaes.indica.domain.Vendedor;
import java.util.List;
import java.util.Optional;

public interface IndicacaoRepository {
    Indicacao save(Indicacao indicacao);
    Optional<Indicacao> findById(Long id);
    List<Indicacao> findByVendedor(Vendedor vendedor);
    List<Indicacao> findByClienteIndicante(ClienteIndicante clienteIndicante);
}