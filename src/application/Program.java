package application;

import model.entities.*;
import model.enums.Especialidade;
import model.enums.TipoPagamento;
import model.exceptions.RegraNegocioException;
import model.services.AgendamentoService;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class Program {
    public static void main(String[] args) {

        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);
        List<Paciente> pacientesList = new ArrayList<>();
        List<Profissional> profissionaisList = new ArrayList<>();
        List<Agendamento> agendamentosList = new ArrayList<>();

        int opcao = 0;

        do {
            try {
                menu();
                opcao = sc.nextInt();
                sc.nextLine();

                switch (opcao) {
                    case 1 -> cadastroDePacientes(sc, pacientesList);
                    case 2 -> cadastroDeMedicos(sc, profissionaisList);
                    case 3 -> agendamentoDeConsultas(sc, agendamentosList, pacientesList, profissionaisList);
                    case 4 -> salvarConsultaEmArquivo(agendamentosList);
                    case 5 -> listarConsultasSalvas(agendamentosList);
                    case 0 -> System.out.println("Saindo...");
                }

            } catch (InputMismatchException e) {
                sc.nextLine();
                System.out.println("Por gentileza, inserir número no formato inteiro.");
            }

        } while (opcao != 0);

        sc.close();
    }

    private static void menu() {
        System.out.println();
        System.out.println("1. Cadastro de Paciente");
        System.out.println("2. Cadastro de Médico");
        System.out.println("3. Agendamento de Consulta");
        System.out.println("4. Salvar Consulta em Arquivo");
        System.out.println("5. Listar Consultas Salvas");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");
    }

    private static void cadastroDePacientes(Scanner scanner, List<Paciente> pacienteList) {
        System.out.println();
        try {

            // Função para cadastrar dados.
            Paciente paciente = dadosCadastraisPacientes(scanner, pacienteList);
            pacienteList.add(paciente);

            System.out.println();
            System.out.println("Paciente cadastrado com sucesso.");
        } catch (DateTimeParseException e) {
            System.out.println();
            System.out.println("Por gentileza, inserir data no formato (dd/MM/yyyy).");
        } catch (RegraNegocioException e) {
            System.out.println();
            System.out.print("Erro: " + e.getMessage());
        }
    }

    private static void cadastroDeMedicos(Scanner scanner, List<Profissional> profissionalList) {
        System.out.println();
        try {
            // Função para cadastrar dados
            Profissional profissional = dadosCadastraisMedicos(scanner, profissionalList);
            profissionalList.add(profissional);

            System.out.println();
            System.out.println("Médico cadastrado com sucesso.");
        } catch (InputMismatchException e) {
            System.out.println();
            System.out.println("Por gentileza, insira número no formato inteiro.");
        }
    }

    private static void agendamentoDeConsultas(Scanner scanner, List<Agendamento> agendamentoList, List<Paciente> pacienteList, List<Profissional> profissionalList) {
        System.out.println();
        try {

            System.out.print("CPF do paciente: ");
            String cpfPaciente = scanner.nextLine();

            Paciente paciente = buscaPaciente(pacienteList, cpfPaciente);
            if (paciente == null) {
                System.out.println();
                System.out.println("Erro: Paciente não encontrado.");
                return;
            }

            System.out.print("CRM do médico: ");
            String crmMedico = scanner.nextLine();

            Profissional profissional = buscaMedico(profissionalList, crmMedico);
            if (profissional == null) {
                System.out.println();
                System.out.println("Erro: Médico não encontrado.");
                return;
            }

            LocalDateTime dataDaConsulta;
            while (true) {
                System.out.print("Data da consulta: ");
                dataDaConsulta = LocalDateTime.parse(scanner.nextLine().trim(), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

                boolean temConflito = horarioConflita(agendamentoList, dataDaConsulta, crmMedico);
                if (temConflito) {
                    System.out.println("Horário de consulta já está agendada.");
                    continue;
                }

                break;
            }


            System.out.print("Digite o número do tipo de pagamento: (1- Particular | 2- Convenio | 3- SUS): ");
            int opcaoPagamento = scanner.nextInt();
            scanner.nextLine();

            TipoPagamento tipoPagamento = switch (opcaoPagamento) {
                case 1 -> TipoPagamento.PARTICULAR;
                case 2 -> TipoPagamento.CONVENIO;
                case 3 -> TipoPagamento.SUS;
                default -> throw new IllegalStateException("Opção inválida de pagamento.");
            };


            AgendamentoService service = new AgendamentoService();
            Agendamento agendamento = service.agendarConsultar(paciente, profissional, dataDaConsulta, tipoPagamento);
            agendamentoList.add(agendamento);

            System.out.println();
            System.out.println("Consulta agendada com sucesso!");
            System.out.println(agendamento.toString());

        } catch (DateTimeParseException e) {
            System.out.println();
            System.out.println("Erro: Data da consulta inválida.");
        } catch (RegraNegocioException e) {
            System.out.println();
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void salvarConsultaEmArquivo(List<Agendamento> list) {
        System.out.println();
        if (list.isEmpty()) {
            System.out.println("Não tem agendamentos cadastrados no sistema.");
            return;
        }

        File criandoPasta = new File(System.getProperty("user.dir") + ("/dados"));
        criandoPasta.mkdir();

        File criandoArquivo = new File(criandoPasta.getPath() + "/consultas-" + LocalDate.now() + ".csv");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(criandoArquivo))) {

            bw.write(Agendamento.tituloArquivoCsv());
            bw.newLine();

            for (Agendamento agendamento : list) {
                bw.write(agendamento.formatoCsv());
                bw.newLine();
            }

            System.out.println("Agendamentos foram exportados!");

        } catch (IOException e) {
            System.out.println("Erro ao acessar o arquivo: " + e.getMessage());
        }
    }

    private static void listarConsultasSalvas(List<Agendamento> agendamentoList) {
        System.out.println();
        if (agendamentoList.isEmpty()) {
            System.out.println("Não tem consultas salvas!");
            return;
        }

        System.out.println("Consultas salvas: ");
        for (Agendamento agendamento : agendamentoList) {
            System.out.println(agendamento.toString());
            System.out.println();
        }
    }

    private static Profissional getProfissional(int opcaoEspecialidade, String nomeMedico, String crmMedico) {
        Especialidade especialidade = switch (opcaoEspecialidade) {
            case 1 -> Especialidade.CLINICO_GERAL;
            case 2 -> Especialidade.PEDIATRA;
            case 3 -> Especialidade.CARDIOLOGISTA;
            case 4 -> Especialidade.ORTOPEDISTA;
            default -> throw new IllegalStateException("Não temos essa opção de especialidade.");
        };

        return switch (especialidade) {
            case CLINICO_GERAL -> new MedicoClinicoGeral(nomeMedico, crmMedico);
            case PEDIATRA -> new MedicoPediatra(nomeMedico, crmMedico);
            case CARDIOLOGISTA -> new MedicoCardiologista(nomeMedico, crmMedico);
            case ORTOPEDISTA -> new MedicoOrtopedista(nomeMedico, crmMedico);
        };
    }

    // DADOS CADASTRAIS PACIENTES
    private static Paciente dadosCadastraisPacientes(Scanner scanner, List<Paciente> pacienteList) {
        System.out.print("Nome: ");
        String nomePaciente = scanner.nextLine();

        String cpfPaciente;
        while (true) {
            System.out.print("CPF (000.000.000-00): ");
            cpfPaciente = scanner.nextLine().trim();

            if (!cpfPaciente.matches("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}")) {
                System.out.println("Erro: CPF no formato inválido");
                continue;
            }

            if (buscaPaciente(pacienteList, cpfPaciente) != null) {
                System.out.println("Erro: CPF já cadastrado.");
                continue;
            }
            break;
        }

        System.out.print("Data de nascimento: ");
        String dataStr = scanner.nextLine().trim();
        LocalDate dataDeNascimentoPaciente = LocalDate.parse(dataStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        return new Paciente(nomePaciente, dataDeNascimentoPaciente, cpfPaciente);
    }

    // DADOS CADASTRAIS MEDICOS
    private static Profissional dadosCadastraisMedicos(Scanner scanner, List<Profissional> profissionalList) {
        System.out.print("Nome: ");
        String nomeMedico = scanner.nextLine();
        String crmMedico;
        while (true) {
            System.out.print("CRM (000000): ");
            crmMedico = scanner.nextLine().trim();

            if (!crmMedico.matches("\\d{6}")) {
                System.out.println("Erro: CRM no formato inválido");
                continue;
            }

            if (buscaMedico(profissionalList, crmMedico) != null) {
                System.out.println("Erro: CRM já cadastrado.");
                continue;
            }

            break;
        }

        System.out.print("Digite o número para a especialidade (1- Clínico Geral | 2- Pediatra | 3- Cardiologista | 4- Ortopedista): ");
        int opcaoEspecialidade = scanner.nextInt();
        scanner.nextLine();

        return getProfissional(opcaoEspecialidade, nomeMedico, crmMedico);
    }

    // FUNÇÃO PARA BUSCAR MEDICO
    private static Profissional buscaMedico(List<Profissional> profissionalList, String crm) {
        return profissionalList.stream().filter(x -> x.getCrm().equals(crm)).findFirst().orElse(null);
    }

    // FUNÇÃO PARA BUSCAR PACIENTE
    private static Paciente buscaPaciente(List<Paciente> pacienteList, String cpf) {
        return pacienteList.stream().filter(x -> x.getCpf().equals(cpf)).findFirst().orElse(null);
    }

    // FUNÇÃO PARA VALIDAR SE O HORARIO JÁ ESTÁ AGENDADO.
    private static boolean horarioConflita(List<Agendamento> agendamentoList, LocalDateTime dataDaConsultaAgora, String crm) {

        List<Agendamento> agendamentoDoMedico = filtrarAgendamentoDeUmMedico(agendamentoList, crm);

        Agendamento buscarHorarioDisponivel = null;

        if (!agendamentoDoMedico.isEmpty()) {
            buscarHorarioDisponivel = agendamentoDoMedico.stream().filter(x ->
                    dataDaConsultaAgora.isBefore(x.getDataConsulta().plusMinutes(60))
                            && x.getDataConsulta().isBefore(dataDaConsultaAgora.plusMinutes(60))).findFirst().orElse(null);
        }

        return buscarHorarioDisponivel != null;
    }

    // FUNÇÃO PARA FILTRAR ATENDIMENTO DE UM MEDICO ESPECÍFICO.
    private static List<Agendamento> filtrarAgendamentoDeUmMedico (List<Agendamento> agendamentoList, String crm) {
        return agendamentoList.stream().filter(x -> x.getProfissional().getCrm().equalsIgnoreCase(crm)).toList();
    }

}