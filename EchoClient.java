mport java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EchoClient {

    public static void main(String[] args) {
        String host = "localhost";
        String risposta;
        String scritto;
        Scanner scanner = new Scanner(System.in);
        try {

            Socket socket = new Socket(host, 9999);
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
            while (true) {
                scritto = scanner.nextLine();
                output.println(scritto);
                risposta = input.readLine();
                System.out.println(risposta);
            }
        } catch (IOException ex) {
            Logger.getLogger(EchoClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
