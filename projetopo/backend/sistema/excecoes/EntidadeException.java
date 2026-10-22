package backend.sistema.excecoes;

/**
 * Exceção lançada quando há problemas com entidades do sistema
 */
public class EntidadeException extends Exception {
    private static final long serialVersionUID = 1L;
    
    public EntidadeException(String mensagem) {
        super(mensagem);
    }
}
