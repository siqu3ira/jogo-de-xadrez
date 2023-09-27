package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaDeXadrez;

public class Rainha extends PecaDeXadrez {


    public Rainha(Tabuleiro tabuleiro, Cor cor) {
        super(tabuleiro, cor);
    }

    public String toString() {
        return "r";
    }

    @Override
    public boolean[][] movimentosPossiveis() {
        var mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
        Posicao p = new Posicao(0, 0);

        // Frente
        p.setValues(posicao.getLinha() - 1, posicao.getColuna());
        while(getTabuleiro().posicaoExiste(p) && !getTabuleiro().temUmaPeca(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
            p.setLinha(p.getLinha() - 1);
        }
        if (getTabuleiro().posicaoExiste(p) && temUmaPecaInimiga(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // Esqueda
        p.setValues(posicao.getLinha(), posicao.getColuna() - 1);
        while(getTabuleiro().posicaoExiste(p) && !getTabuleiro().temUmaPeca(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
            p.setColuna(p.getColuna() - 1);
        }
        if (getTabuleiro().posicaoExiste(p) && temUmaPecaInimiga(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // Direita
        p.setValues(posicao.getLinha(), posicao.getColuna() + 1);
        while(getTabuleiro().posicaoExiste(p) && !getTabuleiro().temUmaPeca(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
            p.setColuna(p.getColuna() + 1);
        }
        if (getTabuleiro().posicaoExiste(p) && temUmaPecaInimiga(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // Atrás
        p.setValues(posicao.getLinha() + 1, posicao.getColuna());
        while(getTabuleiro().posicaoExiste(p) && !getTabuleiro().temUmaPeca(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
            p.setLinha(p.getLinha() + 1);
        }
        if (getTabuleiro().posicaoExiste(p) && temUmaPecaInimiga(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        p.setValues(posicao.getLinha() - 1, posicao.getColuna() - 1);
        while(getTabuleiro().posicaoExiste(p) && !getTabuleiro().temUmaPeca(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
            p.setValues(p.getLinha() - 1, p.getColuna() - 1);
        }
        if (getTabuleiro().posicaoExiste(p) && temUmaPecaInimiga(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // NE
        p.setValues(posicao.getLinha() - 1, posicao.getColuna() + 1);
        while(getTabuleiro().posicaoExiste(p) && !getTabuleiro().temUmaPeca(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
            p.setValues(p.getLinha() - 1, p.getColuna() + 1);
        }
        if (getTabuleiro().posicaoExiste(p) && temUmaPecaInimiga(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // SE
        p.setValues(posicao.getLinha() + 1, posicao.getColuna() + 1);
        while(getTabuleiro().posicaoExiste(p) && !getTabuleiro().temUmaPeca(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
            p.setValues(p.getLinha() + 1, p.getColuna() + 1);
        }
        if (getTabuleiro().posicaoExiste(p) && temUmaPecaInimiga(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // SO
        p.setValues(posicao.getLinha() + 1, posicao.getColuna() - 1);
        while(getTabuleiro().posicaoExiste(p) && !getTabuleiro().temUmaPeca(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
            p.setValues(p.getLinha() + 1, p.getColuna() - 1);
        }
        if (getTabuleiro().posicaoExiste(p) && temUmaPecaInimiga(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        return mat;
    }
}
