
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

/**
 * MIT License
 *
 * Copyright(c) 2022-25 João Caram <caram@pucminas.br>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

public class AppOficina {

    static final int MAX_PEDIDOS = 100;
    static Produto[] produtos;
    static int quantProdutos = 0;
    static String nomeArquivoDados = "produtos.txt";
    static IOrdenador<Produto> ordenador;
    static Comparator<Produto> comparator;
    // #region utilidades
    static Scanner teclado;

    

    static <T extends Number> T lerNumero(String mensagem, Class<T> classe) {
        System.out.print(mensagem + ": ");
        T valor;
        try {
            valor = classe.getConstructor(String.class).newInstance(teclado.nextLine());
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException e) {
            return null;
        }
        return valor;
    }

    static void limparTela() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    static void pausa() {
        System.out.println("Tecle Enter para continuar.");
        teclado.nextLine();
    }

    static void cabecalho() {
        limparTela();
        System.out.println("XULAMBS COMÉRCIO DE COISINHAS v0.2\n================");
    }
    

    static int exibirMenuPrincipal() {
        cabecalho();
        System.out.println("1 - Procurar produto");
        System.out.println("2 - Filtrar produtos por preço máximo");
        System.out.println("3 - Ordenar produtos");
        System.out.println("4 - Embaralhar produtos");
        System.out.println("5 - Listar produtos");
        System.out.println("6 - Cadastrar produto");
        System.out.println("0 - Finalizar");
       
        return lerNumero("Digite sua opção", Integer.class);
    }

    static int exibirMenuOrdenadores() {
        cabecalho();
        System.out.println("1 - Bolha");
        System.out.println("2 - Inserção");
        System.out.println("3 - Seleção");
        System.out.println("4 - Mergesort");
        System.out.println("0 - Finalizar");
       
        return lerNumero("Digite sua opção", Integer.class);
    }

    static int exibirMenuComparadores() {
        cabecalho();
        System.out.println("1 - Padrão");
        System.out.println("2 - Por código");
        
        return lerNumero("Digite sua opção", Integer.class);
    }

    // #endregion
    static Produto[] carregarProdutos(String nomeArquivo){
        Scanner dados;
        Produto[] dadosCarregados;
        try{
            dados = new Scanner(new File(nomeArquivo));
            int tamanho = Integer.parseInt(dados.nextLine());
            
            dadosCarregados = new Produto[tamanho + 10];
            int i = 0;
            while (dados.hasNextLine()) {
                Produto novoProduto = Produto.criarDoTexto(dados.nextLine());
                dadosCarregados[i] = novoProduto;
                i++;
            }
            dados.close();
        }catch (FileNotFoundException fex){
            System.out.println("Arquivo não encontrado. Iniciando array limpo.");
            dadosCarregados = new Produto[10];
        }
        return dadosCarregados;
    }


    static Produto localizarProduto() {
        cabecalho();
        System.out.println("Localizando um produto");
        System.out.print("Digite o nome (descrição) do produto: ");
        String nomeStr = teclado.nextLine();
        Produto localizado = null;
        
        Produto dummy = new ProdutoNaoPerecivel(nomeStr, 0.01);
        
        for (int i = 0; i < quantProdutos && localizado == null; i++) {
            if (produtos[i] != null && produtos[i].equals(dummy)) {
                localizado = produtos[i];
            }
        }
        return localizado;
    }

    private static void mostrarProduto(Produto produto) {
        cabecalho();
        String mensagem = "Dados inválidos";
        
        if(produto!=null){
            mensagem = String.format("Dados do produto:\n%s", produto);            
        }
        
        System.out.println(mensagem);
    }

    private static void filtrarPorPrecoMaximo(){
        cabecalho();
        System.out.println("Filtrando por valor máximo:");
        double valor = lerNumero("valor", Double.class);
        StringBuilder relatorio = new StringBuilder();
        for (int i = 0; i < quantProdutos; i++) {
            if(produtos[i].valorDeVenda() < valor)
            relatorio.append(produtos[i]+"\n");
        }
        System.out.println(relatorio.toString());
    }

    static void ordenarProdutos(){
        cabecalho();
        
        int opcao = exibirMenuOrdenadores();
        Produto[] dadosCarregados = Arrays.copyOf(produtos, quantProdutos);
        int menu = -1;
        while (menu != 0) {
            System.out.println("Digite o numero correspondente ao metodo desesjado:");
            System.out.println("Bubble = 1\nInsertion = 2\nSelection = 3\nMerge = 4\nSair = 0");
            menu = Integer.parseInt(teclado.nextLine());
            if(menu == 0) break;
            System.out.println("Como você deseja ordenar as informações?: ");
            System.out.println("Por código: 1\nPor texto: 2");
            int ord = Integer.parseInt(teclado.nextLine());
            switch (ord) {
                case 1:
                    comparator = new ComparadorPorCodigo();
                    break;
                case 2:
                    comparator = null;
                    break;
                default:
                    comparator = null;
                    break;
            }
            switch (menu) {
                case 1:
                    fazBubble(dadosCarregados);
                    break;

                case 2:
                    fazInsertion(dadosCarregados);
                    break;
                case 3:
                    fazSelection(dadosCarregados);
                    break;
                
                case 4:
                    fazMerge(dadosCarregados);
                    break;
                case 0:
                    System.out.println("Finalizando sistema...");
            
                default:
                    System.err.println("Valor inválido");
                    break;
            }
        }
    }

    static void embaralharProdutos(){
        if(produtos != null && quantProdutos > 0){
            java.util.Collections.shuffle(java.util.Arrays.asList(produtos).subList(0, quantProdutos));
        }
    }

    static void verificarSubstituicao(Produto[] dadosOriginais, Produto[] copiaDados){
        System.out.print("Deseja sobrescrever os dados originais pelos ordenados (S/N)? ");
        String resposta = teclado.nextLine().toUpperCase();
        if(resposta.equals("S")){
            produtos = Arrays.copyOf(copiaDados, copiaDados.length + 10);
            System.out.println("Vetor substituído no sistema.");
        }
    }

    static void listarProdutos(){
        cabecalho();
        for (int i = 0; i < quantProdutos; i++) {
            System.out.println(produtos[i]);
        }
    }

    static void cadastrarProduto() {
        cabecalho();
        System.out.println("Cadastro de Novo Produto");
        int tipo = lerNumero("Tipo (1 - Não Perecível, 2 - Perecível)", Integer.class);
        System.out.print("Descrição: ");
        String descricao = teclado.nextLine();
        double precoCusto = lerNumero("Preço de Custo (ex: 5.0)", Double.class);
        double margemLucro = lerNumero("Margem de Lucro (ex: 0.2)", Double.class);
        
        Produto novo = null;
        if (tipo == 1) {
            novo = new ProdutoNaoPerecivel(descricao, precoCusto, margemLucro);
        } else if (tipo == 2) {
            System.out.print("Data de validade (dd/MM/yyyy): ");
            String dataStr = teclado.nextLine();
            java.time.LocalDate validade = java.time.LocalDate.parse(dataStr, java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            novo = new ProdutoPerecivel(descricao, precoCusto, margemLucro, validade);
        } else {
            System.out.println("Tipo inválido. Cancelando cadastro.");
            return;
        }
        
        if (produtos == null) {
            produtos = new Produto[10];
        } else if (quantProdutos >= produtos.length) {
            produtos = java.util.Arrays.copyOf(produtos, produtos.length + 10);
        }
        produtos[quantProdutos] = novo;
        quantProdutos++;
        System.out.println("Produto cadastrado com sucesso!");
    }

    static void salvarProdutos(String nomeArquivo) {
        try {
            java.io.FileWriter arquivo = new java.io.FileWriter(nomeArquivo);
            java.io.PrintWriter gravador = new java.io.PrintWriter(arquivo);
            
            gravador.println(quantProdutos);
            for (int i = 0; i < quantProdutos; i++) {
                gravador.println(produtos[i].gerarDadosTexto());
            }
            gravador.close();
            arquivo.close();
        } catch (java.io.IOException e) {
            System.out.println("Erro ao salvar o arquivo: " + e.getMessage());
        }
    }
    public static void fazBubble(Produto[] dados){
            BubbleSort<Produto> bolha = new BubbleSort<>();
            Produto[] vetorOrdenadoBolha = comparator != null ? bolha.ordenar(dados, comparator) : bolha.ordenar(dados);
            System.out.println("\nVetor ordenado método Bolha:");
            for (Produto p : vetorOrdenadoBolha) System.out.println(p);
            verificarSubstituicao(dados, vetorOrdenadoBolha);
        }

    public static void fazInsertion(Produto[] dados){
            InsertionSort<Produto> insertion = new InsertionSort<>();
            Produto[] vetorOrdenadoInsertion = comparator != null ? insertion.ordenar(dados, comparator) : insertion.ordenar(dados);
            System.out.println("\nVetor ordenado método insertion:");
            for (Produto p : vetorOrdenadoInsertion) System.out.println(p);
            verificarSubstituicao(dados, vetorOrdenadoInsertion);
    }

    public static void fazSelection(Produto[] dados){  
            SelectionSort<Produto> Selection = new SelectionSort<>();
            Produto[] vetorOrdenadoSelection = comparator != null ? Selection.ordenar(dados, comparator) : Selection.ordenar(dados);
            System.out.println("\nVetor ordenado método Selection:");
            for (Produto p : vetorOrdenadoSelection) System.out.println(p);
            verificarSubstituicao(dados, vetorOrdenadoSelection);
    }

    public static void fazMerge(Produto[] dados){  
            MergeSort<Produto> Merge = new MergeSort<>();
            Produto[] vetorOrdenadoMerge = comparator != null ? Merge.ordenar(dados, comparator) : Merge.ordenar(dados);
            System.out.println("\nVetor ordenado método Merge:");
            for (Produto p : vetorOrdenadoMerge) System.out.println(p);
            verificarSubstituicao(dados, vetorOrdenadoMerge);
    }

    public static void main(String[] args) {
        teclado = new Scanner(System.in);
        
        produtos = carregarProdutos(nomeArquivoDados);
        quantProdutos = 0;
        if (produtos != null) {
            while (quantProdutos < produtos.length && produtos[quantProdutos] != null) {
                quantProdutos++;
            }
        }
        embaralharProdutos();

        int opcao = -1;
        
        do {
            opcao = exibirMenuPrincipal();
            switch (opcao) {
                case 1 -> mostrarProduto(localizarProduto());
                case 2 -> filtrarPorPrecoMaximo();
                case 3 -> ordenarProdutos();
                case 4 -> embaralharProdutos();
                case 5 -> listarProdutos();
                case 6 -> cadastrarProduto();
                case 0 -> System.out.println("FLW VLW OBG VLT SMP.");
            }
            if(opcao != 0) pausa();
        }while (opcao != 0);
        salvarProdutos(nomeArquivoDados);
        teclado.close();
    }                        
}
