package xadrez;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

import java.awt.*;

public class Partida {

    private Tabuleiro tabuleiro;

    public Partida() {
        tabuleiro = new Tabuleiro(8, 8);
        setupInicial();
    }

    public PecaDeXadrez[][] getPecas() {
        PecaDeXadrez[][] matriz = new PecaDeXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];

        for(int i = 0; i < tabuleiro.getLinhas(); i++) {
            for(int j = 0; j < tabuleiro.getColunas(); j++) {
                matriz[i][j] = (PecaDeXadrez) tabuleiro.peca(i, j);
            }
        }

        return matriz;
    }

    private void setupInicial() {
        tabuleiro.ColocarPeca(new Torre(tabuleiro, Cor.BRANCO), new Posicao(2, 1));
        tabuleiro.ColocarPeca(new Rei(tabuleiro, Cor.PRETO), new Posicao(0, 4));
        tabuleiro.ColocarPeca(new Rei(tabuleiro, Cor.BRANCO), new Posicao(7, 4));
    }
}
