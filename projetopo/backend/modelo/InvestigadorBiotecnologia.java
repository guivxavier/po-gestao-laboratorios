package backend.modelo;

/**
 * Investigador especializado em Biotecnologia
 */
public class InvestigadorBiotecnologia extends Investigador {
    private static final long serialVersionUID = 1L;
    
    private String areaInvestigacao; // ex: microbiologia, genética
    
    public InvestigadorBiotecnologia() {
        super();
    }
    
    public InvestigadorBiotecnologia(String nomeCompleto, String email, String password,
                                     int laboratorioId, String areaInvestigacao) {
        super(nomeCompleto, email, password, "Biotecnologia", laboratorioId);
        this.areaInvestigacao = areaInvestigacao;
    }
    
    public String getAreaInvestigacao() {
        return areaInvestigacao;
    }
    
    public void setAreaInvestigacao(String areaInvestigacao) {
        this.areaInvestigacao = areaInvestigacao;
    }
    
    @Override
    public String getDetalhesEspecificos() {
        return "Área de Investigação: " + areaInvestigacao;
    }
    
    @Override
    public String toString() {
        return super.toString() + " | " + getDetalhesEspecificos();
    }
}
