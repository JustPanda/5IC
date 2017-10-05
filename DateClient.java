package DateClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class DateClient {

    public static void main(String[] args) throws IOException {
        String serverAddress ="localhost";
        Socket s=new Socket(serverAddress,9090);
        BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));
        String output="Data ricevuta: ";
        while(output != null){
            System.out.print(output);
            output = input.readLine();
        }
    }
}
