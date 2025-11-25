package com.silasnovaes.indica.service.implementation;

import com.silasnovaes.indica.domain.*;
import com.silasnovaes.indica.domain.enums.StatusIndicacao;
import com.silasnovaes.indica.repository.interfaces.IndicacaoRepository;
import com.silasnovaes.indica.repository.interfaces.UsuarioRepository;
import com.silasnovaes.indica.service.interfaces.CalculoComissaoService;
import com.silasnovaes.indica.service.interfaces.VendedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VendedorServiceImpl implements VendedorService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private IndicacaoRepository indicacaoRepository;
    @Autowired
    private CalculoComissaoService calculoComissaoService;

    @Override
    public Vendedor cadastrarVendedor(String nome, String email, String senha) {
        Vendedor vendedor = new Vendedor(nome, email, senha);
        return (Vendedor) usuarioRepository.save(vendedor);
    }

    @Override
    public ClienteIndicante cadastrarClienteIndicante(Long vendedorId, String nome, String email, String senha, String chavePix) {
        Vendedor vendedor = (Vendedor) usuarioRepository.findById(vendedorId)
                .orElseThrow(() -> new RuntimeException("Vendedor não encontrado"));

        ClienteIndicante indicante = new ClienteIndicante(nome, email, senha, vendedor);
        indicante.setChavePix(chavePix);

        ClienteIndicante salvo = (ClienteIndicante) usuarioRepository.save(indicante);
        vendedor.getIndicantesAssociados().add(salvo);
        return salvo;
    }

    @Override
    public void definirRegulamento(Long vendedorId, Regulamento regulamento) {
        Vendedor vendedor = (Vendedor) usuarioRepository.findById(vendedorId)
                .orElseThrow(() -> new RuntimeException("Vendedor não encontrado"));
        vendedor.setRegulamentoProprio(regulamento);
        usuarioRepository.save(vendedor);
    }

    @Override
    public void moverIndicacaoCRM(Long indicacaoId, StatusIndicacao novoStatus) {
        Indicacao indicacao = indicacaoRepository.findById(indicacaoId)
                .orElseThrow(() -> new RuntimeException("Indicação não encontrada"));
        indicacao.setStatus(novoStatus);
        indicacaoRepository.save(indicacao);
    }

    @Override
    public void alertarNovaComissao(Long indicacaoId, double valorPago, String produtoContratado) {
        Indicacao indicacao = indicacaoRepository.findById(indicacaoId)
                .orElseThrow(() -> new RuntimeException("Indicação não encontrada"));

        if (indicacao.getStatus() != StatusIndicacao.CONTRATOU) {
            throw new RuntimeException("Erro: Só é possível pagar comissão para vendas com status CONTRATOU.");
        }

        Vendedor vendedor = indicacao.getVendedor();
        calculoComissaoService.calcularEArmazenarComissao(vendedor, indicacao, valorPago, produtoContratado);
    }

    @Override
    public List<Indicacao> listarMinhasIndicacoes(Long vendedorId) {
        Vendedor vendedor = (Vendedor) usuarioRepository.findById(vendedorId)
                .orElseThrow(() -> new RuntimeException("Vendedor não encontrado"));
        return indicacaoRepository.findByVendedor(vendedor);
    }
}