package src;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Sistema sistema = new Sistema();
        Scanner scanner = new Scanner(System.in);
        int opcao;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        do {
            exibirMenu();
            opcao = scanner.nextInt();
            scanner.nextLine();
            switch (opcao) {
                case 1:
                    cadastrarTimeUI(sistema, scanner);
                    break;
                case 2:
                    cadastrarJogadorUI(sistema, scanner, formatter);
                    break;
                case 3:
                    listarTimesUI(sistema);
                    break;
                case 4:
                    listarJogadoresUI(sistema);
                    break;
                case 5:
                    registrarTransferenciaUI(sistema, scanner, formatter);
                    break;
                case 6:
                    sistema.gerarRelatorio();
                    break;
                case 7:
                    cadastrarAgenteUI(sistema, scanner);
                    break;
                case 8:
                    listarAgentesUI(sistema);
                    break;
                case 9:
                    removerJogadorUI(sistema, scanner);
                    break;
                case 10:
                    removerTimeUI(sistema, scanner);
                    break;
                case 11:
                    removerTransferenciaUI(sistema, scanner);
                    break;
                case 12:
                    removerAgenteUI(sistema, scanner);
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
        scanner.close();
    }

    private static void exibirMenu() {
        System.out.println("\n===== MENU PRINCIPAL =====");
        System.out.println("1. Cadastrar Time");
        System.out.println("2. Cadastrar Jogador (Free-Agent ou vinculado a Time)");
        System.out.println("3. Listar Times");
        System.out.println("4. Listar Jogadores");
        System.out.println("5. Registrar Transferência");
        System.out.println("6. Exibir Relatórios do BID");
        System.out.println("7. Cadastrar Agente");
        System.out.println("8. Listar Agentes");
        System.out.println("9. Remover Jogador");
        System.out.println("10. Remover Time");
        System.out.println("11. Remover Transferência");
        System.out.println("12. Remover Agente");
        System.out.println("0. Sair");
        System.out.println("----------------------------------------");
        System.out.println("Regras:");
        System.out.println("- Jogador pode ser cadastrado como Free-Agent (sem time e contrato) ou já vinculado a um time (com contrato).");
        System.out.println("- Transferência de Free-Agent não exige multa nem time de origem.");
        System.out.println("- Transferência de jogador com contrato exige pagamento de multa ao clube de origem.");
        System.out.println("- Todos os jogadores devem ter um agente associado a federação.");
        System.out.println("----------------------------------------");
        System.out.print("Escolha uma opção: ");
    }

    // Métodos auxiliares da interface
    private static void cadastrarTimeUI(Sistema sistema, Scanner scanner) {
        System.out.print("Nome do time: ");
        String nomeTime = scanner.nextLine();
        System.out.print("Saldo em caixa: ");
        double saldo = 0;
        try {
            saldo = Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Saldo inválido. Cadastro cancelado.");
            return;
        }
        try {
            sistema.cadastrarTime(nomeTime, saldo);
            System.out.println("Time cadastrado!");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void cadastrarAgenteUI(Sistema sistema, Scanner scanner) {
        System.out.print("Nome do agente: ");
        String nomeAgente = scanner.nextLine();
        try {
            sistema.cadastrarAgente(nomeAgente);
            System.out.println("Agente cadastrado!");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void listarTimesUI(Sistema sistema) {
        System.out.println("--- Times cadastrados ---");
        for (var t : sistema.listarTimes()) {
            System.out.println("- " + t.getNomeTime() + " (Saldo: " + t.getSaldoCaixa() + ")");
        }
    }

    private static void listarAgentesUI(Sistema sistema) {
        System.out.println("--- Agentes cadastrados ---");
        for (Agente ag : sistema.listarAgentes()) {
            System.out.println("- " + ag.getNomeAgente() + " (Jogadores: " + ag.getJogadoresAgenciados().size() + ")");
        }
    }

    private static void listarJogadoresUI(Sistema sistema) {
        System.out.println("--- Jogadores cadastrados ---");
        for (var j : sistema.listarJogadores()) {
            Contrato c = j.getContrato();
            String status = (j.getTimeAtual() == null) ? "Free-Agent" : j.getTimeAtual().getNomeTime();
            String contratoInfo = (c != null)
                ? (" | Contrato: Início=" + c.getInicio() + ", Fim=" + c.getFim() + ", Multa=" + c.getMultaRescisoria() + ", Cláusulas='" + c.getClausulas() + "'")
                : " | Contrato: Não informado";
            System.out.println("- " + j.getNomeJogador() + " (Posição: " + j.getPosicao() + ", Time: " + status + ")" + contratoInfo);
        }
    }

    private static Agente selecionarAgenteUI(Sistema sistema, Scanner scanner) {
        System.out.println("Selecione o agente:");
        int idxAg = 1;
        for (Agente ag : sistema.listarAgentes()) {
            System.out.println(idxAg + ". " + ag.getNomeAgente());
            idxAg++;
        }
        System.out.print("Digite o número do agente: ");
        int agenteIdx = scanner.nextInt() - 1;
        scanner.nextLine();
        if (agenteIdx < 0 || agenteIdx >= sistema.listarAgentes().size()) {
            System.out.println("Agente inválido.");
            return null;
        }
        return sistema.listarAgentes().get(agenteIdx);
    }

    private static Time selecionarTimeUI(Sistema sistema, Scanner scanner) {
        System.out.println("Selecione o time:");
        int idxTime = 1;
        for (Time t : sistema.listarTimes()) {
            System.out.println(idxTime + ". " + t.getNomeTime());
            idxTime++;
        }
        System.out.print("Digite o número do time: ");
        int timeIdx = scanner.nextInt() - 1;
        scanner.nextLine();
        if (timeIdx < 0 || timeIdx >= sistema.listarTimes().size()) {
            System.out.println("Time inválido.");
            return null;
        }
        return sistema.listarTimes().get(timeIdx);
    }

    private static Jogador selecionarJogadorUI(Sistema sistema, Scanner scanner) {
        System.out.println("Selecione o jogador para transferir:");
        int idx = 1;
        for (Jogador j : sistema.listarJogadores()) {
            String status = (j.getTimeAtual() == null) ? "Free-Agent" : j.getTimeAtual().getNomeTime();
            System.out.println(idx + ". " + j.getNomeJogador() + " (Time atual: " + status + ")");
            idx++;
        }
        System.out.print("Digite o número do jogador: ");
        int jogadorIdx = scanner.nextInt() - 1;
        scanner.nextLine();
        if (jogadorIdx < 0 || jogadorIdx >= sistema.listarJogadores().size()) {
            System.out.println("Jogador inválido.");
            return null;
        }
        return sistema.listarJogadores().get(jogadorIdx);
    }

    private static void cadastrarJogadorUI(Sistema sistema, Scanner scanner, DateTimeFormatter formatter) {
        if (sistema.listarAgentes().isEmpty()) {
            System.out.println("Cadastre um agente antes de cadastrar jogadores.");
            return;
        }
        System.out.print("Nome do jogador: ");
        String nomeJogador = scanner.nextLine();
        System.out.print("Posição: ");
        String posicao = scanner.nextLine();
        System.out.print("Valor de mercado: ");
        double valor = 0;
        try {
            valor = Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Valor de mercado inválido. Cadastro cancelado.");
            return;
        }
        Agente agente = selecionarAgenteUI(sistema, scanner);
        if (agente == null) return;
        System.out.print("Deseja associar o jogador a um time? (s/n): ");
        String associarTime = scanner.nextLine().trim().toLowerCase();
        Time time = null;
        Contrato contrato = null;
        if (associarTime.equals("s")) {
            if (sistema.listarTimes().isEmpty()) {
                System.out.println("Cadastre um time antes de associar.");
                return;
            }
            time = selecionarTimeUI(sistema, scanner);
            if (time == null) return;
            System.out.print("Data de início do contrato (yyyy-MM-dd): ");
            String inicioStr = scanner.nextLine();
            System.out.print("Data de fim do contrato (yyyy-MM-dd): ");
            String fimStr = scanner.nextLine();
            System.out.print("Multa rescisória: ");
            double multaRescisoria = 0;
            try {
                multaRescisoria = Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Multa rescisória inválida. Cadastro cancelado.");
                return;
            }
            System.out.print("Cláusulas do contrato: ");
            String clausulas = scanner.nextLine();
            LocalDate inicio = null, fim = null;
            try {
                inicio = LocalDate.parse(inicioStr, formatter);
                fim = LocalDate.parse(fimStr, formatter);
            } catch (Exception e) {
                System.out.println("Data inválida. Cadastro cancelado.");
                return;
            }
            contrato = new Contrato(null, time, inicio, fim, multaRescisoria, clausulas);
        }
        try {
            sistema.cadastrarJogador(nomeJogador, posicao, valor, time, agente, contrato);
            if (associarTime.equals("s")) {
                System.out.println("Jogador cadastrado e vinculado ao time!");
            } else {
                System.out.println("Jogador cadastrado como Free-Agent!");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void registrarTransferenciaUI(Sistema sistema, Scanner scanner, DateTimeFormatter formatter) {
        if (sistema.listarJogadores().isEmpty() || sistema.listarTimes().isEmpty()) {
            System.out.println("É necessário pelo menos 1 jogador e 1 time para registrar uma transferência.");
            return;
        }
        Jogador jogador = selecionarJogadorUI(sistema, scanner);
        if (jogador == null) return;
        Time timeDestino = selecionarTimeUI(sistema, scanner);
        if (timeDestino == null || timeDestino == jogador.getTimeAtual()) {
            System.out.println("Time inválido.");
            return;
        }
        double valorTransferencia = 0;
        double luvas = 0;
        double multa = 0;
        double comissao = 0;
        if (jogador.getContrato() != null && jogador.getTimeAtual() != null) {
            multa = jogador.getContrato().getMultaRescisoria();
            valorTransferencia = multa;
            luvas = 0;
            comissao = jogador.getAgente().calcularComissao(multa);
            System.out.println("Transferência: O valor da multa rescisória é R$ " + multa + ".");
            System.out.println("Comissão do agente (inclusa na multa): R$ " + comissao);
        } else {
            luvas = jogador.getValorMercado();
            valorTransferencia = 0;
            multa = 0;
            comissao = jogador.getAgente().calcularComissao(luvas);
            System.out.println("Transferência: Jogador Free-Agent. Luvas a serem pagas: R$ " + luvas + ".");
            System.out.println("Comissão do agente (inclusa nas luvas): R$ " + comissao);
        }
        System.out.print("Data de início do novo contrato (yyyy-MM-dd): ");
        String inicioStrT = scanner.nextLine();
        System.out.print("Data de fim do novo contrato (yyyy-MM-dd): ");
        String fimStrT = scanner.nextLine();
        System.out.print("Cláusulas do novo contrato: ");
        String clausulasT = scanner.nextLine();
        LocalDate inicioT = null, fimT = null;
        try {
            inicioT = LocalDate.parse(inicioStrT, formatter);
            fimT = LocalDate.parse(fimStrT, formatter);
        } catch (Exception e) {
            System.out.println("Data inválida. Transferência cancelada.");
            return;
        }
        Contrato novoContrato = new Contrato(jogador, timeDestino, inicioT, fimT, multa, clausulasT);
        sistema.registrarTransferencia(jogador, timeDestino, valorTransferencia, luvas, multa, comissao, novoContrato);
        System.out.println("Transferência registrada!");
    }

    private static void removerJogadorUI(Sistema sistema, Scanner scanner) {
        Jogador jogador = selecionarJogadorUI(sistema, scanner);
        if (jogador == null) return;
        if (sistema.removerJogador(jogador)) {
            System.out.println("Jogador removido com sucesso!");
        } else {
            System.out.println("Erro ao remover jogador.");
        }
    }

    private static void removerTimeUI(Sistema sistema, Scanner scanner) {
        Time time = selecionarTimeUI(sistema, scanner);
        if (time == null) return;
        if (sistema.removerTime(time)) {
            System.out.println("Time removido com sucesso!");
        } else {
            System.out.println("Erro ao remover time.");
        }
    }

    private static void removerTransferenciaUI(Sistema sistema, Scanner scanner) {
        if (sistema.listarTransferencias().isEmpty()) {
            System.out.println("Nenhuma transferência registrada.");
            return;
        }
        System.out.println("Selecione a transferência para remover:");
        int idx = 1;
        for (Transferencia t : sistema.listarTransferencias()) {
            System.out.println(idx + ". Jogador: " + t.getJogador().getNomeJogador() + " | Destino: " + t.getTimeDestino().getNomeTime() + " | Data: " + t.getData());
            idx++;
        }
        System.out.print("Digite o número da transferência: ");
        int transfIdx = scanner.nextInt() - 1;
        scanner.nextLine();
        if (transfIdx < 0 || transfIdx >= sistema.listarTransferencias().size()) {
            System.out.println("Transferência inválida.");
            return;
        }
        Transferencia transferencia = sistema.listarTransferencias().get(transfIdx);
        if (sistema.removerTransferencia(transferencia)) {
            System.out.println("Transferência removida com sucesso!");
        } else {
            System.out.println("Erro ao remover transferência.");
        }
    }

    private static void removerAgenteUI(Sistema sistema, Scanner scanner) {
        if (sistema.listarAgentes().isEmpty()) {
            System.out.println("Nenhum agente cadastrado.");
            return;
        }
        System.out.println("Selecione o agente para remover:");
        int idx = 1;
        for (Agente ag : sistema.listarAgentes()) {
            System.out.println(idx + ". " + ag.getNomeAgente() + " (Jogadores: " + ag.getJogadoresAgenciados().size() + ")");
            idx++;
        }
        System.out.print("Digite o número do agente: ");
        int agenteIdx = scanner.nextInt() - 1;
        scanner.nextLine();
        if (agenteIdx < 0 || agenteIdx >= sistema.listarAgentes().size()) {
            System.out.println("Agente inválido.");
            return;
        }
        Agente agenteParaRemover = sistema.listarAgentes().get(agenteIdx);
        Agente novoAgente = null;
        if (agenteParaRemover.temJogadores()) {
            System.out.println("O agente possui jogadores agenciados. Selecione um novo agente para transferi-los:");
            var agentesValidos = sistema.listarAgentesValidosParaTransferencia(agenteParaRemover);
            if (agentesValidos.isEmpty()) {
                System.out.println("Não há outro agente disponível para transferência.");
                return;
            }
            for (int i = 0; i < agentesValidos.size(); i++) {
                System.out.println((i + 1) + ". " + agentesValidos.get(i).getNomeAgente());
            }
            System.out.print("Digite o número do novo agente: ");
            int idxNovo = scanner.nextInt() - 1;
            scanner.nextLine();
            if (idxNovo < 0 || idxNovo >= agentesValidos.size()) {
                System.out.println("Novo agente inválido.");
                return;
            }
            novoAgente = agentesValidos.get(idxNovo);
        }
        boolean removido = sistema.removerAgenteComTransferencia(agenteParaRemover, novoAgente);
        if (removido) {
            System.out.println("Agente removido com sucesso!");
        } else {
            System.out.println("Erro ao remover agente.");
        }
    }
}
