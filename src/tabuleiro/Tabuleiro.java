package tabuleiro;

public class Tabuleiro {

    private int linhas;
    private int colunas;
    private Peca[][] pecas;

    public Tabuleiro(int linhas, int colunas) {
        if (linhas < 1 || colunas < 1) {
            throw new ExcecaoDeTabuleiro("Erro ao criar o tabuleiro: Tem que ter pelo menos uma linha e uma coluna");
        }
        this.linhas = linhas;
        this.colunas = colunas;
        pecas = new Peca[linhas][colunas];
    }

    public int getLinhas() {
        return linhas;
    }

    public int getColunas() {
        return colunas;
    }

    public Peca peca(int linha, int coluna) {

        if(!posicaoExiste(linha, coluna)) {
            throw new ExcecaoDeTabuleiro("Posição inexistente no tabuleiro");
        }
        return pecas[linha][coluna];
    }

    public Peca peca(Posicao posicao) {

        if(!posicaoExiste(posicao)) {
            throw new ExcecaoDeTabuleiro("Posição inexistente no tabuleiro");
        }
        return pecas[posicao.getLinha()][posicao.getColuna()];
    }

    public void ColocarPeca(Peca peca, Posicao posicao) {

        if(temUmaPeca(posicao)) {
            throw new ExcecaoDeTabuleiro("Já tem uma peça na posição: " + posicao);
        }
        pecas[posicao.getLinha()][posicao.getColuna()] = peca;
        peca.posicao = posicao;
    }

    private boolean posicaoExiste(int linha, int coluna) {
        return linha >= 0 && linha < linhas && coluna >= 0 && coluna < colunas;
    }

    public boolean posicaoExiste (Posicao posicao) {
        return posicaoExiste(posicao.getLinha(), posicao.getColuna());
    }

    public boolean temUmaPeca (Posicao posicao) {

        if(!posicaoExiste(posicao)) {
            throw new ExcecaoDeTabuleiro("Posição inexistente no tabuleiro");
        }

        return peca(posicao) != null;
    }
}
