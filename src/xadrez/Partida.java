package xadrez;

import tabuleiro.Peca;
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

    public PecaDeXadrez performarMovimento(PosicaoDeXadrez posicaoDeOrigem, PosicaoDeXadrez posicaoDeDestino) {

        var origem = posicaoDeOrigem.toPosition();
        var destino = posicaoDeDestino.toPosition();
        validacaoDaPosicaoDeOrigem(origem);
        validacaoDaPosicaoDeDestino(origem, destino);
        var pecaCapturada = fazerMovimento(origem, destino);

        return (PecaDeXadrez) pecaCapturada;
    }

    private Peca fazerMovimento(Posicao origem, Posicao destino) {

        var peca = tabuleiro.removerUmaPeca(origem);
        var pecaCapturada = tabuleiro.removerUmaPeca(destino);

        tabuleiro.colocarPeca(peca, destino);

        return pecaCapturada;
    }

    private void validacaoDaPosicaoDeOrigem(Posicao posicao) {

        if(!tabuleiro.temUmaPeca(posicao)) {
            throw new ExcecaoDeXadrez("Não tem nenhuma peça na posicao de origem.");
        }

        if(!tabuleiro.peca(posicao).existeAlgumMovimentoPossivel()) {
            throw new ExcecaoDeXadrez("Não existem movimentos possíceis para a peça escolhida.");
        }
    }

    private void validacaoDaPosicaoDeDestino (Posicao origem, Posicao destino) {
        if (!tabuleiro.peca(origem).movimentoPossivel(destino)) {
            throw new ExcecaoDeXadrez("A peça escolhida não pode mover para a posição de destino.");
        }
    }


        private void colocarNovaPeca(char coluna , int linha, PecaDeXadrez peca) {
        tabuleiro.colocarPeca(peca, new PosicaoDeXadrez(coluna, linha).toPosition());
    }

    private void setupInicial() {

        colocarNovaPeca('c', 2, new Torre(tabuleiro, Cor.BRANCO));
        colocarNovaPeca('d', 2, new Torre(tabuleiro, Cor.BRANCO));
        colocarNovaPeca('e', 2, new Torre(tabuleiro, Cor.BRANCO));
        colocarNovaPeca('e', 1, new Torre(tabuleiro, Cor.BRANCO));
        colocarNovaPeca('c', 1, new Torre(tabuleiro, Cor.BRANCO));
        colocarNovaPeca('d', 1, new Rei(tabuleiro, Cor.BRANCO));


        colocarNovaPeca('c', 7, new Torre(tabuleiro, Cor.PRETO));
        colocarNovaPeca('c', 8, new Torre(tabuleiro, Cor.PRETO));
        colocarNovaPeca('d', 7, new Torre(tabuleiro, Cor.PRETO));
        colocarNovaPeca('e', 7, new Torre(tabuleiro, Cor.PRETO));
        colocarNovaPeca('e', 8, new Torre(tabuleiro, Cor.PRETO));
        colocarNovaPeca('d', 8, new Rei(tabuleiro, Cor.PRETO));
    }
}
