package risponditore;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author tommasoquinto
 */
class Start extends Thread {

    private BufferedReader in;
    private PrintWriter out;
    private ServerSocket server;
    private Socket client;
    Decisioni d;
    String decision;
    String nickname="";
    String number = "";
    int ok = 0;
    static boolean ordinato = false;

    Start(Socket client) {
        try {
            this.client = client;
            //server = new ServerSocket(3333);
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new PrintWriter(client.getOutputStream(), true);

        } catch (IOException e) {
        }

        this.start();

    }

    @Override
    public void run() {
        d = new Decisioni("file");
        d.add();
        try {
            while (true) {
                decision = in.readLine();

                switch (decision) {
                    case "1": {
                        number = "1";
                        out.println("il menù di oggi prevede: " + d.print(number) + " cosa desidera? 2(piatti) 3(bevande)");
                        break;

                    }
                    case "2": {
                        number = "2";
                        out.println("quale piatto si desidera ordinare?");
                        break;

                    }
                    case "3": {
                        number = "3";
                        out.println("quale bevanda si desidera ordinare?");
                        break;
                    }
                    case "no": {

                        if (ordinato == false) {

                            out.println("non hai ordinato niente: 1 (menù) 2(piatti) 3(bevande)");

                            break;
                        } else {

                            out.println(totale());
                            out.println("fine");
                            break;
                        }
                    }
                    case "si": {
                        if (ordinato == false) {
                            out.println("non hai ancora ordinato come vuoi procedere? 1 (menù) 2(piatti) 3(bevande)");
                            break;
                        } else {
                            out.println("1(menù) 2(piatti) 3(bevande)");
                            break;
                        }
                    }
                    default: {
                        if (!number.equals("")) {
                            String help = decision;
                            int j = Integer.parseInt(number) - 1;
                            int appoggio = 0;

                            for (int i = 0; i < d.root.children.get(j).children.size(); i++) {
                                if (d.root.children.get(j).children.get(i).element.contains(help)) {
                                    ok = 1;
                                    appoggio = i;
                                    break;
                                }
                            }
                            if (ok == 1) {
                                ordinato = true;
                                out.println("desidera altro? (si/no)");
                                cost(d.root.children.get(j).children.get(appoggio).element);
                                ok = 0;
                                break;
                            } else {
                                out.println("non esiste,riprova 1(menù) 2(piatti) 3(bevande)");
                                break;
                            }

                        }
                    }
                }
            }

        } catch (IOException ex) {
            try {
                in.close();
                out.close();
            } catch (IOException ex1) {
            }
        }

    }

    public void cost(String help) {
        String numb = "";
        String appoggio;
        for (int i = help.length() - 1; i > 0; i--) {
            if (help.charAt(i) == ' ') {
                break;
            } else {
                numb += help.charAt(i);
            }
        }
        appoggio = help.replace(numb, "");
        numb = new StringBuilder(numb).reverse().toString();
        d.pietanze.add(appoggio);
        d.prezzi.add(Double.parseDouble(numb));

    }

    public String totale() {
        double num = 0;
        String conto = "";
        for (int i = 0; i < d.prezzi.size(); i++) {
            num += d.prezzi.get(i);
            conto += d.pietanze.get(i) + ", ";
        }

        return "hai ordinato: " + conto + " " + "totale: " + num + "€ ";

    }

}

public class Server {

    public static void main(String[] args) throws IOException {
        System.out.println("server on");
        ServerSocket listener = new ServerSocket(3333);
        ExecutorService executor = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 500; i++) {
            Runnable worker = new Start(listener.accept());
            executor.execute(worker);
        }
        executor.shutdown();
    }
}
