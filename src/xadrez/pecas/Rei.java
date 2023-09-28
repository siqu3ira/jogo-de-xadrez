package xadrez.pecas;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.Partida;
import xadrez.PecaDeXadrez;

public class Rei extends PecaDeXadrez {

    private Partida partida;

    public Rei(Tabuleiro tabuleiro, Cor cor, Partida partida) {
        super(tabuleiro, cor);
        this.partida = partida;
    }

    @Override
    public String toString() {
        return "R";
    }

    private boolean podeMover(Posicao posicao) {
        PecaDeXadrez peca =(PecaDeXadrez) getTabuleiro().peca(posicao);

        return peca == null || peca.getCor() != getCor();
    }

    private boolean testMudarPosicao(Posicao pos) {
        PecaDeXadrez peca = (PecaDeXadrez)getTabuleiro().peca(pos);

        return peca != null && peca instanceof Torre &&
                peca.getCor() == getCor() && peca.getNumeroDeJogadas() == 0;
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

        // Movimento especial de trocar poiscao com a torre
        if(getNumeroDeJogadas() == 0 && !partida.getCheck()) {
            // lado direito
            Posicao posT1 = new Posicao(posicao.getLinha(), posicao.getColuna() + 3);
            if(testMudarPosicao(posT1)) {
                Posicao p1 = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
                Posicao p2 = new Posicao(posicao.getLinha(), posicao.getColuna() + 2);
                if(getTabuleiro().peca(p1) == null && getTabuleiro().peca(p2) == null) {
                    mat[posicao.getLinha()][posicao.getColuna() + 2] = true;
                }
            }

            // lado esquerdo
            Posicao posT2 = new Posicao(posicao.getLinha(), posicao.getColuna() - 4);
            if(testMudarPosicao(posT1)) {
                Posicao p1 = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
                Posicao p2 = new Posicao(posicao.getLinha(), posicao.getColuna() - 2);
                Posicao p3 = new Posicao(posicao.getLinha(), posicao.getColuna() - 3);
                if(getTabuleiro().peca(p1) == null && getTabuleiro().peca(p2) == null && getTabuleiro().peca(p3) == null) {
                    mat[posicao.getLinha()][posicao.getColuna() - 2] = true;
                }
            }
        }



        return mat;
    }
}
