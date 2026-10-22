package frontend;

import backend.modelo.*;
import backend.sistema.*;
import backend.sistema.excecoes.*;
import java.util.*;

// Interface em modo texto - aqui é onde o utilizador interage com o sistema
public class InterfaceTexto {
    private Scanner scanner;
    private SistemaLaboratorios sistema;
    private Utilizador utilizadorAtual;
    
    public InterfaceTexto(SistemaLaboratorios sistema) {
        this.scanner = new Scanner(System.in);
        this.sistema = sistema;
        this.utilizadorAtual = null;
    }
    
    public void iniciar() {
        mostrarCabecalho();
        
        while (true) {
            if (utilizadorAtual == null) {
                menuLogin();
            } else {
                // Mostrar menu diferente dependendo do tipo de utilizador
                if (utilizadorAtual instanceof Investigador) {
                    menuInvestigador((Investigador) utilizadorAtual);
                } else if (utilizadorAtual instanceof Coordenador) {
                    menuCoordenador((Coordenador) utilizadorAtual);
                } else if (utilizadorAtual instanceof Administrador) {
                    menuAdministrador((Administrador) utilizadorAtual);
                }
            }
        }
    }
    
    private void mostrarCabecalho() {
        System.out.println("╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║        SISTEMA DE GESTÃO DE LABORATÓRIOS CIENTÍFICOS          ║");
        System.out.println("║                                                                ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝");
        System.out.println();
    }
    
    private void menuLogin() {
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.println("║           AUTENTICAÇÃO                ║");
        System.out.println("╠═══════════════════════════════════════╣");
        System.out.println("║ 1 => Login                            ║");
        System.out.println("║ 2 => Ver estatísticas públicas        ║");
        System.out.println("║ 0 => Sair                             ║");
        System.out.println("╚═══════════════════════════════════════╝");
        
        int opcao = lerOpcao();
        
        switch (opcao) {
            case 1:
                realizarLogin();
                break;
            case 2:
                mostrarEstatisticasPublicas();
                break;
            case 0:
                guardarESair();
                break;
            default:
                System.out.println("[ERRO] Opção inválida!");
        }
    }
    
    private void realizarLogin() {
        try {
            System.out.print("\nEmail: ");
            String email = scanner.nextLine().trim();
            
            System.out.print("Password: ");
            String password = scanner.nextLine().trim();
            
            utilizadorAtual = sistema.autenticar(email, password);
            System.out.println("\n[OK] Login efetuado com sucesso!");
            System.out.println("Bem-vindo(a), " + utilizadorAtual.getNomeCompleto() + "!");
            aguardarEnter();
            
        } catch (AutenticacaoException e) {
            System.out.println("\n[ERRO] " + e.getMessage());
            aguardarEnter();
        }
    }
    
    private void mostrarEstatisticasPublicas() {
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.println("║       ESTATÍSTICAS DO SISTEMA         ║");
        System.out.println("╚═══════════════════════════════════════╝");
        
        Map<String, Integer> stats = sistema.obterEstatisticasGerais();
        stats.forEach((chave, valor) -> 
            System.out.println(String.format("  %s: %d", chave, valor))
        );
        
        aguardarEnter();
    }
    
    // ========== MENU INVESTIGADOR ==========
    
    private void menuInvestigador(Investigador investigador) {
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.println("║       MENU INVESTIGADOR               ║");
        System.out.println("╠═══════════════════════════════════════╣");
        System.out.println("║ 1 => Ver os meus dados                ║");
        System.out.println("║ 2 => Editar os meus dados             ║");
        System.out.println("║ 3 => Ver projetos em que participo    ║");
        System.out.println("║ 4 => Ver as minhas atividades         ║");
        System.out.println("║ 5 => Ver as minhas estatísticas       ║");
        System.out.println("║ 0 => Logout                           ║");
        System.out.println("╚═══════════════════════════════════════╝");
        
        int opcao = lerOpcao();
        
        switch (opcao) {
            case 1:
                verDadosInvestigador(investigador);
                break;
            case 2:
                editarDadosInvestigador(investigador);
                break;
            case 3:
                verProjetosInvestigador(investigador);
                break;
            case 4:
                verAtividadesInvestigador(investigador);
                break;
            case 5:
                verEstatisticasInvestigador(investigador);
                break;
            case 0:
                logout();
                break;
            default:
                System.out.println("❌ Opção inválida!");
        }
    }
    
