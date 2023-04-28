package instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.exception.instituicao;

public class InstituicaoNotFoundException extends RuntimeException {

    static final long serialVersionUID = 1L;

    public InstituicaoNotFoundException() {
        super("Instituição não encontrada!");
    }

}
