package backend.sistema.excecoes;

/**
 * Exceção lançada quando há problemas de autenticação
 */
public class AutenticacaoException extends Exception {
    private static final long serialVersionUID = 1L;
    
    public AutenticacaoException(String mensagem) {
        super(mensagem);
    }
}
