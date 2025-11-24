package service.interfaces;

import domain.Carteira;
import domain.Comissao;
import domain.Indicacao;

import java.util.List;

public interface IndicanteService {
    Indicacao criarIndicacao(Long indicanteId, String nomeClienteComprador, String telefoneClienteComprador);
    Comissao aprovarComissao(Long comissaoId);
    Comissao contestarComissao(Long comissaoId, String motivo);
    List<Indicacao> verMinhasIndicacoes(Long indicanteId);
    Carteira verMinhaCarteira(Long indicanteId);
}