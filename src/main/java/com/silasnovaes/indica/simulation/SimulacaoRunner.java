package com.silasnovaes.indica.simulation;

import com.silasnovaes.indica.controller.*;
import com.silasnovaes.indica.domain.*;
import com.silasnovaes.indica.domain.enums.*;
import com.silasnovaes.indica.repository.interfaces.UsuarioRepository;
import com.silasnovaes.indica.service.interfaces.AdminService; // Apenas para inicialização de dados se necessário
import com.silasnovaes.indica.service.interfaces.VendedorService; // Apenas para inicialização de dados
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@Component
public class SimulacaoRunner implements CommandLineRunner {

    // --- AGORA USAMOS CONTROLLERS, NÃO SERVICES ---
    @Autowired private AuthController authController;
    @Autowired private AdminController adminController;
    @Autowired private VendedorController vendedorController;
    @Autowired private IndicanteController indicanteController;

    // Mantendo estes serviços apenas para o método 'inicializarDadosIniciais' (Setup)
    // Num cenário real, o setup também seria via controller ou um DataSeeder separado
    @Autowired private VendedorService vendedorServiceSetup;
    @Autowired private AdminService adminServiceSetup;
    @Autowired private UsuarioRepository usuarioRepositorySetup;

    private Scanner scanner = new Scanner(System.in);
    private Usuario usuarioLogado = null;

    @Override
    public void run(String... args) throws Exception {
        inicializarDadosIniciais();

        while (true) {
            if (usuarioLogado == null) {
                menuLogin();
            } else {
                direcionarMenuPorPerfil();
            }
        }
    }

    // --- 1. LOGIN (VIA AUTH CONTROLLER) ---
    private void menuLogin() {
        System.out.println("\n==========================================");
        System.out.println("          SISTEMA INDICA - LOGIN          ");
        System.out.println("==========================================");

        System.out.println("\n🔎 USUÁRIOS DISPONÍVEIS PARA LOGIN:");
        // Chamada ao Controller
        List<Usuario> usuarios = authController.listarTodosParaLogin();

        if (usuarios.isEmpty()) {
            System.out.println("   (Nenhum usuário cadastrado)");
        } else {
            for (Usuario u : usuarios) {
                String statusIcon = u.isAtivo() ? "✅" : "⛔";
                System.out.printf("   [ID: %d] %s %s - %s\n",
                        u.getId(), statusIcon, u.getNome(), u.getPerfil());
            }
        }
        System.out.println("------------------------------------------");

        System.out.println("1 - Fazer Login");
        System.out.println("2 - Cadastrar Novo Vendedor");
        System.out.println("0 - 🛑 ENCERRAR SISTEMA");
        System.out.print("Escolha uma opção: ");

        String op = scanner.nextLine();

        if (op.equals("1")) {
            System.out.print("Digite o ID do Usuário acima: ");
            try {
                Long id = Long.parseLong(scanner.nextLine());
                // Chamada ao Controller
                Usuario u = authController.buscarUsuarioPorId(id);

                if (u != null) {
                    if (!u.isAtivo()) System.out.println("\n⛔ ERRO: Usuário bloqueado ou aguardando aprovação do Admin.");
                    else usuarioLogado = u;
                } else System.out.println("\n❌ ERRO: ID não encontrado.");
            } catch (Exception e) { System.out.println("\n❌ ERRO: Digite um número válido."); }

        } else if (op.equals("2")) {
            System.out.print("Nome: "); String n = scanner.nextLine();
            System.out.print("Email: "); String e = scanner.nextLine();
            // Chamada ao Controller
            Vendedor v = vendedorController.autoCadastro(n, e, "123");
            System.out.println("\n✅ SUCESSO! ID: " + v.getId() + ". Peça ao Admin para aprovar.");

        } else if (op.equals("0")) {
            System.out.println("Encerrando sistema... Até logo!");
            System.exit(0);
        }
    }

    private void direcionarMenuPorPerfil() {
        System.out.println("\n👤 LOGADO COMO: " + usuarioLogado.getNome().toUpperCase() + " [" + usuarioLogado.getPerfil() + "]");
        if (usuarioLogado.getPerfil() == PerfilUsuario.ADMIN) menuAdmin();
        else if (usuarioLogado.getPerfil() == PerfilUsuario.VENDEDOR) menuVendedor();
        else if (usuarioLogado.getPerfil() == PerfilUsuario.INDICANTE) menuIndicante();
    }

