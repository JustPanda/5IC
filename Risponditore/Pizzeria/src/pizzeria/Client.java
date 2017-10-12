package pizzeria;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.*;
import java.net.*;

public class Client 
{
    BufferedReader in;
    PrintWriter out;
    Scanner input;
    
    public void Connect()
    {
        try
        {
            input = new Scanner(System.in);
            Socket socket = new Socket("127.0.0.1", 9090);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            while(true)
            {
                System.out.println("Inserisci richiesta");
                String s = input.nextLine();
                out.println(s);
                String res = in.readLine();
                System.out.println(res);
            }
        }
        catch(Throwable e)
        {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args)
    {
        Client client = new Client();
        client.Connect();
    }
}
