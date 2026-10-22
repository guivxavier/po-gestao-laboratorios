package backend.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Projeto científico
 */
public class Projeto implements Serializable {
    private static final long serialVersionUID = 1L;
    private static int contadorID = 1;
    
    private int id;
    private String titulo;
    private String areaCientifica; // Biotecnologia, Robótica, Energia
    private List<Integer> laboratoriosIds;
    private List<Integer> investigadoresIds;
    private int coordenadorId;
    private EstadoProjeto estado;
    
    public enum EstadoProjeto {
        EM_CURSO("Em Curso"),
        CONCLUIDO("Concluído"),
        SUSPENSO("Suspenso"),
        PLANEAMENTO("Planeamento");
        
        private final String descricao;
        
        EstadoProjeto(String descricao) {
            this.descricao = descricao;
        }
        
        public String getDescricao() {
            return descricao;
        }
    }
    
    public Projeto() {
        this.id = contadorID++;
        this.laboratoriosIds = new ArrayList<>();
        this.investigadoresIds = new ArrayList<>();
        this.estado = EstadoProjeto.EM_CURSO;
    }
    
    public Projeto(String titulo, String areaCientifica, int coordenadorId) {
        this();
        this.titulo = titulo;
        this.areaCientifica = areaCientifica;
        this.coordenadorId = coordenadorId;
    }
    
    // Getters
    public int getId() {
        return id;
    }
    
    public String getTitulo() {
        return titulo;
    }
    
    public String getAreaCientifica() {
        return areaCientifica;
    }
    
    public List<Integer> getLaboratoriosIds() {
        return new ArrayList<>(laboratoriosIds);
    }
    
    public List<Integer> getInvestigadoresIds() {
        return new ArrayList<>(investigadoresIds);
    }
    
    public int getCoordenadorId() {
        return coordenadorId;
    }
    
    public EstadoProjeto getEstado() {
        return estado;
    }
    
    // Setters
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    public void setAreaCientifica(String areaCientifica) {
        this.areaCientifica = areaCientifica;
    }
    
    public void setCoordenadorId(int coordenadorId) {
        this.coordenadorId = coordenadorId;
    }
    
    public void setEstado(EstadoProjeto estado) {
        this.estado = estado;
    }
    
    // Métodos de gestão
    public void adicionarLaboratorio(int laboratorioId) {
        if (!laboratoriosIds.contains(laboratorioId)) {
            laboratoriosIds.add(laboratorioId);
        }
    }
    
    public void removerLaboratorio(int laboratorioId) {
        laboratoriosIds.remove(Integer.valueOf(laboratorioId));
    }
    
    public void adicionarInvestigador(int investigadorId) {
        if (!investigadoresIds.contains(investigadorId)) {
            investigadoresIds.add(investigadorId);
        }
    }
    
    public void removerInvestigador(int investigadorId) {
        investigadoresIds.remove(Integer.valueOf(investigadorId));
    }
    
    @Override
    public String toString() {
        return String.format("ID: %d | Título: %s | Área: %s | Estado: %s | Investigadores: %d | Laboratórios: %d",
            id, titulo, areaCientifica, estado.getDescricao(), investigadoresIds.size(), laboratoriosIds.size());
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Projeto that = (Projeto) obj;
        return id == that.id;
    }
}
