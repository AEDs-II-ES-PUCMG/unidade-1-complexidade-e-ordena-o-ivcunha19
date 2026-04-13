import java.util.Arrays;

public class SelectionSort<T extends Comparable<T>> implements IOrdenador<T> {
    private long comparacoes;
    private long movimentacoes;
    private double tempoOrdenacao;
    private double inicio;

    private double nanoToMilli = 1.0/1_000_000;

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

    private void iniciar(){
        this.comparacoes = 0;
        this.movimentacoes = 0;
        this.inicio = System.nanoTime();
    }

    private void terminar(){
        this.tempoOrdenacao = (System.nanoTime() - this.inicio) * nanoToMilli;
    }

    private void swap(int x, int y, T[] vetor) {
        T temp = vetor[x];
        vetor[x] = vetor[y];
        vetor[y] = temp;
        movimentacoes+=3;
    }

    @Override
    public T[] ordenar(T[] dados) {
        T[] dadosOrdenados = Arrays.copyOf(dados, dados.length);
        int tamanho = dadosOrdenados.length;
        iniciar();
        for (int i = 0; i < tamanho; i++) {
            T menor = dadosOrdenados[i];
            int indiceMenor = i;
            for (int j = i+1; j < tamanho; j++) {
                if (dadosOrdenados[j].compareTo(menor) < 0){
                    menor = dadosOrdenados[j];
                    indiceMenor = j;
                }
            }
            swap(i, indiceMenor, dadosOrdenados);
        }	
        terminar();
        return dadosOrdenados;
    }
}