    // --- 2. ADMIN (VIA ADMIN CONTROLLER) ---
    private void menuAdmin() {
        System.out.println("1 - 📋 Aprovar Vendedores Pendentes");
        System.out.println("2 - 👥 Listar Todos Usuários Detalhado");
        System.out.println("3 - 🗑️ Excluir Usuário");
        System.out.println("4 - 🏆 Rodar Avaliação Trimestral (Gamificação)");
        System.out.println("0 - 🔙 Logout (Voltar)");
        System.out.print("Opção: ");
        String op = scanner.nextLine();

        try {
            if (op.equals("1")) {
                List<Vendedor> pendentes = adminController.listarVendedoresPendentes();
                if (pendentes.isEmpty()) System.out.println("\n(Nenhum vendedor pendente)");
                else {
                    System.out.println("\n--- PENDENTES ---");
                    for (Vendedor v : pendentes) System.out.println("ID: " + v.getId() + " | Nome: " + v.getNome());
                    System.out.print("Digite o ID para aprovar (ou ENTER p/ cancelar): ");
                    String idStr = scanner.nextLine();
                    if (!idStr.isEmpty()) {
                        adminController.aprovarVendedor(Long.parseLong(idStr));
                        System.out.println("✅ Vendedor Aprovado!");
                    }
                }
            } else if (op.equals("2")) {
                System.out.println("\n--- TODOS USUÁRIOS ---");
                for (Usuario u : adminController.listarTodosUsuarios()) {
                    System.out.println("ID: " + u.getId() + " | " + u.getNome() + " | " + u.getPerfil() + " | Ativo: " + u.isAtivo());
                }
            } else if (op.equals("3")) {
                System.out.print("ID para excluir: ");
                adminController.excluirUsuario(Long.parseLong(scanner.nextLine()));
                System.out.println("🗑️ Usuário excluído.");
            } else if (op.equals("4")) {
                adminController.rodarGamificacao();
                System.out.println("✅ Processo de Gamificação concluído.");
            } else if (op.equals("0")) usuarioLogado = null;
        } catch (Exception e) { System.out.println("❌ Erro: " + e.getMessage()); }
    }

    // --- 3. VENDEDOR (VIA VENDEDOR CONTROLLER) ---
    private void menuVendedor() {
        System.out.println("1 - 📊 CRM (Ver/Mover Indicações)");
        System.out.println("2 - ➕ Cadastrar Cliente Indicante");
        System.out.println("0 - 🔙 Logout");
        System.out.print("Opção: ");
        String op = scanner.nextLine();

        if (op.equals("1")) {
            List<Indicacao> lista = vendedorController.verMeuCRM(usuarioLogado.getId());
            if (lista.isEmpty()) System.out.println("\n(CRM vazio)");
            else {
                System.out.println("\n--- MEU CRM ---");
                for (Indicacao i : lista) {
                    System.out.println("ID Indicação: " + i.getId() + " | Cliente: " + i.getNomeClienteComprador() + " | Status: " + i.getStatus());
                }
                System.out.print("Digite o ID da Indicação para mover (ou ENTER p/ voltar): ");
                String idStr = scanner.nextLine();
                if (!idStr.isEmpty()) {
                    System.out.println("Novo Status: 1-EM_NEGOCIACAO, 2-CONTRATOU, 3-NEGADO");
                    String st = scanner.nextLine();
                    StatusIndicacao novoSt = st.equals("2") ? StatusIndicacao.CONTRATOU :
                            st.equals("3") ? StatusIndicacao.NEGADO : StatusIndicacao.EM_NEGOCIACAO;
                    vendedorController.moverCardCRM(Long.parseLong(idStr), novoSt);
                    System.out.println("✅ Status atualizado!");
                }
            }
        } else if (op.equals("2")) {
            System.out.print("Nome: "); String n = scanner.nextLine();
            System.out.print("Email: "); String e = scanner.nextLine();
            ClienteIndicante novo = vendedorController.cadastrarIndicante(usuarioLogado.getId(), n, e, "123", "Pix");
            System.out.println("✅ Indicante criado! ID: " + novo.getId());
        } else if (op.equals("0")) usuarioLogado = null;
    }

