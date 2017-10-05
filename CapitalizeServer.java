import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class CapitalizeServer {

    public static void main(String[] args) throws Exception {

        System.out.println("The capitalization server is running.");
        int clientNumber = 0;
        ServerSocket listener = new ServerSocket(9898);
        try {
            while (true) {
                new Capitalizer(listener.accept(), clientNumber++).start();
            }
        }
        finally {
            listener.close();
        }
    }

    private static class Capitalizer extends Thread {

        private Socket socket;
        private int clientNumber;

        public Capitalizer(Socket socket, int clientNumber) {

            this.socket = socket;
            this.clientNumber = clientNumber;
            myLog("New connection with client #" + clientNumber + " at " + socket);
            
        }

        public void run() {

            try {

                BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                out.println("Hello, you are client #" + clientNumber + ".");
                out.println("Enter a line with only a period to quit\n");

                while (true) {

                    String input = in.readLine();
                    if (input == null || input.equals(".")) {
                        out.println("Close connection for client #" + clientNumber + ".");
                        socket.close();
                        break;
                    }
                    out.println(input.toUpperCase());
                    System.out.println("Il client n°" + clientNumber + " ha richiesto la modifica della stringa \"" + input + "\" nella data: " + new Date().toString());

                }
            } catch (IOException e) {
                myLog("Error handling client# " + clientNumber + ": " + e);
                
            } 
            finally {
                try {
                    socket.close();
                } catch (IOException e) {
                
                }
                myLog("Connection with client# " + clientNumber + " closed");
            }
        }

        private void myLog(String message) {

            System.out.println(message);

        }
    }
}
