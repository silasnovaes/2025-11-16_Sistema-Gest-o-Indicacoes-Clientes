package com.silasnovaes.indica.service.implementation;

import com.silasnovaes.indica.domain.*;
import com.silasnovaes.indica.domain.enums.NivelIndicante;
import com.silasnovaes.indica.domain.enums.StatusComissao;
import com.silasnovaes.indica.repository.interfaces.ComissaoRepository;
import com.silasnovaes.indica.repository.interfaces.UsuarioRepository;
import com.silasnovaes.indica.service.interfaces.CalculoComissaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CalculoComissaoServiceImpl implements CalculoComissaoService {

    @Autowired
    private ComissaoRepository comissaoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository; // Necessário para salvar o novo nível do usuário

    @Override
    public List<Comissao> calcularEArmazenarComissao(Vendedor vendedor, Indicacao indicacao, double valorPago, String produtoContratado) {

        Regulamento regulamento = vendedor.getRegulamentoProprio();
        if (regulamento == null) {
            throw new RuntimeException("ERRO: O Vendedor deste indicante não configurou o regulamento.");
        }

        ClienteIndicante indicante = indicacao.getClienteIndicante();

        // --- 1. LÓGICA DE NÍVEIS DINÂMICOS (NOVA REGRA) ---

        // A) Calcular Total Acumulado Histórico (Aprovadas + Pagas)
        double totalHistorico = indicante.getCarteira().getComissoes().stream()
                .filter(c -> c.getStatus() == StatusComissao.APROVADA || c.getStatus() == StatusComissao.PAGA)
                .mapToDouble(Comissao::getValorComissao)
                .sum();

        // B) Definir Percentual Base para Teste (Bronze ou atual)
        // Se não tiver regra específica no mapa, assume 30% (Bronze Padrão)
        double percentualBase = 0.30;
        if (regulamento.getRegrasComissao().containsKey(produtoContratado)) {
            // Tenta pegar o percentual do nível atual, se existir
            Map<NivelIndicante, Double> regrasNivel = regulamento.getRegrasComissao().get(produtoContratado);
            if (regrasNivel.containsKey(indicante.getNivel())) {
                percentualBase = regrasNivel.get(indicante.getNivel());
            }
        }

        // C) Calcular Projeção (Quanto ele ganharia nesta venda com o nível atual)
        double comissaoProjetada = valorPago * percentualBase;
        double totalComProjecao = totalHistorico + comissaoProjetada;

        // D) Verificar Upgrade de Nível (Regra de 10k e 5k)
        NivelIndicante novoNivel = indicante.getNivel(); // Começa com o atual
        boolean subiuDeNivel = false;

        if (totalComProjecao >= 10000) {
            if (indicante.getNivel() != NivelIndicante.OURO) {
                novoNivel = NivelIndicante.OURO;
                subiuDeNivel = true;
                System.out.println("🌟 PARABÉNS! O Indicante atingiu OURO (> R$ 10k) nesta venda!");
            }
        } else if (totalComProjecao >= 5000) {
            // Só vira Prata se ainda não for Ouro (para não rebaixar quem já é Ouro)
            if (indicante.getNivel() != NivelIndicante.PRATA && indicante.getNivel() != NivelIndicante.OURO) {
                novoNivel = NivelIndicante.PRATA;
                subiuDeNivel = true;
                System.out.println("🚀 PARABÉNS! O Indicante atingiu PRATA (> R$ 5k) nesta venda!");
            }
        }

        // E) Aplicar Novo Nível se houve mudança
        if (subiuDeNivel) {
            indicante.setNivel(novoNivel);
            usuarioRepository.save(indicante); // Salva no banco (Mock)
        }

        // --- 2. CÁLCULO FINAL COM A REGRA DO NÍVEL (ATUALIZADO) ---

        double percentualFinal;
        int numParcelas;

        // Aplica as regras fixas que você definiu para os novos níveis
        if (indicante.getNivel() == NivelIndicante.OURO) {
            percentualFinal = 0.50; // 50%
            numParcelas = 2;        // 2 Parcelas fixas
        } else if (indicante.getNivel() == NivelIndicante.PRATA) {
            percentualFinal = 0.40; // 40%
            numParcelas = 2;        // 2 Parcelas fixas
        } else {
            // Se for BRONZE, usa a regra do regulamento do vendedor (Map) ou padrão
            // Padrão de fallback: 30% em 2x (como era Amil)
            percentualFinal = 0.30;
            numParcelas = 2;

            // Tenta buscar no mapa do vendedor para ser mais preciso
            if (regulamento.getRegrasComissao().containsKey(produtoContratado)) {
                Map<NivelIndicante, Double> regras = regulamento.getRegrasComissao().get(produtoContratado);
                if (regras.containsKey(NivelIndicante.BRONZE)) {
                    percentualFinal = regras.get(NivelIndicante.BRONZE);
                }
            }
            if (regulamento.getRegrasParcelamento().containsKey(produtoContratado)) {
                numParcelas = regulamento.getRegrasParcelamento().get(produtoContratado);
            }
        }

        // --- 3. GERAÇÃO DAS COMISSÕES ---

        double valorTotalComissao = valorPago * percentualFinal;
        double valorPorParcela = valorTotalComissao / numParcelas;
        double percentualPorParcela = percentualFinal / numParcelas;

        List<Comissao> comissoesGeradas = new ArrayList<>();

        for (int i = 1; i <= numParcelas; i++) {
            Comissao comissao = new Comissao(indicacao, valorPago, percentualPorParcela, i, numParcelas);
            // Ajuste fino do valor
            comissao.setValorComissao(valorPorParcela);

            // Salvar
            Comissao salva = comissaoRepository.save(comissao);
            indicante.getCarteira().getComissoes().add(salva);
            comissoesGeradas.add(salva);
        }

        return comissoesGeradas;
    }
}