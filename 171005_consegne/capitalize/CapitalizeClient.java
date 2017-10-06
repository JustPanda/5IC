package capitalize;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class CapitalizeClient
{
    private final static String serverAddress = "127.0.0.1";
    private final static int PORT=1723;

    private BufferedReader in;
    private PrintWriter out;
    private BufferedReader stdIn;

    public void connectToServer() throws IOException
    {
        String userInput, response=null;
        Socket socket = new Socket(serverAddress, PORT);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
        stdIn = new BufferedReader(new InputStreamReader(System.in));

        for (int i = 0; i < 3; i++)
        {
            System.out.println(in.readLine());
        }
        while(true)
        {
            out.println(stdIn.readLine());
            response=in.readLine();
            if(response==null)
                break;
            System.out.println("\nserver responds: " + response);
        }
        System.out.println("\nConnection closed by server.");
    }

    public static void main(String[] args) throws Exception
    {
        CapitalizeClient client = new CapitalizeClient();
        client.connectToServer();
    }
}
