package xadrez;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;
import java.util.List;

import java.awt.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Partida {

    private int turno;
    private Cor jogadorAtual;
    private Tabuleiro tabuleiro;
    private boolean check; // Valor padrão de uma variaável do tipo boolean é false

    private List<Peca> pecasNoTabuleiro = new ArrayList<>();
    private List<Peca> pecasCapturadas = new ArrayList<>();

    public Partida() {
        tabuleiro = new Tabuleiro(8, 8);
        turno = 1;
        jogadorAtual = Cor.BRANCO;
        setupInicial();
    }

    public int getTurno() {
        return  turno;
    }

    public Cor getJogadorAtual() {
        return  jogadorAtual;
    }

    public boolean getCheck() {
        return check;
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

    public boolean[][] possiveisMovimentos(PosicaoDeXadrez posicaoDeOrigem) {
        Posicao posicao = posicaoDeOrigem.toPosition();
        validacaoDaPosicaoDeOrigem(posicao);

        return tabuleiro.peca(posicao).movimentosPossiveis();
    }

    public PecaDeXadrez performarMovimento(PosicaoDeXadrez posicaoDeOrigem, PosicaoDeXadrez posicaoDeDestino) {

        var origem = posicaoDeOrigem.toPosition();
        var destino = posicaoDeDestino.toPosition();
        validacaoDaPosicaoDeOrigem(origem);
        validacaoDaPosicaoDeDestino(origem, destino);
        var pecaCapturada = fazerMovimento(origem, destino);

        if (testeDeCheck(jogadorAtual)) {
            desfazerMovimento(origem, destino, pecaCapturada);
            throw new ExcecaoDeXadrez("Você não pode se colocar em check");
        }

        check = testeDeCheck(oponente(jogadorAtual)) ? true : false;

        proximoTurno();

        return (PecaDeXadrez) pecaCapturada;
    }

    private Peca fazerMovimento(Posicao origem, Posicao destino) {

        var peca = tabuleiro.removerUmaPeca(origem);
        var pecaCapturada = tabuleiro.removerUmaPeca(destino);
        tabuleiro.colocarPeca(peca, destino);

        if (pecaCapturada != null) {
            pecasNoTabuleiro.remove(pecaCapturada);
            pecasCapturadas.add(pecaCapturada);
        }

        return pecaCapturada;
    }

    private void desfazerMovimento(Posicao origem, Posicao destino, Peca pecaCapturada) {
        Peca p = tabuleiro.removerUmaPeca(destino);
        tabuleiro.colocarPeca(p, origem);

        if (pecaCapturada != null) {
            tabuleiro.colocarPeca(pecaCapturada, destino);
            pecasCapturadas.remove(pecaCapturada);
            pecasNoTabuleiro.add(pecaCapturada);
        }
    }

    private void validacaoDaPosicaoDeOrigem(Posicao posicao) {

        if(!tabuleiro.temUmaPeca(posicao)) {
            throw new ExcecaoDeXadrez("Não tem nenhuma peça na posicao de origem.");
        }
        if (jogadorAtual != ((PecaDeXadrez)tabuleiro.peca(posicao)).getCor()) {
            throw new ExcecaoDeXadrez("Peça escolhida não é sua.");
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

    private void proximoTurno() {
        turno ++;
        jogadorAtual = (jogadorAtual == Cor.BRANCO) ? Cor.PRETO : Cor.BRANCO;
    }

    private Cor oponente(Cor cor) {
        return (cor == Cor.BRANCO) ? Cor.PRETO : Cor.BRANCO;
    }

    private PecaDeXadrez rei(Cor cor) {

        List<Peca> lista = pecasNoTabuleiro.stream().filter(x -> ((PecaDeXadrez) x).getCor() == cor).toList();

        for (Peca p : lista) {
            if (p instanceof Rei) {
                return (PecaDeXadrez)p;
            }
        }

        throw new IllegalStateException("Não tem nenhum rei da cor" + cor + "no tabuleiro");
    }

    private boolean testeDeCheck(Cor cor) {
        var posicaoRei = rei(cor).getPosicaoDeXadrez().toPosition();
        List<Peca> pecasInimigas = pecasNoTabuleiro.stream().filter(x -> ((PecaDeXadrez) x).getCor() == oponente(cor)).toList();

        for(Peca p : pecasInimigas) {
            boolean[][] mat = p.movimentosPossiveis();
            if (mat[posicaoRei.getLinha()][posicaoRei.getColuna()]) {
                return true;
            }
        }

        return false;
    }

    private void colocarNovaPeca(char coluna , int linha, PecaDeXadrez peca) {
        tabuleiro.colocarPeca(peca, new PosicaoDeXadrez(coluna, linha).toPosition());
        pecasNoTabuleiro.add(peca);
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
