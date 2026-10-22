package backend.modelo;

/**
 * Investigador especializado em Energia
 */
public class InvestigadorEnergia extends Investigador {
    private static final long serialVersionUID = 1L;
    
    private String fonteEnergetica; // ex: solar, eólica
    
    public InvestigadorEnergia() {
        super();
    }
    
    public InvestigadorEnergia(String nomeCompleto, String email, String password,
                              int laboratorioId, String fonteEnergetica) {
        super(nomeCompleto, email, password, "Energia", laboratorioId);
        this.fonteEnergetica = fonteEnergetica;
    }
    
    public String getFonteEnergetica() {
        return fonteEnergetica;
    }
    
    public void setFonteEnergetica(String fonteEnergetica) {
        this.fonteEnergetica = fonteEnergetica;
    }
    
    @Override
    public String getDetalhesEspecificos() {
        return "Fonte Energética: " + fonteEnergetica;
    }
    
    @Override
    public String toString() {
        return super.toString() + " | " + getDetalhesEspecificos();
    }
}
