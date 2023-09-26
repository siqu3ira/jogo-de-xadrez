package application;

import xadrez.ExcecaoDeXadrez;
import xadrez.Partida;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Program {

    public static void main(String[] args) {


        System.out.println("R = Torre");
        System.out.println("K = Rei");
        Scanner sc = new Scanner(System.in);
        Partida partida = new Partida();

        while (true) {
            try {
            UI.limparTela();
            UI.printTabuleiro(partida.getPecas());

            System.out.println();
            System.out.println("Origem: ");
            var origem = UI.lerPosicaoDeXadrez(sc);

            boolean[][] movimentosPossiveis = partida.possiveisMovimentos(origem);
            UI.limparTela();
            UI.printTabuleiro(partida.getPecas(), movimentosPossiveis);

            System.out.println();
            System.out.println("Destino: ");
            var destino = UI.lerPosicaoDeXadrez(sc);

            var pecaCapturada = partida.performarMovimento(origem, destino);

            } catch (ExcecaoDeXadrez e) {
                System.out.println(e.getMessage());
                sc.nextLine();
            } catch (InputMismatchException e) {
                System.out.println(e.getMessage());
                sc.nextLine();
            }
        }
    }
}
