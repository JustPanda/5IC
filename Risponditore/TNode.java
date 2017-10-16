package risponditoreautomatico;

/**
 *
 * @author marco
 */
public class TNode {

    public String element;
    public Risposte r;

    public TNode(String element, Risposte r) {
        this.element = element;
        this.r = r;
    }

    public String getElement() {
        return element;
    }

}
