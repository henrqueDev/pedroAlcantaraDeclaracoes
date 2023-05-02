package instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.exception.estudante;

public class EstudanteInstituicaoNotFoundException extends RuntimeException {
    public EstudanteInstituicaoNotFoundException() {
        super("Estudante não possui instituição!");
    }

}
