package backend.modelo;

/**
 * Investigador especializado em Robótica
 */
public class InvestigadorRobotica extends Investigador {
    private static final long serialVersionUID = 1L;
    
    private String tipoSistema; // ex: drones, veículos autónomos
    
    public InvestigadorRobotica() {
        super();
    }
    
    public InvestigadorRobotica(String nomeCompleto, String email, String password,
                               int laboratorioId, String tipoSistema) {
        super(nomeCompleto, email, password, "Robótica", laboratorioId);
        this.tipoSistema = tipoSistema;
    }
    
    public String getTipoSistema() {
        return tipoSistema;
    }
    
    public void setTipoSistema(String tipoSistema) {
        this.tipoSistema = tipoSistema;
    }
    
    @Override
    public String getDetalhesEspecificos() {
        return "Tipo de Sistema: " + tipoSistema;
    }
    
    @Override
    public String toString() {
        return super.toString() + " | " + getDetalhesEspecificos();
    }
}
