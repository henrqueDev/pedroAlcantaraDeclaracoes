package instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.exception;

public abstract class AbstractException extends RuntimeException {
    static final long serialVersionUID = 1L;
    public String field;

    public AbstractException(String message) {
        super(message);
    }

    public String getField() {
        return this.field;
    }

}
