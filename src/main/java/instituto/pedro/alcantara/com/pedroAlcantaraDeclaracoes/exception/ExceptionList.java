package instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.exception;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ExceptionList extends Exception {

    private List<AbstractException> exceptions = new ArrayList<AbstractException>();

    public void add(AbstractException e) {
        exceptions.add(e);
    }

    public Collection<AbstractException> getExceptions() {
        return exceptions;
    }
}