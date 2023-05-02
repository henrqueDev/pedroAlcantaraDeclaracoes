package instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.exception.periodo;

public class PeriodoNotMatchLastException extends RuntimeException {

    public PeriodoNotMatchLastException() {
        super("Periodo de data não pode ser inserido entre os cadastrados !");
    }

}
