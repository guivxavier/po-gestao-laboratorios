package backend.modelo;

/**
 * Administrador do sistema
 */
public class Administrador extends Utilizador {
    private static final long serialVersionUID = 1L;
    
    public Administrador() {
        super();
    }
    
    public Administrador(String nomeCompleto, String email, String password) {
        super(nomeCompleto, email, password);
    }
    
    @Override
    public String getTipo() {
        return "Administrador";
    }
    
    @Override
    public String toString() {
        return super.toString() + " | Tipo: Administrador";
    }
}
