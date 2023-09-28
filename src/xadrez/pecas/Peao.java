package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.Partida;
import xadrez.PecaDeXadrez;

public class Peao extends PecaDeXadrez {

    private Partida partida;

    public Peao(Tabuleiro tabuleiro, Cor cor, Partida partida) {
        super(tabuleiro, cor);
        this.partida = partida;
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

            // movimento espeical en passant peça branca
            if (posicao.getLinha() == 3) {

                Posicao esquerda = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
                if (getTabuleiro().posicaoExiste(esquerda) && temUmaPecaInimiga(esquerda) && getTabuleiro().peca(esquerda) == partida.getEnPassantVulnerable()) {
                    mat[esquerda.getLinha() - 1][esquerda.getColuna()] = true;
                }

                Posicao direita = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
                if (getTabuleiro().posicaoExiste(direita) && temUmaPecaInimiga(direita) && getTabuleiro().peca(direita) == partida.getEnPassantVulnerable()) {
                    mat[direita.getLinha() - 1][direita.getColuna()] = true;
                }
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

            // movimento espeical en passant peça preta
            if (posicao.getLinha() == 4) {

                Posicao esquerda = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
                if (getTabuleiro().posicaoExiste(esquerda) && temUmaPecaInimiga(esquerda) && getTabuleiro().peca(esquerda) == partida.getEnPassantVulnerable()) {
                    mat[esquerda.getLinha() + 1][esquerda.getColuna()] = true;
                }

                Posicao direita = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
                if (getTabuleiro().posicaoExiste(direita) && temUmaPecaInimiga(direita) && getTabuleiro().peca(direita) == partida.getEnPassantVulnerable()) {
                    mat[direita.getLinha() + 1][direita.getColuna()] = true;
                }
            }
        }

        return mat;
    }

    @Override
    public String toString() {
        return "P";
    }
}
