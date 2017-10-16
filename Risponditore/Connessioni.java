package risponditoreautomatico;

import java.io.*;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author marco
 */
public class Connessioni extends Thread {

    Socket client;
    BufferedReader in;
    PrintWriter out;
    Albero alb;
    Risposte risp;
    TNode node = new TNode("", risp);

    public Connessioni(Socket client) {
        alb = new Albero();
        risp = new Risposte("", "", "");
        //node = alb.nodo;
        this.client = client;
        try {
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())), true);
        } catch (IOException e) {
        }
    }

    @Override
    public void run() {
        int errori = 3;
        String send = "";
        String risposta;
        try {
            risposta = in.readLine();
        } catch (IOException ex) {
        }
        node = alb.nodo;
        while (true) {
            try {
                out.println(send + "- " + node.element + "  Risposte: " + scriviRisposte(node) + ".  ERRORI RIMANENTI: " + errori);
                risposta = in.readLine();
                send = "";

                if (risposta.equals(node.r.r3) && risposta.equals("bianco")) {
                    node = alb.nodo1;
                    continue;
                } else if (risposta.equals(node.r.r2) && risposta.equals("3")) {
                    node = alb.nodo2;
                    continue;
                } else if (risposta.equals(node.r.r2) && risposta.equals("33")) {
                    node = alb.nodo3;
                    continue;
                } else if (risposta.equals(node.r.r1) && risposta.equals("0")) {
                    node = alb.nodo4;
                    continue;
                } else if (risposta.equals(node.r.r3) && risposta.equals("1492")) {
                    out.println("Complimenti. Hai completato tutte le domande.");
                    in.close();
                    out.close();
                    break;
                } else {
                    send = "Sbagliato. Ritenta.  ";
                    errori--;
                    node = alb.nodo;
                }
                if (errori == 0) {
                    out.println("Hai terminato i tentativi.");
                    in.close();
                    out.close();
                }
            } catch (IOException ex) {
                try {
                    in.close();
                    out.close();
                } catch (IOException ex1) {
                }
            }
        }
    }

    public String scriviRisposte(TNode nodo) {
        String risposte = "";
        risposte += " " + nodo.r.r1;
        risposte += " " + nodo.r.r2;
        risposte += " " + nodo.r.r3;
        return risposte;
    }

}