    private void verDadosInvestigador(Investigador investigador) {
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.println("║         OS MEUS DADOS                 ║");
        System.out.println("╚═══════════════════════════════════════╝");
        System.out.println(investigador.toString());
        System.out.println(investigador.getDetalhesEspecificos());
        
        try {
            Laboratorio lab = sistema.obterLaboratorio(investigador.getLaboratorioId());
            System.out.println("Laboratório: " + lab.getNome());
        } catch (EntidadeException e) {
            System.out.println("Laboratório: N/A");
        }
        
        aguardarEnter();
    }
    
    private void editarDadosInvestigador(Investigador investigador) {
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.println("║       EDITAR OS MEUS DADOS            ║");
        System.out.println("╠═══════════════════════════════════════╣");
        System.out.println("║ 1. Alterar nome                       ║");
        System.out.println("║ 2. Alterar email                      ║");
        System.out.println("║ 3. Alterar password                   ║");
        System.out.println("║ 0. Voltar                             ║");
        System.out.println("╚═══════════════════════════════════════╝");
        
        int opcao = lerOpcao();
        
        try {
            switch (opcao) {
                case 1:
                    System.out.print("Novo nome: ");
                    sistema.alterarNome(investigador, scanner.nextLine().trim());
                    System.out.println("[OK] Nome atualizado!");
                    break;
                case 2:
                    System.out.print("Novo email: ");
                    sistema.alterarEmail(investigador, scanner.nextLine().trim());
                    System.out.println("[OK] Email atualizado!");
                    break;
                case 3:
                    System.out.print("Nova password: ");
                    sistema.alterarPassword(investigador, scanner.nextLine().trim());
                    System.out.println("[OK] Password atualizada!");
                    break;
                case 0:
                    return;
                default:
                    System.out.println("[ERRO] Opção inválida!");
            }
        } catch (EntidadeException e) {
            System.out.println("[ERRO] " + e.getMessage());
        }
        
        aguardarEnter();
    }
    
    private void verProjetosInvestigador(Investigador investigador) {
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.println("║       OS MEUS PROJETOS                ║");
        System.out.println("╚═══════════════════════════════════════╝");
        
        List<Projeto> projetos = sistema.listarProjetosDoInvestigador(investigador.getId());
        
        if (projetos.isEmpty()) {
            System.out.println("Não está inscrito em nenhum projeto.");
        } else {
            for (Projeto projeto : projetos) {
                System.out.println("\n" + projeto.toString());
                double totalHoras = sistema.calcularTotalHorasProjeto(projeto.getId());
                System.out.println("  Total de horas dedicadas ao projeto: " + totalHoras + "h");
            }
        }
        
        aguardarEnter();
    }
    
    private void verAtividadesInvestigador(Investigador investigador) {
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.println("║       AS MINHAS ATIVIDADES            ║");
        System.out.println("╚═══════════════════════════════════════╝");
        
        List<Atividade> atividades = sistema.listarAtividadesDoInvestigador(investigador.getId());
        
        if (atividades.isEmpty()) {
            System.out.println("Ainda não tem atividades registadas.");
        } else {
            for (Atividade atividade : atividades) {
                System.out.println(atividade.toString());
            }
        }
        
        aguardarEnter();
    }
    
    private void verEstatisticasInvestigador(Investigador investigador) {
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.println("║       AS MINHAS ESTATÍSTICAS          ║");
        System.out.println("╚═══════════════════════════════════════╝");
        
        System.out.println("Número de projetos: " + investigador.getNumProjetos());
        System.out.println("Número de atividades: " + investigador.getNumAtividades());
        
        double totalHoras = sistema.calcularTotalHorasInvestigador(investigador.getId());
        System.out.println("Total de horas dedicadas: " + totalHoras + "h");
        
        aguardarEnter();
    }
    
    // ========== MENU COORDENADOR ==========
    
    private void menuCoordenador(Coordenador coordenador) {
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.println("║       MENU COORDENADOR                ║");
        System.out.println("╠═══════════════════════════════════════╣");
        System.out.println("║ 1 => Criar projeto                    ║");
        System.out.println("║ 2 => Gerir os meus projetos           ║");
        System.out.println("║ 3 => Adicionar investigador a projeto ║");
        System.out.println("║ 4 => Remover investigador de projeto  ║");
        System.out.println("║ 5 => Registar atividade               ║");
        System.out.println("║ 6 => Ver atividades de projeto        ║");
        System.out.println("║ 7 => Alterar estado de projeto        ║");
        System.out.println("║ 0 => Logout                           ║");
        System.out.println("╚═══════════════════════════════════════╝");
        
        int opcao = lerOpcao();
        
        switch (opcao) {
            case 1:
                criarProjeto(coordenador);
                break;
            case 2:
                gerirProjetos(coordenador);
                break;
            case 3:
                adicionarInvestigadorAProjeto(coordenador);
                break;
            case 4:
                removerInvestigadorDeProjeto(coordenador);
                break;
            case 5:
                registarAtividade(coordenador);
                break;
            case 6:
                verAtividadesDeProjeto(coordenador);
                break;
            case 7:
                alterarEstadoProjeto(coordenador);
                break;
            case 0:
                logout();
                break;
            default:
                System.out.println("[ERRO] Opção inválida!");
        }
    }
    
