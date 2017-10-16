package risponditoreautomatico;

/**
 *
 * @author marco
 */
public class Albero {

    TNode nodo, nodo1, nodo2, nodo3, nodo4;

    public Albero() {
        nodo = new TNode("Di che colore era il cavallo bianco di Napoleone?", new Risposte("verde", "blu", "bianco"));
        nodo1 = new TNode("Quante T ci sono nella parola 'tuttofare'?", new Risposte("6", "3", "2"));
        nodo2 = new TNode("Quanto fa 15 + 18?", new Risposte("36", "33", "34"));
        nodo3 = new TNode("In che anno nacque gesù?", new Risposte("0", "1", "-1"));
        nodo4 = new TNode("In che anno Cristoforo Colombo scoprì l'America?", new Risposte("1487", "1494", "1492"));
    }

}
