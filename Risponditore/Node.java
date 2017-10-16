package risponditore;

import java.util.ArrayList;

/**
 *
 * @author tommasoquinto
 */
public class Node<E> {

    public String element;
    public Node<String> parent;
    public ArrayList<Node<String>> children = new ArrayList<>();

    public Node(String element) {
        this.element = element;
    }

}
