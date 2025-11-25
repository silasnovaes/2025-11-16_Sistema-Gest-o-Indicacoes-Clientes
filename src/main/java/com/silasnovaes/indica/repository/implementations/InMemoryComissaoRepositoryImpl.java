package com.silasnovaes.indica.repository.implementation;

import com.silasnovaes.indica.domain.Carteira;
import com.silasnovaes.indica.domain.Comissao;
import com.silasnovaes.indica.repository.interfaces.ComissaoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class InMemoryComissaoRepositoryImpl implements ComissaoRepository {

    private static final Map<Long, Comissao> database = new ConcurrentHashMap<>();
    private static final AtomicLong idCounter = new AtomicLong(0);

    @Override
    public Comissao save(Comissao comissao) {
        if (comissao.getId() == null) {
            comissao.setId(idCounter.incrementAndGet());
        }
        database.put(comissao.getId(), comissao);
        return comissao;
    }

    @Override
    public Optional<Comissao> findById(Long id) {
        return Optional.ofNullable(database.get(id));
    }

    @Override
    public List<Comissao> findByCarteira(Carteira carteira) {
        // Na simulação, filtramos pelo ID do dono da carteira
        Long donoId = carteira.getProprietario().getId();
        return database.values().stream()
                .filter(c -> c.getIndicacao().getClienteIndicante().getId().equals(donoId))
                .collect(Collectors.toList());
    }
}