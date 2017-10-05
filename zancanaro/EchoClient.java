/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esercizioecho;

import java.io.*;
import java.net.*;
import java.lang.Thread;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.DataOutputStream;
import java.net.Socket;
import java.io.*;
import java.util.Scanner;

public class EchoClient 
{
    public static void main(String[] args) throws IOException
    {
        Socket socket = new Socket("localhost", 9999);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintStream out = new PrintStream(socket.getOutputStream());
        Scanner input = new Scanner(System.in);

        while(true)
        {
            System.out.println("inserisci la stringa");
            String str = input.nextLine();
            out.println(str);
            String res = in.readLine();
            System.out.println("Ricevuto: " + res);
        }
      
    }
}
