import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


public class Client 
{
    public static void main(String[] args) throws IOException
    {
        String serverAddress = "127.0.0.1";
        Socket socket = new Socket(serverAddress, 8080);
        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        Scanner scanner = new Scanner(System.in);

        String text="";
        
        while(text!="null")
        {
           text = scanner.next();
           out.println(text);
           text= input.readLine();
           System.out.println(text);
        }
    }
}