package pizzeria;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import javax.swing.*;


public class Client
{
    public static void main(String[] args) throws IOException
    {
        JFrame f = new JFrame();
        
        
        Scanner in = new Scanner(System.in);
        String serverAddress = "localhost"; // server string
        Socket s = new Socket(serverAddress, 9898);
        BufferedReader input =
            new BufferedReader(new InputStreamReader(s.getInputStream()));
        //
        PrintWriter out = new PrintWriter(s.getOutputStream(), true);
        //
        
        String str = "ciao cliente, digita il tuo nome";
        System.out.println(str);
        do 
        { 
            str = in.nextLine();           
            out.println(str);
            System.out.println(input.readLine());

            chiediPizze(in, out, input);      
            chiediBibite(in, out, input);           
        } while(str != null);
        
    }
    
    public static void chiediPizze(Scanner in,PrintWriter out, BufferedReader input ) throws IOException
    {
        String pizz = "cdcd";
        while(!pizz.equals("STOP"))
        {
            pizz = in.nextLine();
            out.println(pizz);
            System.out.println(input.readLine());       
        }
    }
    
    public static void chiediBibite(Scanner in,PrintWriter out, BufferedReader input ) throws IOException
    {
          String bibite = "cdcd";
            while(!bibite.equals("STOP"))
            {
                bibite = in.nextLine();
                out.println(bibite);
                System.out.println(input.readLine());
                out.println(in.nextLine());
                System.out.println(input.readLine());
            }
    }
}
