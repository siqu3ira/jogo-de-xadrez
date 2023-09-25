package application;

import xadrez.Partida;

public class Program {

    public static void main(String[] args) {


        System.out.println("R = Torre");
        System.out.println("K = Rei");
        Partida partida = new Partida();
        UI.printTabuleiro(partida.getPecas());
    }
}
