package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaDeXadrez;

public class Peao extends PecaDeXadrez {


    public Peao(Tabuleiro tabuleiro, Cor cor) {
        super(tabuleiro, cor);
    }

    @Override
    public boolean[][] movimentosPossiveis() {
        var mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
        Posicao p = new Posicao(0, 0);

        if(getCor() == Cor.BRANCA) { // Possíveis movimentos dos peões brancos

            // Frente (movimentar para cima)
            p.setValues(posicao.getLinha() - 1, posicao.getColuna());
            if(getTabuleiro().posicaoExiste(p) && !getTabuleiro().temUmaPeca(p)) {
                mat[p.getLinha()][p.getColuna()] = true;
            }

            // Duas casas a frente caso seja o primeiro movimento
            p.setValues(posicao.getLinha() - 2, posicao.getColuna());
            var p2 = new Posicao(posicao.getLinha() - 1, posicao.getColuna());
            if(getTabuleiro().posicaoExiste(p) && !getTabuleiro().temUmaPeca(p) && getTabuleiro().posicaoExiste(p2) &&
                    !getTabuleiro().temUmaPeca(p2) && getNumeroDeJogadas() == 0) {
                mat[p.getLinha()][p.getColuna()] = true;
            }

            // Diagonal esquerda
            p.setValues(posicao.getLinha() - 1, posicao.getColuna() - 1);
            if(getTabuleiro().posicaoExiste(p) && temUmaPecaInimiga(p)) {
                mat[p.getLinha()][p.getColuna()] = true;
            }

            // Diagonal direita
            p.setValues(posicao.getLinha() - 1, posicao.getColuna() + 1);
            if(getTabuleiro().posicaoExiste(p) && temUmaPecaInimiga(p)) {
                mat[p.getLinha()][p.getColuna()] = true;
            }

        } else { // Possíveis movimentos dos peões pretos

            // Frente (movimentar para baixo)
            p.setValues(posicao.getLinha() + 1, posicao.getColuna());
            if(getTabuleiro().posicaoExiste(p) && !getTabuleiro().temUmaPeca(p)) {
                mat[p.getLinha()][p.getColuna()] = true;
            }

            // Duas casas a frente caso seja o primeiro movimento
            p.setValues(posicao.getLinha() + 2, posicao.getColuna());
            var p2 = new Posicao(posicao.getLinha() + 1, posicao.getColuna());
            if(getTabuleiro().posicaoExiste(p) && !getTabuleiro().temUmaPeca(p) && getTabuleiro().posicaoExiste(p2) &&
                    !getTabuleiro().temUmaPeca(p2) && getNumeroDeJogadas() == 0) {
                mat[p.getLinha()][p.getColuna()] = true;
            }

            // Diagonal esquerda
            p.setValues(posicao.getLinha() + 1, posicao.getColuna() - 1);
            if(getTabuleiro().posicaoExiste(p) && temUmaPecaInimiga(p)) {
                mat[p.getLinha()][p.getColuna()] = true;
            }

            // Diagonal direita
            p.setValues(posicao.getLinha() + 1, posicao.getColuna() + 1);
            if(getTabuleiro().posicaoExiste(p) && temUmaPecaInimiga(p)) {
                mat[p.getLinha()][p.getColuna()] = true;
            }
        }

        return mat;
    }

    @Override
    public String toString() {
        return "P";
    }
}
