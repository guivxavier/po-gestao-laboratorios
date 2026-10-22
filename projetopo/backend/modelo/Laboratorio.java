package backend.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Laboratório que acolhe investigadores
 */
public class Laboratorio implements Serializable {
    private static final long serialVersionUID = 1L;
    private static int contadorID = 1;
    
    private int id;
    private String nome;
    private String localizacao;
    private List<Integer> investigadoresIds;
    private List<Integer> projetosIds;
    
    public Laboratorio() {
        this.id = contadorID++;
        this.investigadoresIds = new ArrayList<>();
        this.projetosIds = new ArrayList<>();
    }
    
    public Laboratorio(String nome, String localizacao) {
        this();
        this.nome = nome;
        this.localizacao = localizacao;
    }
    
    // O contador é static, por isso não é guardado no ficheiro.
    // Depois de carregar os dados é preciso acertá-lo para não repetir IDs.
    public static void atualizarContador(int ultimoId) {
        if (ultimoId >= contadorID) {
            contadorID = ultimoId + 1;
        }
    }
    
    // Getters
    public int getId() {
        return id;
    }
    
    public String getNome() {
        return nome;
    }
    
    public String getLocalizacao() {
        return localizacao;
    }
    
    public List<Integer> getInvestigadoresIds() {
        return new ArrayList<>(investigadoresIds);
    }
    
    public List<Integer> getProjetosIds() {
        return new ArrayList<>(projetosIds);
    }
    
    // Setters
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }
    
    // Métodos de gestão
    public void adicionarInvestigador(int investigadorId) {
        if (!investigadoresIds.contains(investigadorId)) {
            investigadoresIds.add(investigadorId);
        }
    }
    
    public void removerInvestigador(int investigadorId) {
        investigadoresIds.remove(Integer.valueOf(investigadorId));
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
    public String toString() {
        return String.format("ID: %d | Nome: %s | Localização: %s | Investigadores: %d | Projetos: %d",
            id, nome, localizacao, investigadoresIds.size(), getNumProjetos());
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Laboratorio that = (Laboratorio) obj;
        return id == that.id;
    }
    
    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}
