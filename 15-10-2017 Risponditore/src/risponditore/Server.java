/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package risponditore;

import java.net.*;
import java.util.*;
import java.util.concurrent.*;
import java.io.*;

/**
 *
 * @author manue
 */
public class Server
{

    public static void main(String[] args) throws Throwable
    {

        ServerSocket s = new ServerSocket(8080);
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        int clientIndex = 0;
        while (clientIndex <= 1000)
        {
            clientIndex++;
            Socket socket = (Socket) s.accept();
            ClientConnection client = new ClientConnection(socket, clientIndex);
            System.out.println("Connesso al client n°" + clientIndex);
            executor.execute(client);
        }
        executor.shutdown();
        s.close();
    }
}

class ClientConnection implements Runnable
{

    Socket socket;
    int index;

    public ClientConnection(Socket socket, int index) throws IOException
    {
        this.socket = socket;
        this.index = index;
    }

    @Override
    public void run()
    {

        try
        {
            boolean isExit = false;
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Risponditore risponditore = new Risponditore();
            writer.println(risponditore.Root.Question);
            Node temp = risponditore.Root;
          /*  while(temp.Children.get(0)!=null)
            {
                String answers ="\n";
                for(int i=0; i<temp.Answers.length; i++)
                {
                    answers+=temp.Answers[i] + "\n";
                }
                System.out.println(temp.Question + answers);
                temp = temp.Children.get(0);
            } */
            while (!isExit)
            {

                String input = reader.readLine();
                if (input.toLowerCase().equals("exit"))
                {
                    System.out.println("Ho ricevuto exit||||||||||||||||");
                    writer.println("Uscita...");
                    isExit = true;
                } else
                {
                    System.out.println("Ho ricevuto " + input);
                    String output = risponditore.Exe(input);
                    if(output==null)
                    {
                        isExit=true;
                    }
                    else
                    {
                       writer.println(output); 
                    }                    
                }

            }

        } catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