    // --- 4. INDICANTE (VIA INDICANTE CONTROLLER) ---
    private void menuIndicante() {
        ClienteIndicante me = (ClienteIndicante) usuarioLogado;
        System.out.println("Nível Atual: " + me.getNivel());
        System.out.println("1 - ➕ Nova Indicação (Simular Venda)");
        System.out.println("2 - 💰 Ver Minha Carteira");
        System.out.println("0 - 🔙 Logout");
        System.out.print("Opção: ");
        String op = scanner.nextLine();

        if (op.equals("1")) {
            System.out.print("Nome do Cliente Comprador: "); String nome = scanner.nextLine();
            System.out.print("Valor do Plano (R$): ");
            try {
                double valor = Double.parseDouble(scanner.nextLine());

                // Chamada ao Controller
                Indicacao ind = indicanteController.criarIndicacao(me.getId(), nome, "000");
                ind.setStatus(StatusIndicacao.CONTRATOU);

                // Chamada ao Controller
                List<Comissao> comissoes = indicanteController.gerarPropostaComissao(me, ind, valor, "Amil");
                double total = comissoes.stream().mapToDouble(Comissao::getValorComissao).sum();

                System.out.println("\n-----------------------------------------");
                System.out.println("💰 PROPOSTA GERADA PELO SISTEMA");
                System.out.println("   Valor Plano: R$ " + valor);
                System.out.println("   Sua Comissão: R$ " + total);
                System.out.println("   Parcelamento: " + comissoes.size() + "x de R$ " + comissoes.get(0).getValorComissao());
                System.out.println("-----------------------------------------");
                System.out.println("1 - ✅ ACEITAR");
                System.out.println("2 - ⚠️ CONTESTAR");
                System.out.print("Decisão: ");

                if (scanner.nextLine().equals("1")) {
                    comissoes.forEach(c -> c.setStatus(StatusComissao.APROVADA));
                    System.out.println("✅ Maravilha! Comissão aprovada.");
                } else {
                    System.out.print("Motivo da contestação: ");
                    String m = scanner.nextLine();
                    comissoes.forEach(c -> { c.setStatus(StatusComissao.CONTESTADA); c.setMotivoContestacao(m); });
                    System.out.println("⚠️ Registrado. O vendedor analisará.");
                }
            } catch (Exception e) { System.out.println("❌ Erro: " + e.getMessage()); }
        } else if (op.equals("2")) {
            // Chamada ao Controller
            Carteira c = indicanteController.verMinhaCarteira(me.getId());
            System.out.println("\n--- MINHA CARTEIRA ---");
            if (c.getComissoes().isEmpty()) System.out.println("(Vazia)");

            for (Comissao com : c.getComissoes()) {
                System.out.printf("ID: %d | R$ %.2f | %s\n", com.getId(), com.getValorComissao(), com.getStatus());
                if (com.getStatus() == StatusComissao.CONTESTADA) {
                    System.out.println("   Motivo: " + com.getMotivoContestacao());
                }
            }
            System.out.print("\n[ENTER] para voltar...");
            scanner.nextLine();
        } else if (op.equals("0")) usuarioLogado = null;
    }

    private void inicializarDadosIniciais() {
        if (usuarioRepositorySetup.findAll().isEmpty()) {
            Administrador adm = new Administrador("Super Admin", "admin@indica.com", "123");
            usuarioRepositorySetup.save(adm);

            Vendedor v = vendedorServiceSetup.cadastrarVendedor("Silas Vendedor", "vendedor@indica.com", "123");
            adminServiceSetup.aprovarVendedor(v.getId());

            Regulamento reg = new Regulamento(v);
            Map<NivelIndicante, Double> regra = new HashMap<>();
            regra.put(NivelIndicante.BRONZE, 0.30);
            reg.getRegrasComissao().put("Amil", regra);
            reg.getRegrasParcelamento().put("Amil", 2);
            vendedorServiceSetup.definirRegulamento(v.getId(), reg);

            vendedorServiceSetup.cadastrarClienteIndicante(v.getId(), "João Indicante", "joao@indica.com", "123", "Pix");
        }
    }
}