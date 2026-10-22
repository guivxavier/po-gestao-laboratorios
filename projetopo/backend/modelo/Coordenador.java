package backend.modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * Coordenador de projetos
 */
public class Coordenador extends Utilizador {
    private static final long serialVersionUID = 1L;
    
    private List<Integer> projetosIds;
    
    public Coordenador() {
        super();
        this.projetosIds = new ArrayList<>();
    }
    
    public Coordenador(String nomeCompleto, String email, String password) {
        super(nomeCompleto, email, password);
        this.projetosIds = new ArrayList<>();
    }
    
    public List<Integer> getProjetosIds() {
        return new ArrayList<>(projetosIds);
    }
    
    public void adicionarProjeto(int projetoId) {
        if (!projetosIds.contains(projetoId)) {
            projetosIds.add(projetoId);
        }
    }
    
    public void removerProjeto(int projetoId) {
        projetosIds.remove(Integer.valueOf(projetoId));
    }
    
    public int getNumProjetos() {
        return projetosIds.size();
    }
    
    @Override
    public String getTipo() {
        return "Coordenador";
    }
    
    @Override
    public String toString() {
        return super.toString() + String.format(" | Projetos Geridos: %d", getNumProjetos());
    }
}
