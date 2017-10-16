package risponditoreautomatico;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author marco
 */
public class Client {

    public static void main(String[] args) throws IOException {
        Scanner input = new Scanner(System.in);
        final int port = 4455;
        Socket serv = new Socket("localhost", port);
        BufferedReader in = new BufferedReader(new InputStreamReader(serv.getInputStream()));
        PrintWriter out = new PrintWriter(serv.getOutputStream(), true);
        String str = "";
        while (true) {
            str = input.nextLine();
            out.println(str);
            System.out.println(in.readLine());
        }
    }

}
