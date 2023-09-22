package tabuleiro;

public class Peca {

    protected Posicao posicao;
    private Tabuleiro tabuleiro;

    public Peca(Tabuleiro tabuleiro) {
        this.tabuleiro = tabuleiro;
        posicao = null; // Se não colocado no construtor o java já identifica por padrão que a variável é nula
    }

    protected Tabuleiro getTabuleiro() {
        return tabuleiro;
    }

}
