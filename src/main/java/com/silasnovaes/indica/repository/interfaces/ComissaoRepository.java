package com.silasnovaes.indica.repository.interfaces;

import com.silasnovaes.indica.domain.Carteira;
import com.silasnovaes.indica.domain.Comissao;
import java.util.List;
import java.util.Optional;

public interface ComissaoRepository {
    Comissao save(Comissao comissao);
    Optional<Comissao> findById(Long id);
    List<Comissao> findByCarteira(Carteira carteira);
}