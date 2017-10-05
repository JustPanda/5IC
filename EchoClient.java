/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package echoclient;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author davide.cesare
 */
public class EchoClient
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        try
        {
            String host = "localhost";
            Socket client = new Socket(host,9999);
           DataOutputStream data = new DataOutputStream(client.getOutputStream());
            System.out.println("Inserire la stringa");
           Scanner input= new Scanner(System.in);
           String echo = input.nextLine();
           data.writeUTF(echo);
        }
        catch (IOException ex)
        {
            Logger.getLogger(EchoClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
