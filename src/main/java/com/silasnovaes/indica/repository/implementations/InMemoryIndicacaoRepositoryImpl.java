package com.silasnovaes.indica.repository.implementation;

import com.silasnovaes.indica.domain.ClienteIndicante;
import com.silasnovaes.indica.domain.Indicacao;
import com.silasnovaes.indica.domain.Vendedor;
import com.silasnovaes.indica.repository.interfaces.IndicacaoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class InMemoryIndicacaoRepositoryImpl implements IndicacaoRepository {

    private static final Map<Long, Indicacao> database = new ConcurrentHashMap<>();
    private static final AtomicLong idCounter = new AtomicLong(0);

    @Override
    public Indicacao save(Indicacao indicacao) {
        if (indicacao.getId() == null) {
            indicacao.setId(idCounter.incrementAndGet());
        }
        database.put(indicacao.getId(), indicacao);
        return indicacao;
    }

    @Override
    public Optional<Indicacao> findById(Long id) {
        return Optional.ofNullable(database.get(id));
    }

    @Override
    public List<Indicacao> findByVendedor(Vendedor vendedor) {
        return database.values().stream()
                .filter(i -> i.getVendedor().getId().equals(vendedor.getId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Indicacao> findByClienteIndicante(ClienteIndicante clienteIndicante) {
        return database.values().stream()
                .filter(i -> i.getClienteIndicante().getId().equals(clienteIndicante.getId()))
                .collect(Collectors.toList());
    }
}