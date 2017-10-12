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
        Model m;
        ArrayList<String> temp;
        public Server(Socket client, int ClientNumber)
        {
            this.ClientNumber = ClientNumber;
            this.client = client;
            m = new Model();
            temp = new ArrayList<>();
        }
        
        @Override
        public void run()
        {
            try
            {
                int i=0;
                in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                out = new PrintWriter(client.getOutputStream(), true);
                m.Setup();
                m.Build();
                m.Assign();
                temp.add(m.Intro());
                while(true)
                {
                    out.println(temp.get(i));
                    String answer = in.readLine();
                    String question = m.Decide(temp.get(i), answer);
                    temp.add(question);
                    i++;
                }
            }
            catch(Throwable e)
            {
                e.printStackTrace();
            }
        }
    }
}
