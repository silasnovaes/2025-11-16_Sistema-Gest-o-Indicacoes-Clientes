package com.silasnovaes.indica.repository.implementation;

import com.silasnovaes.indica.domain.Usuario;
import com.silasnovaes.indica.repository.interfaces.UsuarioRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryUsuarioRepositoryImpl implements UsuarioRepository {

    // Simula a tabela de usuários
    private static final Map<Long, Usuario> database = new ConcurrentHashMap<>();
    private static final AtomicLong idCounter = new AtomicLong(0);

    @Override
    public Usuario save(Usuario usuario) {
        if (usuario.getId() == null) {
            usuario.setId(idCounter.incrementAndGet());
        }
        database.put(usuario.getId(), usuario);
        return usuario;
    }

    @Override
    public Optional<Usuario> findById(Long id) {
        return Optional.ofNullable(database.get(id));
    }

    @Override
    public Optional<Usuario> findByEmail(String email) {
        return database.values().stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }

    @Override
    public List<Usuario> findAll() {
        return new ArrayList<>(database.values());
    }

    @Override
    public void deleteById(Long id) {
        database.remove(id);
    }
}