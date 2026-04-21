import java.util.Comparator;


public class ComparadorCriterioC implements Comparator<Pedido> {

    @Override
    public int compare(Pedido o1, Pedido o2) {
        if (o1.ticketMedio() - o2.ticketMedio() != 0) {
            return (int)(o1.ticketMedio() - o2.ticketMedio());
        }else if(o1.valorFinal() - o2.valorFinal() != 0){
            return (int)(o1.valorFinal() - o2.valorFinal());
        }else{
            return o1.hashCode() - o2.hashCode();
        }
    }
}
