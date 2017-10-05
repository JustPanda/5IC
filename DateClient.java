/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consegnadateclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author davide.cesare
 */
public class DateClient
{
 public static void main(String [] args)
 {
     try
     {
         String host = "localhost";
         Socket socket = new Socket(host,9090);
         BufferedReader buff = new BufferedReader(new InputStreamReader(socket.getInputStream()));
         String data = buff.readLine();
         System.out.println(data);
         
     }
     catch (IOException ex)
     {
         Logger.getLogger(DateClient.class.getName()).log(Level.SEVERE, null, ex);
     }
     
 }   
}
