package instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.exception.instituicao;

public class InstituicaoWithoutPeriodoException extends RuntimeException {
    static final long serialVersionUID = 1L;

    public InstituicaoWithoutPeriodoException() {
        super("Instituicao não possui periodoLetivo em curso!");
    }
}
