package repository.interfaces;

import domain.Carteira;
import domain.Comissao;
import java.util.List;
import java.util.Optional;

public interface ComissaoRepository {
    Comissao save(Comissao comissao);
    Optional<Comissao> findById(Long id);
    List<Comissao> findByCarteira(Carteira carteira);
}