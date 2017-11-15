/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

public class Lettore extends Thread
{
    
   
    Socket s;  
    ObservableList<String> l;
    LoginFXMLController controller;
  
    public Lettore(Socket s, LoginFXMLController controller) 
    {
        this.s=s;
        this.controller = controller;
        this.start();
    }
    
    @Override
    public void run()
    {
        try 
        {
            l = FXCollections.observableArrayList();
            System.out.println("entra nel run");
            
            String str = Client.input.readLine();
            System.out.println(str);
            if(str.equals("MANDALISTA"))
            {
                System.out.println("entra nell'if");
                while(!str.equals("STOP"))
                {
                    System.out.println("entra nel while");
                    try 
                    {
                        str = Client.input.readLine();
                        if(!str.equals("STOP"))
                        {
                            l.add(str);
                            System.out.println(str);
                        }
                    }
                    catch (IOException ex)   
                    {
                        System.out.println("ROGTREHBVIRGTEB");
                    }
                }
                
            }
            Client.lp = l;
            System.out.println("l: "+l);
            System.out.println("lista dal client: "+Client.lp);
            
            while(true)
            {
                System.out.println("aspetta altro");
                str = Client.input.readLine();
                if(str.equals("CHIEDIMESSAGGI"))
                {
                    System.out.println("str: "+str);
                    while(!str.equals("STOP"))
                    {
                        str = Client.input.readLine();
                        if(!str.equals("STOP"))
                        {
                            Client.messaggiR.add(str);
                            System.out.println(str);
                        }

                    }
                }
                else
                {
                    String messaggio = "";
                    if(str.equals("INVIAMESSAGGIO"))
                    {
                        System.out.println(str);
                        messaggio = Client.input.readLine();
                        
                        controller.aggiungiMessaggio(messaggio);
                        System.out.println(messaggio);
                        
                        
                    }
                }

            }
        } 
        catch (IOException ex)
        {
            Logger.getLogger(Lettore.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}

