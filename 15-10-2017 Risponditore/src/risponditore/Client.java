/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package risponditore;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;
import java.util.Scanner;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

/**
 *
 * @author manue
 */
public class Client
{

    /**
     * @param args the command line arguments
     */
    public static String host = "localhost";
    public static int authPort = 8080;

    public static void main(String[] args) throws IOException
    {
        boolean isExit = false;
        /* SSLSocketFactory sslsocketfactory;
        SSLSocket sslsocket;
        
        sslsocketfactory= (SSLSocketFactory) SSLSocketFactory.getDefault();
        sslsocket= (SSLSocket) sslsocketfactory.createSocket(host, authPort);
        //sslsocket.setEnabledProtocols(new String[]{"TLSv1.2"}); */

        Socket s = new Socket("localhost", 8080);

        PrintWriter writer = new PrintWriter(s.getOutputStream(), true);
        BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream()));

        Scanner scanner = new Scanner(System.in);
        while (!isExit)
        {
            String output = scanner.nextLine();
            if (output.toLowerCase() == "exit")
            {
                writer.println(output);
                isExit = true;
            } 
            else
            {
                writer.println(output);
                String input = reader.readLine();
                System.out.println(input);
            }
            
        }
    }

}
