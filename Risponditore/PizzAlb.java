package pizzeria;

/**
 *
 * @author gioele
 */
public class PizzAlb {
    //sotto albero sinistro
    public PizzNode node=new PizzNode(null,"Che pizza vuoi?");
    public PizzNode node1=new PizzNode(node,"Ne vuoi un'altra?");
    public PizzNode node2=new PizzNode(node1,"Che bibita vuoi?");
    public PizzNode node3=new PizzNode(node2,"Ne vuoi un'altra?");
    //sotto albero destro
    public PizzNode node4=new PizzNode(node,"Che bibita vuoi?");
    public PizzNode node5=new PizzNode(node4,"Ne vuoi un'altra?");
}
