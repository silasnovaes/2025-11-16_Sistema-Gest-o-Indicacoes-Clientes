package repository.interfaces;

import domain.ClienteIndicante;
import domain.Indicacao;
import domain.Vendedor;
import java.util.List;
import java.util.Optional;

public interface IndicacaoRepository {
    Indicacao save(Indicacao indicacao);
    Optional<Indicacao> findById(Long id);
    List<Indicacao> findByVendedor(Vendedor vendedor);
    List<Indicacao> findByClienteIndicante(ClienteIndicante clienteIndicante);
}