    private void criarProjeto(Coordenador coordenador) {
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.println("║         CRIAR PROJETO                 ║");
        System.out.println("╚═══════════════════════════════════════╝");
        
        try {
            System.out.print("Título do projeto: ");
            String titulo = scanner.nextLine().trim();
            
            System.out.println("\nÁrea científica:");
            System.out.println("1. Biotecnologia");
            System.out.println("2. Robótica");
            System.out.println("3. Energia");
            System.out.print("Escolha: ");
            
            int opcaoArea = lerOpcao();
            String area;
            
            switch (opcaoArea) {
                case 1: area = "Biotecnologia"; break;
                case 2: area = "Robótica"; break;
                case 3: area = "Energia"; break;
                default:
                    System.out.println("[ERRO] Área inválida!");
                    aguardarEnter();
                    return;
            }
            
            Projeto projeto = sistema.criarProjeto(titulo, area, coordenador.getId());
            System.out.println("\n[OK] Projeto criado com sucesso!");
            System.out.println(projeto.toString());
            
        } catch (EntidadeException e) {
            System.out.println("\n[ERRO] Erro: " + e.getMessage());
        }
        
        aguardarEnter();
    }
    
    private void gerirProjetos(Coordenador coordenador) {
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.println("║       OS MEUS PROJETOS                ║");
        System.out.println("╚═══════════════════════════════════════╝");
        
        List<Projeto> projetos = sistema.listarProjetosDoCoordenador(coordenador.getId());
        
        if (projetos.isEmpty()) {
            System.out.println("Ainda não criou nenhum projeto.");
        } else {
            for (Projeto projeto : projetos) {
                System.out.println("\n" + projeto.toString());
                System.out.println("  Total de horas: " + sistema.calcularTotalHorasProjeto(projeto.getId()) + "h");
                
                // Mostrar lista de investigadores
                System.out.print("  Investigadores: ");
                List<Integer> invIds = projeto.getInvestigadoresIds();
                if (invIds.isEmpty()) {
                    System.out.println("Nenhum");
                } else {
                    for (int i = 0; i < invIds.size(); i++) {
                        try {
                            Investigador inv = sistema.obterInvestigador(invIds.get(i));
                            System.out.print(inv.getNomeCompleto());
                            if (i < invIds.size() - 1) System.out.print(", ");
                        } catch (EntidadeException e) {
                            // ignora
                        }
                    }
                    System.out.println();
                }
            }
            
            System.out.println("\n╔═══════════════════════════════════════╗");
            System.out.println("║ 1 => Remover um projeto               ║");
            System.out.println("║ 0 => Voltar                           ║");
            System.out.println("╚═══════════════════════════════════════╝");
            
            int opcao = lerOpcao();
            
            if (opcao == 1) {
                removerProjeto(coordenador);
            }
        }
        
        aguardarEnter();
    }
    
    private void removerProjeto(Coordenador coordenador) {
        try {
            System.out.print("\nID do projeto a remover: ");
            int projetoId = Integer.parseInt(scanner.nextLine().trim());
            
            sistema.obterProjetoDoCoordenador(projetoId, coordenador.getId());
            sistema.removerProjeto(projetoId);
            System.out.println("\n[OK] Projeto removido com sucesso!");
            
        } catch (NumberFormatException e) {
            System.out.println("\n[ERRO] ID inválido!");
        } catch (EntidadeException e) {
            System.out.println("\n[ERRO] " + e.getMessage());
        }
    }

