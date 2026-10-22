package backend.modelo;

import java.io.Serializable;

/**
 * Classe abstrata base para todos os utilizadores do sistema
 */
public abstract class Utilizador implements Serializable {
    private static final long serialVersionUID = 1L;
    private static int contadorID = 1;
    
    private int id;
    private String nomeCompleto;
    private String email;
    private String password;
    
    public Utilizador() {
        this.id = contadorID++;
    }
    
    public Utilizador(String nomeCompleto, String email, String password) {
        this();
        this.nomeCompleto = nomeCompleto;
        this.email = email;
        this.password = password;
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
    
    public String getNomeCompleto() {
        return nomeCompleto;
    }
    
    public String getEmail() {
        return email;
    }
    
    public String getPassword() {
        return password;
    }
    
    // Setters
    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    @Override
    public String toString() {
        return String.format("ID: %d | Nome: %s | Email: %s", id, nomeCompleto, email);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Utilizador that = (Utilizador) obj;
        return id == that.id;
    }
    
    public abstract String getTipo();
}
