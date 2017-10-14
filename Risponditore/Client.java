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
    
    public static void main(String[] args) throws Throwable
    {
        try
        {
            Scanner input = new Scanner(System.in);
            Socket socket = new Socket("127.0.0.1", 9090);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            while(true)
            {
                String res = in.readLine();
                if(res.toLowerCase() != "Arrivederci".toLowerCase() || res.toLowerCase().split(" ")[0] != "Lei".toLowerCase())
                {
                    System.out.println(res);
                    String s = input.nextLine();
                    out.println(s);
                }
                else
                {
                    System.out.println(res);
                    break;
                }
            }
			socket.shutdownInput();
			socket.shutdownOutput();
			socket.close();
			in.close();
			out.close();
			input.close();
			System.exit(0);
        }
        catch(Throwable e)
        {
            System.exit(0);
        }
    }
}
