package instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.exception.instituicao;

public class InstituicaoNotFoundException extends RuntimeException {
    public InstituicaoNotFoundException() {
        super("Instituição não encontrada!");
    }

}
