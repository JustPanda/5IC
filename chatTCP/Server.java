package chattcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {

    public static void main(String[] args) {
        int porta = 9999;
        Socket client = null;
        DatabaseSQL database = null;
        String username = null;
        String password = null;
        try {
            ServerSocket server = new ServerSocket(porta);

            boolean register = false;
            PrintWriter out;
            BufferedReader in;
            String scelta;
            ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
            database = new DatabaseSQL();
            database.newDatabase("database.sqlite");
            database.createDati("database.sqlite");
            Chat chat = new Chat(database);

            while (true) {
                client = server.accept();
                chat.add(client);
                out = new PrintWriter(client.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                while (!register) {
                    out.println("1) Registrazione\n2) Accedi");
                    //out.println("2) Accedi");
                    //out.print("La tua scelta: ");
                    scelta = in.readLine();

                    if (scelta.equalsIgnoreCase("1")) {
                        out.println("Inserire username.");
                        username = in.readLine();
                        out.println("Inserire password.");
                        password = in.readLine();
                        database.addUser(username, password, client, "database.sqlite");

                    }

                    if (scelta.equalsIgnoreCase("2")) {
                        out.println("Inserire username.");
                        username = in.readLine();
                        out.println("Inserire password.");
                        password = in.readLine();
                        register = database.checkInfo(username, password, client, "database.sqlite");
                        if (!register) {
                            out.println("Credenziali errate");
                        } else {
                            out.println("Credenziali accettate");
                        }
                    }
                }
                register = false;
                executor.execute(new Execute(client, username, chat, database));

            }
        } catch (IOException ex) {
            try {
                System.out.println(client.getLocalSocketAddress().toString() + " è uscito");
                database.setOffline(username, password, client, "database.sqlite");
                client.close();
                client = null;
            } catch (IOException ex1) {
            }
        }

    }

}
