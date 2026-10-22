package backend.modelo;

import java.util.ArrayList;
import java.util.List;

// Classe abstrata para investigadores
// Serve de base para os 3 tipos especializados
public abstract class Investigador extends Utilizador {
    private static final long serialVersionUID = 1L;
    
    private String areaEspecializacao;
    private int laboratorioId;
    private List<Integer> projetosIds;
    private List<Integer> atividadesIds;
    
    public Investigador() {
        super();
        this.projetosIds = new ArrayList<>();
        this.atividadesIds = new ArrayList<>();
    }
    
    public Investigador(String nomeCompleto, String email, String password, 
                       String areaEspecializacao, int laboratorioId) {
        super(nomeCompleto, email, password);
        this.areaEspecializacao = areaEspecializacao;
        this.laboratorioId = laboratorioId;
        this.projetosIds = new ArrayList<>();
        this.atividadesIds = new ArrayList<>();
    }
    
    // Getters e Setters
    public String getAreaEspecializacao() {
        return areaEspecializacao;
    }
    
    public int getLaboratorioId() {
        return laboratorioId;
    }
    
    public List<Integer> getProjetosIds() {
        return new ArrayList<>(projetosIds);
    }
    
    public List<Integer> getAtividadesIds() {
        return new ArrayList<>(atividadesIds);
    }
    
    public void setAreaEspecializacao(String areaEspecializacao) {
        this.areaEspecializacao = areaEspecializacao;
    }
    
    public void setLaboratorioId(int laboratorioId) {
        this.laboratorioId = laboratorioId;
    }
    
    // Adicionar projeto ao investigador
    public void adicionarProjeto(int projetoId) {
        if (!projetosIds.contains(projetoId)) {
            projetosIds.add(projetoId);
        }
    }
    
    public void removerProjeto(int projetoId) {
        projetosIds.remove(Integer.valueOf(projetoId));
    }
    
    public void adicionarAtividade(int atividadeId) {
        if (!atividadesIds.contains(atividadeId)) {
            atividadesIds.add(atividadeId);
        }
    }
    
    // Contar projetos e atividades
    public int getNumProjetos() {
        return projetosIds.size();
    }
    
    public int getNumAtividades() {
        return atividadesIds.size();
    }
    
    @Override
    public String getTipo() {
        return "Investigador";
    }
    
    @Override
    public String toString() {
        return super.toString() + String.format(" | Área: %s | Projetos: %d | Atividades: %d", 
            areaEspecializacao, getNumProjetos(), getNumAtividades());
    }
    
    // Cada tipo de investigador implementa isto de forma diferente
    public abstract String getDetalhesEspecificos();
}
