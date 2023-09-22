package application;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Partida;

public class Program {

    public static void main(String[] args) {

        Partida partida = new Partida();
        UI.printTabuleiro(partida.getPecas());
    }
}
