/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package date;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/**
 *
 * @author manue
 */
public class DateServer
{
    public static void main(String[] args) throws IOException 
    {
        ServerSocket listener = new ServerSocket(9090);
        System.out.println("running server");
        try 
        {
            while (true) 
            {
                Socket socket = listener.accept();
                try 
                {
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true); // with autoflush
                    out.println(new Date().toString());
                } 
                finally 
                {
                    // autocloseable ...
                    socket.close();
                }
            }
        }
        finally 
        {
            listener.close();
        }
    }
}
