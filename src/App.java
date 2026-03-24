import java.util.Arrays;
import java.util.Random;

public class App {
    static final int[] tamanhosTesteGrande =  { 31_250_000, 62_500_000, 125_000_000, 250_000_000, 500_000_000 };
    static final int[] tamanhosTesteMedio =   {     12_500,     25_000,      50_000,     100_000,     200_000 };
    static final int[] tamanhosTestePequeno = {          3,          6,          12,          24,          48 };
    static Random aleatorio = new Random();
    static long operacoes;
    static double nanoToMilli = 1.0/1_000_000;
    

    /**
     * Gerador de vetores aleatórios de tamanho pré-definido. 
     * @param tamanho Tamanho do vetor a ser criado.
     * @return Vetor com dados aleatórios, com valores entre 1 e (tamanho/2), desordenado.
     */
    static int[] gerarVetor(int tamanho){
        int[] vetor = new int[tamanho];
        for (int i = 0; i < tamanho; i++) {
            vetor[i] = aleatorio.nextInt(1, tamanho/2);
        }
        return vetor;        
    }

    /**
     * Gerador de vetores de objetos do tipo Integer aleatórios de tamanho pré-definido. 
     * @param tamanho Tamanho do vetor a ser criado.
     * @return Vetor de Objetos Integer com dados aleatórios, com valores entre 1 e (tamanho/2), desordenado.
     */
    static Integer[] gerarVetorObjetos(int tamanho) {
        Integer[] vetor = new Integer[tamanho];
        for (int i = 0; i < tamanho; i++) {
            vetor[i] = aleatorio.nextInt(1, 10 * tamanho);
        }
        return vetor;
    }


    public static void main(String[] args) {
        int[] tam = tamanhosTesteMedio;
        for (int i = 0; i<tam.length; i++) {
            Integer[] vetor = gerarVetorObjetos(tam[i]);   
            BubbleSort<Integer> bolha = new BubbleSort<>();
            Integer[] vetorOrdenadoBolha = bolha.ordenar(vetor);
            System.out.println("\nVetor ordenado método Bolha:");
            System.out.println("Comparações: " + bolha.getComparacoes());
            System.out.println("Movimentações: " + bolha.getMovimentacoes());
            System.out.println("Tempo de ordenação (ms): " + bolha.getTempoOrdenacao());
        }
        

        for (int i = 0; i<tam.length; i++) {
            Integer[] vetor = gerarVetorObjetos(tam[i]);   
            InsertionSort<Integer> bolha = new InsertionSort<>();
            Integer[] vetorOrdenadoBolha = bolha.ordenar(vetor);
            System.out.println("\nVetor ordenado método insertion:");
            System.out.println("Comparações: " + bolha.getComparacoes());
            System.out.println("Movimentações: " + bolha.getMovimentacoes());
            System.out.println("Tempo de ordenação (ms): " + bolha.getTempoOrdenacao());
        }
        /* TO DO
        *Fazer a implementacao do restante do main para a ordenacao 
        *  com os algoritmos InsertionSort e SelectionSort
        */

    }
}
