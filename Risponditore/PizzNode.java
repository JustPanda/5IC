package pizzeria;

import java.util.ArrayList;

/**
 *
 * @author gioele
 */
public class PizzNode <E> {
    public String domanda;
    public PizzNode<String> parent;

    public PizzNode(PizzNode parent,String domanda) {
        this.domanda = domanda;
        this.parent=parent;
    }
}
