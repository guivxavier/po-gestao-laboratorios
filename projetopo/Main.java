import backend.sistema.SistemaLaboratorios;
import backend.sistema.DadosIniciais;
import frontend.InterfaceTexto;
import java.io.File;


public class Main {
    private static final String FICHEIRO_DADOS = "sistema_laboratorios.dat";
    
    public static void main(String[] args) {
        SistemaLaboratorios sistema;
        
        // Tentar carregar dados existentes
        File ficheiro = new File(FICHEIRO_DADOS);
        if (ficheiro.exists()) {
            try {
                sistema = SistemaLaboratorios.carregarDados(FICHEIRO_DADOS);
                System.out.println("✓ Dados carregados do ficheiro: " + FICHEIRO_DADOS);
            } catch (Exception e) {
                System.out.println("⚠ Erro ao carregar dados. A criar novo sistema...");
                sistema = new SistemaLaboratorios();
                DadosIniciais.popularSistema(sistema);
            }
        } else {
            // Criar novo sistema e popular com dados iniciais
            sistema = new SistemaLaboratorios();
            System.out.println("✓ Novo sistema criado");
            System.out.println("\n══════════════════════════════════════════════════════");
            System.out.println("  A popular sistema com dados iniciais de exemplo...");
            System.out.println("══════════════════════════════════════════════════════");
            DadosIniciais.popularSistema(sistema);
        }
        
        // Iniciar interface
        InterfaceTexto interfaceTexto = new InterfaceTexto(sistema);
        interfaceTexto.iniciar();
    }
}
