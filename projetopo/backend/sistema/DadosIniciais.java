package backend.sistema;

import backend.modelo.*;
import backend.sistema.excecoes.*;

/**
 * Classe para popular o sistema com dados iniciais de exemplo
 */
public class DadosIniciais {
    
    public static void popularSistema(SistemaLaboratorios sistema) {
        try {
            // Criar administrador padrão
            sistema.adicionarAdministrador("Admin Sistema", "admin@lab.pt", "admin123");
            
            // Criar laboratórios
            Laboratorio lab1 = sistema.adicionarLaboratorio("Lab. Biotecnologia Avançada", "Porto");
            Laboratorio lab2 = sistema.adicionarLaboratorio("Lab. Robótica e IA", "Braga");
            Laboratorio lab3 = sistema.adicionarLaboratorio("Lab. Energias Renováveis", "Lisboa");
            
            // Criar coordenadores
            Coordenador coord1 = sistema.adicionarCoordenador("Dr. Manuel Vieira", "manuel.vieira@lab.pt", "coord123");
            Coordenador coord2 = sistema.adicionarCoordenador("Dra. Ana Pereira", "ana.pereira@lab.pt", "coord456");
            
            // Criar investigadores de Biotecnologia
            sistema.adicionarInvestigador("biotecnologia", "Guilherme Xavier", "gui.xavier@lab.pt", 
                                         "inv123", lab1.getId(), "Microbiologia");
            sistema.adicionarInvestigador("biotecnologia", "Tomas Silva", "tomas.silva@lab.pt", 
                                         "inv456", lab1.getId(), "Genética");
            
            // Criar investigadores de Robótica
            sistema.adicionarInvestigador("robotica", "Joao Faria", "joao.faria@lab.pt", 
                                         "inv789", lab2.getId(), "Drones");
            sistema.adicionarInvestigador("robotica", "Pedro Lindo", "pedro.lindo@lab.pt", 
                                         "inv012", lab2.getId(), "Veículos Autónomos");
            
            // Criar investigadores de Energia
            sistema.adicionarInvestigador("energia", "Tiago Lima", "tiago.lima@lab.pt", 
                                         "inv345", lab3.getId(), "Solar");
            sistema.adicionarInvestigador("energia", "Inês Pedroso", "ines.pedroso@lab.pt", 
                                         "inv678", lab3.getId(), "Eólica");
            
            // Criar projetos
            Projeto proj1 = sistema.criarProjeto("Desenvolvimento de Vacinas", "Biotecnologia", coord1.getId());
            Projeto proj2 = sistema.criarProjeto("Drones de Vigilância", "Robótica", coord1.getId());
            Projeto proj3 = sistema.criarProjeto("Painéis Solares Eficientes", "Energia", coord2.getId());
            
            // Adicionar investigadores aos projetos
            // Projeto 1
            sistema.adicionarInvestigadorAoProjeto(proj1.getId(), 1); 
            sistema.adicionarInvestigadorAoProjeto(proj1.getId(), 2); 
            
            // Projeto 2
            sistema.adicionarInvestigadorAoProjeto(proj2.getId(), 3); 
            sistema.adicionarInvestigadorAoProjeto(proj2.getId(), 4); 
            
            // Projeto 3
            sistema.adicionarInvestigadorAoProjeto(proj3.getId(), 5); 
            sistema.adicionarInvestigadorAoProjeto(proj3.getId(), 6); 
            
            // Registar algumas atividades
            sistema.registarAtividade(proj1.getId(), 1, "Experiência", 8.5);
            sistema.registarAtividade(proj1.getId(), 2, "Teste", 6.0);
            sistema.registarAtividade(proj2.getId(), 3, "Simulação", 12.0);
            sistema.registarAtividade(proj3.getId(), 5, "Publicação", 4.5);
            
            System.out.println("Sucesso! Sistema populado com dados iniciais");
            System.out.println("  - 3 Laboratórios criados");
            System.out.println("  - 6 Investigadores criados");
            System.out.println("  - 2 Coordenadores criados");
            System.out.println("  - 1 Administrador criado");
            System.out.println("  - 3 Projetos criados");
            System.out.println("  - 4 Atividades registadas");
            System.out.println("\nCredenciais de teste:");
            System.out.println("  Admin: admin@lab.pt / admin123");
            System.out.println("  Coordenador: joao.silva@lab.pt / coord123");
            System.out.println("  Investigador: ana.costa@lab.pt / inv123");
            
        } catch (EntidadeException e) {
            System.err.println("Erro ao popular sistema: " + e.getMessage());
        }
    }
}
