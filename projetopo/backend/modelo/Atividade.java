package backend.modelo;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Atividade realizada por um investigador num projeto
 */
public class Atividade implements Serializable {
    private static final long serialVersionUID = 1L;
    private static int contadorID = 1;
    
    private int id;
    private int projetoId;
    private int investigadorId;
    private String tipoAtividade; // experiência, teste, simulação, publicação
    private LocalDate data;
    private double duracaoHoras;
    
    public Atividade() {
        this.id = contadorID++;
        this.data = LocalDate.now();
    }
    
    public Atividade(int projetoId, int investigadorId, String tipoAtividade, double duracaoHoras) {
        this();
        this.projetoId = projetoId;
        this.investigadorId = investigadorId;
        this.tipoAtividade = tipoAtividade;
        this.duracaoHoras = duracaoHoras;
    }
    
    public Atividade(int projetoId, int investigadorId, String tipoAtividade, 
                    LocalDate data, double duracaoHoras) {
        this.id = contadorID++;
        this.projetoId = projetoId;
        this.investigadorId = investigadorId;
        this.tipoAtividade = tipoAtividade;
        this.data = data;
        this.duracaoHoras = duracaoHoras;
    }
    
    // Getters
    public int getId() {
        return id;
    }
    
    public int getProjetoId() {
        return projetoId;
    }
    
    public int getInvestigadorId() {
        return investigadorId;
    }
    
    public String getTipoAtividade() {
        return tipoAtividade;
    }
    
    public LocalDate getData() {
        return data;
    }
    
    public double getDuracaoHoras() {
        return duracaoHoras;
    }
    
    // Setters
    public void setProjetoId(int projetoId) {
        this.projetoId = projetoId;
    }
    
    public void setInvestigadorId(int investigadorId) {
        this.investigadorId = investigadorId;
    }
    
    public void setTipoAtividade(String tipoAtividade) {
        this.tipoAtividade = tipoAtividade;
    }
    
    public void setData(LocalDate data) {
        this.data = data;
    }
    
    public void setDuracaoHoras(double duracaoHoras) {
        this.duracaoHoras = duracaoHoras;
    }
    
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return String.format("ID: %d | Projeto: %d | Investigador: %d | Tipo: %s | Data: %s | Duração: %.1fh",
            id, projetoId, investigadorId, tipoAtividade, data.format(formatter), duracaoHoras);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Atividade that = (Atividade) obj;
        return id == that.id;
    }
}
