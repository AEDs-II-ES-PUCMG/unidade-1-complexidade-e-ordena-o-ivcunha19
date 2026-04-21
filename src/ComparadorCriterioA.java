import java.util.Comparator;

/**
 * Critério A - Valor Final do Pedido (crescente).
 * Desempate 1: Volume Total de Itens (quantProdutos).
 * Desempate 2: Código Identificador do primeiro item do pedido.
 */
public class ComparadorCriterioA implements Comparator<Pedido> {

    @Override
    public int compare(Pedido o1, Pedido o2) {
        if ((o1.valorFinal() - o2.valorFinal()) > 0) {
            return 1;
        }else if((o1.valorFinal() - o2.valorFinal() < 0)){
                return -1;
        }else if (o1.getQuantosProdutos() - o2.getQuantosProdutos() != 0) {
                return o1.getQuantosProdutos() - o2.getQuantosProdutos();
        }else{
            return o1.getIdPrimeiroProduto() - o2.getIdPrimeiroProduto();
        }
    }
}
