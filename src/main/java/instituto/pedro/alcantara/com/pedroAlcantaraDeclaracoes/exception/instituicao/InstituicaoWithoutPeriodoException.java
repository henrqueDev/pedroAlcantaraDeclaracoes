package instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.exception.instituicao;

public class InstituicaoWithoutPeriodoException extends RuntimeException {
    public InstituicaoWithoutPeriodoException() {
        super("Instituicao não possui periodoLetivo em curso!");
    }
}
