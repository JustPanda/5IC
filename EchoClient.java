package EchoClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class EchoClient {
    public static void main (String [] args){
        String indirizzoServer="localhost";
        Socket socket;
        BufferedReader input;
        PrintWriter output;
        String ip;
        String op;
        Scanner scanner=new Scanner(System.in);
        try {
            socket=new Socket(indirizzoServer,9999);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(),true);
            while(true)
            {
                op=scanner.nextLine();
                output.println(op);
                ip=input.readLine();
                System.out.println(ip);
            }
        } catch (IOException ex) {
            Logger.getLogger(EchoClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
