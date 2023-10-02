package application;

import xadrez.ExcecaoDeXadrez;
import xadrez.Partida;
import xadrez.PecaDeXadrez;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Program {

    public static void main(String[] args) {


        System.out.println("T = Torre");
        System.out.println("C = Cavalo");
        System.out.println("B = Bispo");
        System.out.println("r = Rainha");
        System.out.println("R = Rei");
        System.out.println("P = Peão");
        Scanner sc = new Scanner(System.in);
        Partida partida = new Partida();
        List<PecaDeXadrez> pecasCapturadas = new ArrayList<>();

        while (!partida.getCheckMate()) {
            try {
            UI.limparTela();
            UI.printPartida(partida, pecasCapturadas);

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

            if(pecaCapturada != null) {
                pecasCapturadas.add(pecaCapturada);
            }

            if (partida.getPromocao() != null) {
                System.out.print("Qual peça você quer o peão seja promovida para? (r/C/B/T): ");
                String tipo = sc.nextLine();

                while (!tipo.equals("B") && !tipo.equals("C") && !tipo.equals("T") && !tipo.equals("r")) {
                    System.out.print("Valor inválido! Qual peça você quer o peão seja promovida para? (r/C/B/T):");
                    tipo = sc.nextLine();

                }
                partida.trocarPecaPromovida(tipo);
            }

            } catch (ExcecaoDeXadrez e) {
                System.out.println(e.getMessage());
                sc.nextLine();
            } catch (InputMismatchException e) {
                System.out.println(e.getMessage());
                sc.nextLine();
            }
        }

        UI.limparTela();
        UI.printPartida(partida, pecasCapturadas);
    }
}
