package xadrez;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;

public abstract class PecaDeXadrez extends Peca {

    private Cor cor;

    public PecaDeXadrez(Tabuleiro tabuleiro, Cor cor) {
        super(tabuleiro);
        this.cor = cor;
    }

    public Cor getCor() {
        return cor;
    }

    protected boolean temUmaPecaInimiga(Posicao position) {
        PecaDeXadrez p = (PecaDeXadrez)getTabuleiro().peca(position);
        return p != null && p.getCor() != cor;
    }

    public PosicaoDeXadrez getPosicaoDeXadrez() {
        return  PosicaoDeXadrez.fromPosition(posicao);
    }
}
