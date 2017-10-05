package echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author marco
 */
public class Echo {

    public static void main(String[] args) throws IOException {
        String ipServer = "127.0.0.1";
        Scanner scanner = new Scanner(System.in);
        Socket socket = new Socket(ipServer, 1122);
        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);        
        while (true) {
            String message = scanner.next();
            out.println(message);
            String answer = input.readLine();
            System.out.println(answer);
        }
    }

}
