package com.silasnovaes.indica.service.implementation;

import com.silasnovaes.indica.domain.ClienteIndicante;
import com.silasnovaes.indica.domain.Usuario;
import com.silasnovaes.indica.domain.enums.NivelIndicante;
import com.silasnovaes.indica.domain.enums.StatusIndicacao;
import com.silasnovaes.indica.repository.interfaces.IndicacaoRepository;
import com.silasnovaes.indica.repository.interfaces.UsuarioRepository;
import com.silasnovaes.indica.service.interfaces.GamificacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GamificacaoServiceImpl implements GamificacaoService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private IndicacaoRepository indicacaoRepository;

    @Override
    public void avaliarNiveisTrimestralmente() {
        for (Usuario usuario : usuarioRepository.findAll()) {
            if (usuario instanceof ClienteIndicante) {
                ClienteIndicante indicante = (ClienteIndicante) usuario;

                // Contar vendas "CONTRATOU"
                long vendasFechadas = indicacaoRepository.findByClienteIndicante(indicante).stream()
                        .filter(i -> i.getStatus() == StatusIndicacao.CONTRATOU)
                        .count();

                NivelIndicante novoNivel;
                if (vendasFechadas >= 16) novoNivel = NivelIndicante.OURO;
                else if (vendasFechadas >= 10) novoNivel = NivelIndicante.PRATA;
                else novoNivel = NivelIndicante.BRONZE;

                if (indicante.getNivel() != novoNivel) {
                    System.out.println("GAMIFICAÇÃO: " + indicante.getNome() + " subiu para " + novoNivel);
                    indicante.setNivel(novoNivel);
                    usuarioRepository.save(indicante);
                }
            }
        }
    }
}