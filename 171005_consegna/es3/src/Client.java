import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client
{
     public static void main(String[] args) throws IOException
     {
        String serverAddress = "127.0.0.1";   
        Socket socket = new Socket(serverAddress, 9090);
        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String s = "DATA RECEIVED:";
        while(s != null) 
        {
            System.out.println(s);
            s = input.readLine();
        }
    }
}