    private void adicionarInvestigadorAProjeto(Coordenador coordenador) {
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.println("║   ADICIONAR INVESTIGADOR A PROJETO    ║");
        System.out.println("╚═══════════════════════════════════════╝");
        
        try {
            mostrarProjetosDoCoordenador(coordenador);
            
            System.out.print("\nID do projeto: ");
            int projetoId = Integer.parseInt(scanner.nextLine().trim());
            sistema.obterProjetoDoCoordenador(projetoId, coordenador.getId());
            
            // Mostrar investigadores
            List<Investigador> investigadores = sistema.listarInvestigadores();
            System.out.println("\nInvestigadores disponíveis:");
            for (Investigador inv : investigadores) {
                System.out.println("  [" + inv.getId() + "] " + inv.getNomeCompleto() + 
                                 " - " + inv.getAreaEspecializacao());
            }
            
            System.out.print("\nID do investigador: ");
            int investigadorId = Integer.parseInt(scanner.nextLine().trim());
            
            sistema.adicionarInvestigadorAoProjeto(projetoId, investigadorId);
            System.out.println("\n[OK] Investigador adicionado ao projeto com sucesso!");
            
        } catch (NumberFormatException e) {
            System.out.println("\n[ERRO] ID inválido!");
        } catch (EntidadeException e) {
            System.out.println("\n[ERRO] Erro: " + e.getMessage());
        }
        
        aguardarEnter();
    }
    
    private void removerInvestigadorDeProjeto(Coordenador coordenador) {
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.println("║   REMOVER INVESTIGADOR DE PROJETO     ║");
        System.out.println("╚═══════════════════════════════════════╝");
        
        try {
            mostrarProjetosDoCoordenador(coordenador);
            
            System.out.print("\nID do projeto: ");
            int projetoId = Integer.parseInt(scanner.nextLine().trim());
            
            Projeto projeto = sistema.obterProjetoDoCoordenador(projetoId, coordenador.getId());
            System.out.println("\nInvestigadores no projeto:");
            for (int invId : projeto.getInvestigadoresIds()) {
                Investigador inv = sistema.obterInvestigador(invId);
                System.out.println("  [" + inv.getId() + "] " + inv.getNomeCompleto());
            }
            
            System.out.print("\nID do investigador: ");
            int investigadorId = Integer.parseInt(scanner.nextLine().trim());
            
            sistema.removerInvestigadorDoProjeto(projetoId, investigadorId);
            System.out.println("\n[OK] Investigador removido do projeto!");
            
        } catch (NumberFormatException e) {
            System.out.println("\n[ERRO] ID inválido!");
        } catch (EntidadeException e) {
            System.out.println("\n[ERRO] Erro: " + e.getMessage());
        }
        
        aguardarEnter();
    }
    
    private void registarAtividade(Coordenador coordenador) {
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.println("║       REGISTAR ATIVIDADE              ║");
        System.out.println("╚═══════════════════════════════════════╝");
        
        try {
            List<Projeto> projetos = sistema.listarProjetosDoCoordenador(coordenador.getId());
            List<Projeto> projetosDisponiveis = new ArrayList<>();
            
            for (Projeto p : projetos) {
                if (p.aceitaAtividades()) {
                    projetosDisponiveis.add(p);
                }
            }
            
            if (projetosDisponiveis.isEmpty()) {
                System.out.println("\n[ERRO] Não há projetos disponíveis para registar atividades.");
                System.out.println("(Apenas projetos EM_CURSO ou PLANEAMENTO permitem atividades)");
                aguardarEnter();
                return;
            }
            
            System.out.println("\nProjetos disponíveis:");
            for (Projeto p : projetosDisponiveis) {
                System.out.println("  [ID: " + p.getId() + "] " + p.getTitulo() + " (" + p.getEstado() + ")");
            }
            
            System.out.print("\nID do projeto: ");
            int projetoId = Integer.parseInt(scanner.nextLine().trim());
            
            Projeto projeto = sistema.obterProjetoDoCoordenador(projetoId, coordenador.getId());

// Mostrar investigadores do projeto
            System.out.println("\nInvestigadores no projeto:");
            for (int invId : projeto.getInvestigadoresIds()) {
                Investigador inv = sistema.obterInvestigador(invId);
                System.out.println("  [" + inv.getId() + "] " + inv.getNomeCompleto());
            }
            
            System.out.print("\nID do investigador: ");
            int investigadorId = Integer.parseInt(scanner.nextLine().trim());
            
            System.out.print("Tipo de atividade (experiência/teste/simulação/publicação): ");
            String tipo = scanner.nextLine().trim();
            
            System.out.print("Duração em horas: ");
            double duracao = lerDecimal();
            
            Atividade atividade = sistema.registarAtividade(projetoId, investigadorId, tipo, duracao);
            System.out.println("\n[OK] Atividade registada com sucesso!");
            System.out.println(atividade.toString());
            
        } catch (NumberFormatException e) {
            System.out.println("\n[ERRO] Valor inválido!");
        } catch (EntidadeException e) {
            System.out.println("\n[ERRO] Erro: " + e.getMessage());
        }
        
        aguardarEnter();
    }
    
