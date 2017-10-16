package risponditore;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author tommasoquinto
 */
public class Client extends Thread {

    private final static String ADDRESS = "127.0.0.1";
    private final static int PORT = 3333;
    String com = "";
    String doo = "";
    private Socket server;
    BufferedReader in;
    PrintWriter out;

    Client() {
        try {
            server = new Socket(ADDRESS, PORT);
            in = new BufferedReader(new InputStreamReader(server.getInputStream()));
            out = new PrintWriter(server.getOutputStream(), true);
            this.start();

        } catch (IOException e) {
        }
    }

    @Override
    public void run() {
        Scanner input = new Scanner(System.in);
        try {
             System.out.println("chi sei?");
             String nick="";
             out.println(nick=input.nextLine());
             System.out.println("Ciao "+nick+" Benvenuto alla Trattoria,come posso esserle utile? 1(menu) 2(piatti) 3(bevande)");
            while (!com.equals("fine")) {
                out.println(input.nextLine());
                System.out.println(doo = in.readLine());
                com=doo;
            }

        } catch (IOException e) {
        }
    }

    public static void main(String[] args) {
        new Client();
    }
}
