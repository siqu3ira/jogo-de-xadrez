package xadrez;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.pecas.*;

import java.util.ArrayList;
import java.util.List;

public class Partida {

    private int turno;
    private Cor jogadorAtual;
    private Tabuleiro tabuleiro;
    private boolean check; // Valor padrão de uma variaável do tipo boolean é false
    private boolean checkMate;
    private PecaDeXadrez enPassantVulnerable;
    private PecaDeXadrez promocao;

    private List<Peca> pecasNoTabuleiro = new ArrayList<>();
    private List<Peca> pecasCapturadas = new ArrayList<>();

    public Partida() {
        tabuleiro = new Tabuleiro(8, 8);
        turno = 1;
        jogadorAtual = Cor.BRANCA;
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

    public boolean getCheckMate() {
        return checkMate;
    }

    public PecaDeXadrez getEnPassantVulnerable() {
        return enPassantVulnerable;
    }

    public PecaDeXadrez getPromocao() {
        return promocao;
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

        PecaDeXadrez pecaMovida = (PecaDeXadrez)tabuleiro.peca(destino);

        // Movimento especial promocao
        promocao = null;
        if(pecaMovida instanceof Peao) {
            if((pecaMovida.getCor() == Cor.BRANCA && destino.getLinha() == 0) || (pecaMovida.getCor() == Cor.PRETA && destino.getLinha() == 7)) {
                promocao = (PecaDeXadrez)tabuleiro.peca(destino);
                promocao = trocarPecaPromovida("r");
            }
        }



        check = testeDeCheck(oponente(jogadorAtual)) ? true : false;

        if (testeDeCheckMate(oponente(jogadorAtual))) {
            checkMate = true;
        } else {
            proximoTurno();
        }

        // movimento especial en passant
        if (pecaMovida instanceof Peao && (destino.getLinha() == origem.getLinha() - 2|| destino.getLinha() ==  origem.getLinha() + 2)) {
            enPassantVulnerable = pecaMovida;
        } else {
            enPassantVulnerable = null;
        }

        return (PecaDeXadrez) pecaCapturada;
    }

    public PecaDeXadrez trocarPecaPromovida(String tipo) {

        if (promocao == null) {
            throw new IllegalStateException("Não tem nenhuma peça para ser promovida");
        }

        if (!tipo.equals("B") && !tipo.equals("C") && !tipo.equals("T") && !tipo.equals("r")) {

            return promocao;
        }

        Posicao pos = promocao.getPosicaoDeXadrez().toPosition();
        Peca peca = tabuleiro.removerUmaPeca(pos);
        pecasNoTabuleiro.remove(peca);

        PecaDeXadrez novaPeca = novaPeca(tipo, promocao.getCor());
        tabuleiro.colocarPeca(novaPeca, pos);
        pecasNoTabuleiro.add(novaPeca);

        return novaPeca;
    }

    private PecaDeXadrez novaPeca(String tipo, Cor cor) {
        if(tipo.equals("B")) return new Bispo(tabuleiro, cor);
        if(tipo.equals("T")) return new Torre(tabuleiro, cor);
        if(tipo.equals("C")) return new Cavalo(tabuleiro, cor);

        return new Rainha(tabuleiro, cor);
   }

    private Peca fazerMovimento(Posicao origem, Posicao destino) {

        PecaDeXadrez peca = (PecaDeXadrez)tabuleiro.removerUmaPeca(origem);
        peca.incrementarNumeroDeJogadas();
        var pecaCapturada = tabuleiro.removerUmaPeca(destino);
        tabuleiro.colocarPeca(peca, destino);

        if (pecaCapturada != null) {
            pecasNoTabuleiro.remove(pecaCapturada);
            pecasCapturadas.add(pecaCapturada);
        }

        // Movimento especial do rei, trocando de lugar com a torre do lado direito
        if(peca instanceof Rei && destino.getColuna() == origem.getColuna() + 2) {
            Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() + 3);
            Posicao destinoT = new Posicao(origem.getLinha(), origem.getColuna() + 1);
            PecaDeXadrez torre = (PecaDeXadrez)tabuleiro.removerUmaPeca(origemT);
            tabuleiro.colocarPeca(torre, destinoT);
            torre.incrementarNumeroDeJogadas();
        }

        // Movimento especial do rei, trocando de lugar com a torre do lado esquerdo
        if(peca instanceof Rei && destino.getColuna() == origem.getColuna() - 2) {
            Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() - 4);
            Posicao destinoT = new Posicao(origem.getLinha(), origem.getColuna() - 1);
            PecaDeXadrez torre = (PecaDeXadrez)tabuleiro.removerUmaPeca(origemT);
            tabuleiro.colocarPeca(torre, destinoT);
            torre.incrementarNumeroDeJogadas();
        }

