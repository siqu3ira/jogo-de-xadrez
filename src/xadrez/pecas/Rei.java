package xadrez.pecas;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaDeXadrez;

public class Rei extends PecaDeXadrez {

    public Rei(Tabuleiro tabuleiro, Cor cor) {
        super(tabuleiro, cor);
    }

    @Override
    public String toString() {
        return "R";
    }

    private boolean podeMover(Posicao posicao) {
        PecaDeXadrez peca =(PecaDeXadrez) getTabuleiro().peca(posicao);

        return peca == null || peca.getCor() != getCor();
    }

    @Override
    public boolean[][] movimentosPossiveis() {
        var mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
        Posicao p = new Posicao(0, 0);

        // Acima
        p.setValues(posicao.getLinha() - 1, posicao.getColuna());
        if (getTabuleiro().posicaoExiste(p) && podeMover(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // Abaixo
        p.setValues(posicao.getLinha() + 1, posicao.getColuna());
        if (getTabuleiro().posicaoExiste(p) && podeMover(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // Esquerda
        p.setValues(posicao.getLinha(), posicao.getColuna() - 1);
        if (getTabuleiro().posicaoExiste(p) && podeMover(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // Direita
        p.setValues(posicao.getLinha(), posicao.getColuna() + 1);
        if (getTabuleiro().posicaoExiste(p) && podeMover(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // NO
        p.setValues(posicao.getLinha() - 1, posicao.getColuna() - 1);
        if (getTabuleiro().posicaoExiste(p) && podeMover(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // NL
        p.setValues(posicao.getLinha() - 1, posicao.getColuna() + 1);
        if (getTabuleiro().posicaoExiste(p) && podeMover(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // SO
        p.setValues(posicao.getLinha() + 1, posicao.getColuna() - 1);
        if (getTabuleiro().posicaoExiste(p) && podeMover(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // SL
        p.setValues(posicao.getLinha() + 1, posicao.getColuna() + 1);
        if (getTabuleiro().posicaoExiste(p) && podeMover(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        return mat;
    }
}