    private void verAtividadesDeProjeto(Coordenador coordenador) {
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.println("║     ATIVIDADES DE PROJETO             ║");
        System.out.println("╚═══════════════════════════════════════╝");
        
        try {
            mostrarProjetosDoCoordenador(coordenador);
            
            System.out.print("\nID do projeto: ");
            int projetoId = Integer.parseInt(scanner.nextLine().trim());
            sistema.obterProjetoDoCoordenador(projetoId, coordenador.getId());
            
            List<Atividade> atividades = sistema.listarAtividadesDoProjeto(projetoId);
            
            if (atividades.isEmpty()) {
                System.out.println("\nAinda não há atividades neste projeto.");
            } else {
                System.out.println("\nAtividades:");
                for (Atividade atividade : atividades) {
                    System.out.println(atividade.toString());
                }
            }
            
        } catch (NumberFormatException e) {
            System.out.println("\n[ERRO] ID inválido!");
        } catch (EntidadeException e) {
            System.out.println("\n[ERRO] Erro: " + e.getMessage());
        }
        
        aguardarEnter();
    }
    
    private void alterarEstadoProjeto(Coordenador coordenador) {
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.println("║     ALTERAR ESTADO DE PROJETO         ║");
        System.out.println("╚═══════════════════════════════════════╝");
        
        try {
            mostrarProjetosDoCoordenador(coordenador);
            
            System.out.print("\nID do projeto: ");
            int projetoId = Integer.parseInt(scanner.nextLine().trim());
            sistema.obterProjetoDoCoordenador(projetoId, coordenador.getId());
            
            System.out.println("\nNovo estado:");
            System.out.println("1. Em Curso");
            System.out.println("2. Suspenso");
            System.out.println("3. Concluído");
            System.out.println("4. Planeamento");
            System.out.print("Escolha: ");
            
            int opcaoEstado = lerOpcao();
            Projeto.EstadoProjeto novoEstadoEnum;
            
            switch (opcaoEstado) {
                case 1: novoEstadoEnum = Projeto.EstadoProjeto.EM_CURSO; break;
                case 2: novoEstadoEnum = Projeto.EstadoProjeto.SUSPENSO; break;
                case 3: novoEstadoEnum = Projeto.EstadoProjeto.CONCLUIDO; break;
                case 4: novoEstadoEnum = Projeto.EstadoProjeto.PLANEAMENTO; break;
                default:
                    System.out.println("[ERRO] Estado inválido!");
                    aguardarEnter();
                    return;
            }
            
            sistema.alterarEstadoProjeto(projetoId, novoEstadoEnum);
            System.out.println("\n[OK] Estado do projeto atualizado!");
            
        } catch (NumberFormatException e) {
            System.out.println("\n[ERRO] ID inválido!");
        } catch (EntidadeException e) {
            System.out.println("\n[ERRO] Erro: " + e.getMessage());
        }
        
        aguardarEnter();
    }
    
    private void mostrarProjetosDoCoordenador(Coordenador coordenador) {
        System.out.println("\nOs seus projetos:");
        for (Projeto p : sistema.listarProjetosDoCoordenador(coordenador.getId())) {
            System.out.println("  [" + p.getId() + "] " + p.getTitulo() + " (" + p.getEstado().getDescricao() + ")");
        }
    }
    
    // ========== MENU ADMINISTRADOR ==========
    
    private void menuAdministrador(Administrador admin) {
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.println("║       MENU ADMINISTRADOR              ║");
        System.out.println("╠═══════════════════════════════════════╣");
        System.out.println("║ 1 => Gerir investigadores             ║");
        System.out.println("║ 2 => Gerir coordenadores              ║");
        System.out.println("║ 3 => Gerir laboratórios               ║");
        System.out.println("║ 4 => Listar todos investigadores      ║");
        System.out.println("║ 5 => Listar todos coordenadores       ║");
        System.out.println("║ 6 => Listar todos laboratórios        ║");
        System.out.println("║ 7 => Listar todos projetos            ║");
        System.out.println("║ 8 => Ver estatísticas globais         ║");
        System.out.println("║ 9 => Ver todas as atividades          ║");
        System.out.println("║ 0 => Logout                           ║");
        System.out.println("╚═══════════════════════════════════════╝");
        
        int opcao = lerOpcao();
        
        switch (opcao) {
            case 1:
                gerirInvestigadores();
                break;
            case 2:
                gerirCoordenadores();
                break;
            case 3:
                gerirLaboratorios();
                break;
            case 4:
                listarInvestigadores();
                break;
            case 5:
                listarCoordenadores();
                break;
            case 6:
                listarLaboratorios();
                break;
            case 7:
                verTodosProjetos();
                break;
            case 8:
                verEstatisticasCompletas();
                break;
            case 9: verTodasAtividades(); 
                break;
            case 0:
                logout();
                break;
            default:
                System.out.println("[ERRO] Opção inválida!");
        }
    }
    
