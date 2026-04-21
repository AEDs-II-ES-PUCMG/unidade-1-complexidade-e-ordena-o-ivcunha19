import java.util.Comparator;

/**
 * Critério B - Volume Total de Itens (crescente).
 * Desempate 1: Data do Pedido.
 * Desempate 2: Código Identificador do pedido.
 */
public class ComparadorCriterioB implements Comparator<Pedido> {

    @Override
    public int compare(Pedido o1, Pedido o2) {
        if (o1.getFormaDePagamento() - o2.getFormaDePagamento() != 0) {
            return o1.getFormaDePagamento() - o2.getFormaDePagamento();
        }else if(o1.valorFinal() - o2.valorFinal() != 0){
            return (int)(o1.valorFinal() - o2.valorFinal());
        }else{
            return o1.hashCode() - o2.hashCode();
        }
    }
}
