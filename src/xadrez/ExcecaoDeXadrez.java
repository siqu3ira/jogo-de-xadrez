package xadrez;

public class ExcecaoDeXadrez extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ExcecaoDeXadrez(String msg) {
        super(msg);
    }
}
