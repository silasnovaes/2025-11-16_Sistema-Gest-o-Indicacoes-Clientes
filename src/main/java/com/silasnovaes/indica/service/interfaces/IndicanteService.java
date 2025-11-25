package com.silasnovaes.indica.service.interfaces;

import com.silasnovaes.indica.domain.Carteira;
import com.silasnovaes.indica.domain.Comissao;
import com.silasnovaes.indica.domain.Indicacao;

import java.util.List;

public interface IndicanteService {
    Indicacao criarIndicacao(Long indicanteId, String nomeClienteComprador, String telefoneClienteComprador);
    Comissao aprovarComissao(Long comissaoId);
    Comissao contestarComissao(Long comissaoId, String motivo);
    List<Indicacao> verMinhasIndicacoes(Long indicanteId);
    Carteira verMinhaCarteira(Long indicanteId);
}