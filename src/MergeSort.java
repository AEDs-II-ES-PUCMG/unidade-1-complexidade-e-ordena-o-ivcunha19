import java.util.Arrays;

public class MergeSort<T extends Comparable<T>> implements IOrdenador<T> {

    private long comparacoes;
    private long movimentacoes;
    private double tempoOrdenacao;
    private double inicio;

    private double nanoToMilli = 1.0 / 1_000_000;

    @Override
    public long getComparacoes() {
        return comparacoes;
    }

    @Override
    public long getMovimentacoes() {
        return movimentacoes;
    }

    @Override
    public double getTempoOrdenacao() {
        return tempoOrdenacao;
    }

    private void iniciar() {
        this.comparacoes = 0;
        this.movimentacoes = 0;
        this.inicio = System.nanoTime();
    }

    private void terminar() {
        this.tempoOrdenacao = (System.nanoTime() - this.inicio) * nanoToMilli;
    }

    @Override
    public T[] ordenar(T[] dados) {
        T[] dadosOrdenados = Arrays.copyOf(dados, dados.length);
        iniciar();
        mergeSort(dadosOrdenados, 0, dadosOrdenados.length - 1);
        terminar();
        return dadosOrdenados;
    }

    private void mergeSort(T[] dados, int esq, int dir) {
        if (esq < dir) {
            int meio = (esq + dir) / 2;
            mergeSort(dados, esq, meio);
            mergeSort(dados, meio + 1, dir);
            intercalar(dados, esq, meio, dir);
        }
    }

    private void intercalar(T[] dados, int esq, int meio, int dir) {
        int n1 = meio - esq + 1;
        int n2 = dir - meio;

        T[] esquerda = Arrays.copyOfRange(dados, esq, meio + 1);
        T[] direita = Arrays.copyOfRange(dados, meio + 1, dir + 1);

        int i = 0, j = 0, k = esq;

        while (i < n1 && j < n2) {
            comparacoes++;
            if (esquerda[i].compareTo(direita[j]) <= 0) {
                dados[k++] = esquerda[i++];
            } else {
                dados[k++] = direita[j++];
            }
            movimentacoes++;
        }

        while (i < n1) {
            dados[k++] = esquerda[i++];
            movimentacoes++;
        }

        while (j < n2) {
            dados[k++] = direita[j++];
            movimentacoes++;
        }
    }
}