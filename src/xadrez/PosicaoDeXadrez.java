package xadrez;

import tabuleiro.Posicao;

public class PosicaoDeXadrez {

    private char coluna;
    private int linha;

    public PosicaoDeXadrez(char coluna, int linha) {
        if(coluna < 'a' || coluna > 'h' || linha < 1 || linha > 8) {
            throw new ExcecaoDeXadrez("Erro ao colocar a posição. Valores válidos são de a1 a h8");
        }
        this.coluna = coluna;
        this.linha = linha;
    }

    public char getColuna() {
        return coluna;
    }

    public int getLinha() {
        return linha;
    }

    protected Posicao toPosition() {
        return new Posicao(8 - linha, coluna - 'a');
    }

    protected static PosicaoDeXadrez fromPosition(Posicao posicao) {
        return new PosicaoDeXadrez( (char) ('a' + posicao.getColuna()), 8 - posicao.getLinha());
    }

    @Override
    public String toString() {
        return "" + coluna + linha;
    }
}