        // Movimento especial en passant
        if (peca instanceof  Peao) {
            if (origem.getColuna() != destino.getColuna() && pecaCapturada == null) {
                Posicao posicaoDoPeao;
                if (peca.getCor() == Cor.BRANCA) {
                    posicaoDoPeao = new Posicao(destino.getLinha() + 1, destino.getColuna());
                } else {
                    posicaoDoPeao = new Posicao(destino.getLinha() - 1, destino.getColuna());
                }

                pecaCapturada = tabuleiro.removerUmaPeca(posicaoDoPeao);
                pecasCapturadas.add(pecaCapturada);
                pecasNoTabuleiro.remove(pecaCapturada);
            }
        }

        return pecaCapturada;
    }

    private void desfazerMovimento(Posicao origem, Posicao destino, Peca pecaCapturada) {
        PecaDeXadrez p = (PecaDeXadrez)tabuleiro.removerUmaPeca(destino);
        p.decrementarNumeroDeJogadas();
        tabuleiro.colocarPeca(p, origem);

        if (pecaCapturada != null) {
            tabuleiro.colocarPeca(pecaCapturada, destino);
            pecasCapturadas.remove(pecaCapturada);
            pecasNoTabuleiro.add(pecaCapturada);
        }

        // Movimento especial do rei, trocando de lugar com a torre do lado direito
        if(p instanceof Rei && destino.getColuna() == origem.getColuna() + 2) {
            Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() + 3);
            Posicao destinoT = new Posicao(origem.getLinha(), origem.getColuna() + 1);
            PecaDeXadrez torre = (PecaDeXadrez)tabuleiro.removerUmaPeca(destinoT);
            tabuleiro.colocarPeca(torre, origemT);
            torre.decrementarNumeroDeJogadas();
        }

        // Movimento especial do rei, trocando de lugar com a torre do lado esquerdo
        if(p instanceof Rei && destino.getColuna() == origem.getColuna() - 2) {
            Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() - 4);
            Posicao destinoT = new Posicao(origem.getLinha(), origem.getColuna() - 1);
            PecaDeXadrez torre = (PecaDeXadrez)tabuleiro.removerUmaPeca(destinoT);
            tabuleiro.colocarPeca(torre, origemT);
            torre.decrementarNumeroDeJogadas();
        }

        if (p instanceof  Peao) {
            if (origem.getColuna() != destino.getColuna() && pecaCapturada == enPassantVulnerable) {
                PecaDeXadrez peao = (PecaDeXadrez)tabuleiro.removerUmaPeca(destino);
                Posicao posicaoDoPeao;
                if (p.getCor() == Cor.BRANCA) {
                    posicaoDoPeao = new Posicao(3, destino.getColuna());
                } else {
                    posicaoDoPeao = new Posicao(4, destino.getColuna());
                }

                tabuleiro.colocarPeca(peao, posicaoDoPeao);
            }
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
        jogadorAtual = (jogadorAtual == Cor.BRANCA) ? Cor.PRETA : Cor.BRANCA;
    }

    private Cor oponente(Cor cor) {
        return (cor == Cor.BRANCA) ? Cor.PRETA : Cor.BRANCA;
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

    private boolean testeDeCheckMate(Cor cor) {
        if (!testeDeCheck(cor)) {
            return false;
        }

        List<Peca> lista = pecasNoTabuleiro.stream().filter(x -> ((PecaDeXadrez) x).getCor() == cor).toList();

        for(Peca p :lista) {
            boolean[][] mat = p.movimentosPossiveis();
            for (int i = 0; i < mat.length; i++) {
                for (int j = 0; j < mat.length; j++) {
                    if(mat[i][j]) {
                       Posicao origem = ((PecaDeXadrez)p).getPosicaoDeXadrez().toPosition();
                       Posicao destino = new Posicao(i, j);
                       Peca pecaCapturada = fazerMovimento(origem, destino);
                       boolean testeCheck = testeDeCheck(cor);
                       desfazerMovimento(origem, destino, pecaCapturada);
                       if (!testeCheck) {
                           return false;
                       }
                    }
                }
            }
        }

        return true;
    }

    private void colocarNovaPeca(char coluna , int linha, PecaDeXadrez peca) {
        tabuleiro.colocarPeca(peca, new PosicaoDeXadrez(coluna, linha).toPosition());
        pecasNoTabuleiro.add(peca);
    }

    private void setupInicial() {

        colocarNovaPeca('a', 1, new Torre(tabuleiro, Cor.BRANCA));
        colocarNovaPeca('b', 1, new Cavalo(tabuleiro, Cor.BRANCA));
        colocarNovaPeca('c', 1, new Bispo(tabuleiro, Cor.BRANCA));
        colocarNovaPeca('d', 1, new Rainha(tabuleiro, Cor.BRANCA));
        colocarNovaPeca('e', 1, new Rei(tabuleiro, Cor.BRANCA, this));
        colocarNovaPeca('f', 1, new Bispo(tabuleiro, Cor.BRANCA));
        colocarNovaPeca('g', 1, new Cavalo(tabuleiro, Cor.BRANCA));
        colocarNovaPeca('h', 1, new Torre(tabuleiro, Cor.BRANCA));
        colocarNovaPeca('a', 2, new Peao(tabuleiro, Cor.BRANCA, this));
        colocarNovaPeca('b', 2, new Peao(tabuleiro, Cor.BRANCA, this));
        colocarNovaPeca('c', 2, new Peao(tabuleiro, Cor.BRANCA, this));
        colocarNovaPeca('d', 2, new Peao(tabuleiro, Cor.BRANCA, this));
        colocarNovaPeca('e', 2, new Peao(tabuleiro, Cor.BRANCA, this));
        colocarNovaPeca('f', 2, new Peao(tabuleiro, Cor.BRANCA, this));
        colocarNovaPeca('g', 2, new Peao(tabuleiro, Cor.BRANCA, this));
        colocarNovaPeca('h', 2, new Peao(tabuleiro, Cor.BRANCA, this));

        colocarNovaPeca('a', 8, new Torre(tabuleiro, Cor.PRETA));
        colocarNovaPeca('b', 8, new Cavalo(tabuleiro, Cor.PRETA));
        colocarNovaPeca('c', 8, new Bispo(tabuleiro, Cor.PRETA));
        colocarNovaPeca('d', 8, new Rainha(tabuleiro, Cor.PRETA));
        colocarNovaPeca('e', 8, new Rei(tabuleiro, Cor.PRETA, this)); // this está referenciando a própria classe partida
        colocarNovaPeca('f', 8, new Bispo(tabuleiro, Cor.PRETA));
        colocarNovaPeca('g', 8, new Cavalo(tabuleiro, Cor.PRETA));
        colocarNovaPeca('h', 8, new Torre(tabuleiro, Cor.PRETA));
        colocarNovaPeca('a', 7, new Peao(tabuleiro, Cor.PRETA, this));
        colocarNovaPeca('b', 7, new Peao(tabuleiro, Cor.PRETA, this));
        colocarNovaPeca('c', 7, new Peao(tabuleiro, Cor.PRETA, this));
        colocarNovaPeca('d', 7, new Peao(tabuleiro, Cor.PRETA, this));
        colocarNovaPeca('e', 7, new Peao(tabuleiro, Cor.PRETA, this));
        colocarNovaPeca('f', 7, new Peao(tabuleiro, Cor.PRETA, this));
        colocarNovaPeca('g', 7, new Peao(tabuleiro, Cor.PRETA, this));
        colocarNovaPeca('h', 7, new Peao(tabuleiro, Cor.PRETA, this));

    }
}
