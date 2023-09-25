package application;

import xadrez.Partida;

import java.util.Scanner;

public class Program {

    public static void main(String[] args) {


        System.out.println("R = Torre");
        System.out.println("K = Rei");
        Scanner sc = new Scanner(System.in);
        Partida partida = new Partida();

        while (true) {

            UI.printTabuleiro(partida.getPecas());
            System.out.println();
            System.out.println("Origem: ");
            var origem = UI.lerPosicaoDeXadrez(sc);

            System.out.println();
            System.out.println("Destino: ");
            var destino = UI.lerPosicaoDeXadrez(sc);

            var pecaCapturada = partida.performarMovimento(origem, destino);

        }
    }
}