    private void gerirInvestigadores() {
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.println("║       GERIR INVESTIGADORES            ║");
        System.out.println("╠═══════════════════════════════════════╣");
        System.out.println("║ 1. Criar investigador                 ║");
        System.out.println("║ 2. Listar investigadores              ║");
        System.out.println("║ 3. Remover investigador               ║");
        System.out.println("║ 0. Voltar                             ║");
        System.out.println("╚═══════════════════════════════════════╝");
        
        int opcao = lerOpcao();
        
        switch (opcao) {
            case 1:
                criarInvestigador();
                break;
            case 2:
                listarInvestigadores();
                break;
            case 3:
                removerInvestigador();
                break;
            case 0:
                return;
            default:
                System.out.println("[ERRO] Opção inválida!");
        }
    }
    
    private void criarInvestigador() {
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.println("║       CRIAR INVESTIGADOR              ║");
        System.out.println("╚═══════════════════════════════════════╝");
        
        try {
            System.out.print("Nome completo: ");
            String nome = scanner.nextLine().trim();
            
            System.out.print("Email: ");
            String email = scanner.nextLine().trim();
            
            System.out.print("Password: ");
            String password = scanner.nextLine().trim();
            
            System.out.println("\nTipo de investigador:");
            System.out.println("1. Biotecnologia");
            System.out.println("2. Robótica");
            System.out.println("3. Energia");
            System.out.print("Escolha: ");
            
            int tipo = lerOpcao();
            String tipoStr;
            String perguntaDetalhe;
            
            switch (tipo) {
                case 1:
                    tipoStr = "Biotecnologia";
                    perguntaDetalhe = "Área de investigação (ex: Microbiologia, Genética): ";
                    break;
                case 2:
                    tipoStr = "Robótica";
                    perguntaDetalhe = "Tipo de sistema (ex: Drones, Veículos Autónomos): ";
                    break;
                case 3:
                    tipoStr = "Energia";
                    perguntaDetalhe = "Fonte energética (ex: Solar, Eólica): ";
                    break;
                default:
                    System.out.println("[ERRO] Tipo inválido!");
                    aguardarEnter();
                    return;
            }
            
            System.out.print(perguntaDetalhe);
            String detalhe = scanner.nextLine().trim();
            
            // Mostrar laboratórios
            List<Laboratorio> labs = sistema.listarLaboratorios();
            if (labs.isEmpty()) {
                System.out.println("\n[ERRO] Não existem laboratórios. Crie um primeiro!");
                aguardarEnter();
                return;
            }
            
            System.out.println("\nLaboratórios:");
            for (Laboratorio lab : labs) {
                System.out.println("  [" + lab.getId() + "] " + lab.getNome());
            }
            
            System.out.print("\nID do laboratório: ");
            int labId = Integer.parseInt(scanner.nextLine().trim());
            
            Investigador inv = sistema.adicionarInvestigador(tipoStr, nome, email, password, labId, detalhe);
            System.out.println("\n[OK] Investigador criado com sucesso!");
            System.out.println(inv.toString());
            
        } catch (NumberFormatException e) {
            System.out.println("\n[ERRO] ID inválido!");
        } catch (EntidadeException e) {
            System.out.println("\n[ERRO] Erro: " + e.getMessage());
        }
        
        aguardarEnter();
    }
    
    private void listarInvestigadores() {
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.println("║       LISTA DE INVESTIGADORES         ║");
        System.out.println("╚═══════════════════════════════════════╝");
        
        List<Investigador> investigadores = sistema.listarInvestigadores();
        
        if (investigadores.isEmpty()) {
            System.out.println("Não existem investigadores.");
        } else {
            for (Investigador inv : investigadores) {
                System.out.println("\n" + inv.toString());
                System.out.println(inv.getDetalhesEspecificos());
            }
        }
        
        aguardarEnter();
    }
    
