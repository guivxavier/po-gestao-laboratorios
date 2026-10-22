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
            Investigador inv1 = sistema.adicionarInvestigador("biotecnologia", "Guilherme Xavier", "gui.xavier@lab.pt", 
                                         "inv123", lab1.getId(), "Microbiologia");
            Investigador inv2 = sistema.adicionarInvestigador("biotecnologia", "Tomas Silva", "tomas.silva@lab.pt", 
                                         "inv456", lab1.getId(), "Genética");
            
            // Criar investigadores de Robótica
            Investigador inv3 = sistema.adicionarInvestigador("robotica", "Joao Faria", "joao.faria@lab.pt", 
                                         "inv789", lab2.getId(), "Drones");
            Investigador inv4 = sistema.adicionarInvestigador("robotica", "Pedro Lindo", "pedro.lindo@lab.pt", 
                                         "inv012", lab2.getId(), "Veículos Autónomos");
            
            // Criar investigadores de Energia
            Investigador inv5 = sistema.adicionarInvestigador("energia", "Tiago Lima", "tiago.lima@lab.pt", 
                                         "inv345", lab3.getId(), "Solar");
            Investigador inv6 = sistema.adicionarInvestigador("energia", "Inês Pedroso", "ines.pedroso@lab.pt", 
                                         "inv678", lab3.getId(), "Eólica");
            
            // Criar projetos
            Projeto proj1 = sistema.criarProjeto("Desenvolvimento de Vacinas", "Biotecnologia", coord1.getId());
            Projeto proj2 = sistema.criarProjeto("Drones de Vigilância", "Robótica", coord1.getId());
            Projeto proj3 = sistema.criarProjeto("Painéis Solares Eficientes", "Energia", coord2.getId());
            
            // Adicionar investigadores aos projetos
            // (usar os IDs devolvidos, porque os utilizadores partilham o mesmo contador)
            sistema.adicionarInvestigadorAoProjeto(proj1.getId(), inv1.getId()); 
            sistema.adicionarInvestigadorAoProjeto(proj1.getId(), inv2.getId()); 
            
            sistema.adicionarInvestigadorAoProjeto(proj2.getId(), inv3.getId()); 
            sistema.adicionarInvestigadorAoProjeto(proj2.getId(), inv4.getId()); 
            
            sistema.adicionarInvestigadorAoProjeto(proj3.getId(), inv5.getId()); 
            sistema.adicionarInvestigadorAoProjeto(proj3.getId(), inv6.getId()); 
            
            // Registar algumas atividades
            sistema.registarAtividade(proj1.getId(), inv1.getId(), "Experiência", 8.5);
            sistema.registarAtividade(proj1.getId(), inv2.getId(), "Teste", 6.0);
            sistema.registarAtividade(proj2.getId(), inv3.getId(), "Simulação", 12.0);
            sistema.registarAtividade(proj3.getId(), inv5.getId(), "Publicação", 4.5);
            
            System.out.println("Sucesso! Sistema populado com dados iniciais");
            System.out.println("  - 3 Laboratórios criados");
            System.out.println("  - 6 Investigadores criados");
            System.out.println("  - 2 Coordenadores criados");
            System.out.println("  - 1 Administrador criado");
            System.out.println("  - 3 Projetos criados");
            System.out.println("  - 4 Atividades registadas");
            System.out.println("\nCredenciais de teste:");
            System.out.println("  Admin: admin@lab.pt / admin123");
            System.out.println("  Coordenador: manuel.vieira@lab.pt / coord123");
            System.out.println("  Investigador: gui.xavier@lab.pt / inv123");
            
        } catch (EntidadeException e) {
            System.err.println("Erro ao popular sistema: " + e.getMessage());
        }
    }
}
