package instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.exception.periodo;

public class PeriodoNotMatchLastException extends RuntimeException {
    static final long serialVersionUID = 1L;

    public PeriodoNotMatchLastException() {
        super("Periodo não pode ser inserido no meio do atual!");
    }

}
