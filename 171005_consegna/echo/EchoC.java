package echosc;

/**
 *
 * @author gioele
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class EchoC {

    public static void main(String[] args) throws IOException {
        String serverAddress = "localhost";
        Socket socket = new Socket(serverAddress, 9090);
        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        Scanner scanner = new Scanner(System.in);
        String strIn;
        while (true) {
            strIn = scanner.next();
            out.println(strIn);
            strIn = input.readLine();
            System.out.println(strIn);
        }
    }
}