    private void removerInvestigador() {
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.println("║       REMOVER INVESTIGADOR            ║");
        System.out.println("╚═══════════════════════════════════════╝");
        
        try {
            List<Investigador> investigadores = sistema.listarInvestigadores();
            System.out.println("\nInvestigadores:");
            for (Investigador inv : investigadores) {
                System.out.println("  [" + inv.getId() + "] " + inv.getNomeCompleto());
            }
            
            System.out.print("\nID do investigador: ");
            int id = Integer.parseInt(scanner.nextLine().trim());
            
            sistema.removerInvestigador(id);
            System.out.println("\n[OK] Investigador removido com sucesso!");
            
        } catch (NumberFormatException e) {
            System.out.println("\n[ERRO] ID inválido!");
        } catch (EntidadeException e) {
            System.out.println("\n[ERRO] Erro: " + e.getMessage());
        }
        
        aguardarEnter();
    }
    
    private void gerirCoordenadores() {
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.println("║       GERIR COORDENADORES             ║");
        System.out.println("╠═══════════════════════════════════════╣");
        System.out.println("║ 1. Criar coordenador                  ║");
        System.out.println("║ 2. Listar coordenadores               ║");
        System.out.println("║ 3. Remover coordenador                ║");
        System.out.println("║ 0. Voltar                             ║");
        System.out.println("╚═══════════════════════════════════════╝");
        
        int opcao = lerOpcao();
        
        switch (opcao) {
            case 1:
                criarCoordenador();
                break;
            case 2:
                listarCoordenadores();
                break;
            case 3:
                removerCoordenador();
                break;
            case 0:
                return;
            default:
                System.out.println("[ERRO] Opção inválida!");
        }
    }
    
    private void criarCoordenador() {
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.println("║       CRIAR COORDENADOR               ║");
        System.out.println("╚═══════════════════════════════════════╝");
        
        try {
            System.out.print("Nome completo: ");
            String nome = scanner.nextLine().trim();
            
            System.out.print("Email: ");
            String email = scanner.nextLine().trim();
            
            System.out.print("Password: ");
            String password = scanner.nextLine().trim();
            
            Coordenador coord = sistema.adicionarCoordenador(nome, email, password);
            System.out.println("\n[OK] Coordenador criado com sucesso!");
            System.out.println(coord.toString());
            
        } catch (EntidadeException e) {
            System.out.println("\n[ERRO] Erro: " + e.getMessage());
        }
        
        aguardarEnter();
    }
    
    private void listarCoordenadores() {
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.println("║       LISTA DE COORDENADORES          ║");
        System.out.println("╚═══════════════════════════════════════╝");
        
        List<Coordenador> coordenadores = sistema.listarCoordenadores();
        
        if (coordenadores.isEmpty()) {
            System.out.println("Não existem coordenadores.");
        } else {
            for (Coordenador coord : coordenadores) {
                System.out.println("\n" + coord.toString());
            }
        }
        
        aguardarEnter();
    }
    
    private void removerCoordenador() {
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.println("║       REMOVER COORDENADOR             ║");
        System.out.println("╚═══════════════════════════════════════╝");
        
        try {
            List<Coordenador> coordenadores = sistema.listarCoordenadores();
            System.out.println("\nCoordenadores:");
            for (Coordenador coord : coordenadores) {
                System.out.println("  [" + coord.getId() + "] " + coord.getNomeCompleto());
            }
            
            System.out.print("\nID do coordenador: ");
            int id = Integer.parseInt(scanner.nextLine().trim());
            
            sistema.removerCoordenador(id);
            System.out.println("\n[OK] Coordenador removido com sucesso!");
            
        } catch (NumberFormatException e) {
            System.out.println("\n[ERRO] ID inválido!");
        } catch (EntidadeException e) {
            System.out.println("\n[ERRO] Erro: " + e.getMessage());
        }
        
        aguardarEnter();
    }
    
    private void gerirLaboratorios() {
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.println("║       GERIR LABORATÓRIOS              ║");
        System.out.println("╠═══════════════════════════════════════╣");
        System.out.println("║ 1. Criar laboratório                  ║");
        System.out.println("║ 2. Listar laboratórios                ║");
        System.out.println("║ 3. Remover laboratório                ║");
        System.out.println("║ 0. Voltar                             ║");
        System.out.println("╚═══════════════════════════════════════╝");
        
        int opcao = lerOpcao();
        
        switch (opcao) {
            case 1:
                criarLaboratorio();
                break;
            case 2:
                listarLaboratorios();
                break;
            case 3:
                removerLaboratorio();
                break;
            case 0:
                return;
            default:
                System.out.println("[ERRO] Opção inválida!");
        }
    }
    
