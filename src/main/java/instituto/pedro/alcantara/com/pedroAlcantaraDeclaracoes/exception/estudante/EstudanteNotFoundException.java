package instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.exception.estudante;

public class EstudanteNotFoundException extends RuntimeException {
    public EstudanteNotFoundException() {
        super("Estudante não encontrado!");
    }
}
