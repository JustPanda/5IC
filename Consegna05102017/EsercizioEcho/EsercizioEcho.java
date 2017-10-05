/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esercizioecho;

/**
 *
 * @author samue
 */
import java.io.*;
import java.net.*;
import java.lang.Thread;
import java.util.Date;

public class EsercizioEcho {
    public static void main(String args[]) {
        
        ServerSocket echoServer = null;
        String line;
        BufferedReader is;
        PrintStream os;
        
        Socket clientSocket = null;
        try {
            echoServer = new ServerSocket(9999);
        } catch (IOException e) {
            System.out.println(e);
        }   
        try {
            clientSocket = echoServer.accept();
            is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            os = new PrintStream(clientSocket.getOutputStream());
            while (true) {
                 line = is.readLine();
                 System.out.println("Ricevuto " + line + " in data " + new Date().toString());
                 Thread.sleep(1000);
                 os.println("ECHO " + line); 
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        catch (InterruptedException ie) {
            System.out.println(ie);
        }
    }
}