    private void criarLaboratorio() {
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.println("║       CRIAR LABORATÓRIO               ║");
        System.out.println("╚═══════════════════════════════════════╝");
        
        try {
            System.out.print("Nome do laboratório: ");
            String nome = scanner.nextLine().trim();
            
            System.out.print("Localização: ");
            String localizacao = scanner.nextLine().trim();
            
            Laboratorio lab = sistema.adicionarLaboratorio(nome, localizacao);
            System.out.println("\n[OK] Laboratório criado com sucesso!");
            System.out.println(lab.toString());
            
        } catch (EntidadeException e) {
            System.out.println("\n[ERRO] Erro: " + e.getMessage());
        }
        
        aguardarEnter();
    }
    
    private void listarLaboratorios() {
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.println("║       LISTA DE LABORATÓRIOS           ║");
        System.out.println("╚═══════════════════════════════════════╝");
        
        List<Laboratorio> laboratorios = sistema.listarLaboratorios();
        
        if (laboratorios.isEmpty()) {
            System.out.println("Não existem laboratórios.");
        } else {
            for (Laboratorio lab : laboratorios) {
                System.out.println("\n" + lab.toString());
            }
        }
        
        aguardarEnter();
    }
    
    private void removerLaboratorio() {
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.println("║       REMOVER LABORATÓRIO             ║");
        System.out.println("╚═══════════════════════════════════════╝");
        
        try {
            List<Laboratorio> laboratorios = sistema.listarLaboratorios();
            System.out.println("\nLaboratórios:");
            for (Laboratorio lab : laboratorios) {
                System.out.println("  [" + lab.getId() + "] " + lab.getNome());
            }
            
            System.out.print("\nID do laboratório: ");
            int id = Integer.parseInt(scanner.nextLine().trim());
            
            sistema.removerLaboratorio(id);
            System.out.println("\n[OK] Laboratório removido com sucesso!");
            
        } catch (NumberFormatException e) {
            System.out.println("\n[ERRO] ID inválido!");
        } catch (EntidadeException e) {
            System.out.println("\n[ERRO] Erro: " + e.getMessage());
        }
        
        aguardarEnter();
    }
    
    private void verTodosProjetos() {
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.println("║       TODOS OS PROJETOS               ║");
        System.out.println("╚═══════════════════════════════════════╝");
        
        List<Projeto> projetos = sistema.listarProjetos();
        
        if (projetos.isEmpty()) {
            System.out.println("Não existem projetos.");
        } else {
            for (Projeto projeto : projetos) {
                System.out.println("\n" + projeto.toString());
                System.out.println("  Total de horas: " + sistema.calcularTotalHorasProjeto(projeto.getId()) + "h");
            }
        }
        
        aguardarEnter();
    }
    
    private void verTodasAtividades() {
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.println("║       TODAS AS ATIVIDADES             ║");
        System.out.println("╚═══════════════════════════════════════╝");
        
        List<Atividade> atividades = sistema.listarAtividades();
        
        if (atividades.isEmpty()) {
            System.out.println("Não existem atividades.");
        } else {
            for (Atividade atividade : atividades) {
                System.out.println(atividade.toString());
            }
        }
        
        aguardarEnter();
    }
    
    private void verEstatisticasCompletas() {
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.println("║       ESTATÍSTICAS COMPLETAS          ║");
        System.out.println("╚═══════════════════════════════════════╝");
        
        Map<String, Integer> stats = sistema.obterEstatisticasGerais();
        stats.forEach((chave, valor) -> 
            System.out.println(String.format("  %s: %d", chave, valor))
        );
        
        aguardarEnter();
    }
    
    // ========== MÉTODOS AUXILIARES ==========
    
    private void logout() {
        utilizadorAtual = null;
        System.out.println("\n[OK] Logout efetuado com sucesso!");
        aguardarEnter();
    }
    
    private void guardarESair() {
        try {
            sistema.guardarDados(SistemaLaboratorios.FICHEIRO_DADOS);
            System.out.println("\n[OK] Dados guardados com sucesso!");
            System.out.println("Até breve!");
            System.exit(0);
        } catch (Exception e) {
            System.out.println("\n[ERRO] Erro ao guardar dados: " + e.getMessage());
            System.out.println("Tem a certeza que quer sair sem guardar? (s/n)");
            String resposta = scanner.nextLine().trim().toLowerCase();
            if (resposta.equals("s")) {
                System.exit(0);
            }
        }
    }
    
    private int lerOpcao() {
        try {
            System.out.print("\nEscolha uma opção: ");
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    // Aceita "8.5" e "8,5"
    private double lerDecimal() {
        return Double.parseDouble(scanner.nextLine().trim().replace(',', '.'));
    }
    
    private void aguardarEnter() {
        System.out.println("\nPressione ENTER para continuar...");
        scanner.nextLine();
    }
}