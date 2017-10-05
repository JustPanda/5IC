/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author manue
 */
public class EchoClient 
{
    public static void main(String[] args) throws IOException
     {
        String serverAddress = "localhost";
        
        Socket socket = new Socket(serverAddress, 9999);
        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        Scanner scanner = new Scanner(System.in);
        while(true)
        {
            String post = scanner.next();
            out.println(post);
            String get= input.readLine();
            System.out.println(get);
        }
    }
}
