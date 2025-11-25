package com.silasnovaes.indica.service.implementation;

import com.silasnovaes.indica.domain.*;
import com.silasnovaes.indica.domain.enums.StatusComissao;
import com.silasnovaes.indica.repository.interfaces.ComissaoRepository;
import com.silasnovaes.indica.repository.interfaces.IndicacaoRepository;
import com.silasnovaes.indica.repository.interfaces.UsuarioRepository;
import com.silasnovaes.indica.service.interfaces.IndicanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IndicanteServiceImpl implements IndicanteService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private IndicacaoRepository indicacaoRepository;
    @Autowired
    private ComissaoRepository comissaoRepository;

    @Override
    public Indicacao criarIndicacao(Long indicanteId, String nomeClienteComprador, String telefoneClienteComprador) {
        ClienteIndicante indicante = (ClienteIndicante) usuarioRepository.findById(indicanteId)
                .orElseThrow(() -> new RuntimeException("Indicante não encontrado"));

        Indicacao indicacao = new Indicacao(
                indicante,
                indicante.getVendedor(),
                nomeClienteComprador,
                telefoneClienteComprador
        );

        return indicacaoRepository.save(indicacao);
    }

    @Override
    public Comissao aprovarComissao(Long comissaoId) {
        Comissao comissao = comissaoRepository.findById(comissaoId)
                .orElseThrow(() -> new RuntimeException("Comissão não encontrada"));

        comissao.setStatus(StatusComissao.APROVADA);
        return comissaoRepository.save(comissao);
    }

    @Override
    public Comissao contestarComissao(Long comissaoId, String motivo) {
        Comissao comissao = comissaoRepository.findById(comissaoId)
                .orElseThrow(() -> new RuntimeException("Comissão não encontrada"));

        comissao.setStatus(StatusComissao.CONTESTADA);
        comissao.setMotivoContestacao(motivo);
        return comissaoRepository.save(comissao);
    }

    @Override
    public List<Indicacao> verMinhasIndicacoes(Long indicanteId) {
        ClienteIndicante indicante = (ClienteIndicante) usuarioRepository.findById(indicanteId)
                .orElseThrow(() -> new RuntimeException("Indicante não encontrado"));
        return indicacaoRepository.findByClienteIndicante(indicante);
    }

    @Override
    public Carteira verMinhaCarteira(Long indicanteId) {
        ClienteIndicante indicante = (ClienteIndicante) usuarioRepository.findById(indicanteId)
                .orElseThrow(() -> new RuntimeException("Indicante não encontrado"));

        // Atualiza a lista da carteira buscando do repositório
        List<Comissao> comissoes = comissaoRepository.findByCarteira(indicante.getCarteira());
        indicante.getCarteira().setComissoes(comissoes);

        return indicante.getCarteira();
    }
}