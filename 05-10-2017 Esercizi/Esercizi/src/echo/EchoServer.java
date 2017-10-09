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
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author manue
 */
public class EchoServer 
{
    public static void main(String args[]) {
        
        ServerSocket echoServer = null;
        String line;
        BufferedReader is;
        PrintStream os;
        
        Socket clientSocket = null;
        try 
        {
            echoServer = new ServerSocket(9999);
            System.out.println("Use \"127.0.0.1 9999\"");
        } 
        catch (IOException e) 
        {
            System.out.println(e);
        }   
        try 
        {
            clientSocket = echoServer.accept();
            is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            os = new PrintStream(clientSocket.getOutputStream());
            while (true) {
                 line = is.readLine();
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
