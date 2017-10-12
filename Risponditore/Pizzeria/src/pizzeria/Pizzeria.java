package pizzeria;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Pizzeria 
{
    public static void main(String[] args) throws Throwable
    {
        System.out.println("Server running...");
        int Client = 0;
        ExecutorService executor = Executors.newCachedThreadPool();
        ServerSocket server = new ServerSocket(9090);
        try
        {
            while(true)
            {
                Server s = new Server(server.accept(), Client++);
                executor.execute(s);
            }
        }
        catch(Throwable e)
        {
            e.printStackTrace();
        }
    }
    
    static class Server extends Thread
    {
        Socket client;
        int ClientNumber;
        BufferedReader in;
        PrintWriter out;
        public Server(Socket client, int ClientNumber)
        {
            this.ClientNumber = ClientNumber;
            this.client = client;
        }
        
        @Override
        public void run()
        {
            try
            {
                in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                out = new PrintWriter(client.getOutputStream(), true);
                while(true)
                {
                    String s = in.readLine();
                    out.println("Server response: " + s);
                }
            }
            catch(Throwable e)
            {
                e.printStackTrace();
            }
        }
    }
}
