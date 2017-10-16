/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 *
 * @author manue
 */
public class Server
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException
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

    // the socket where to listen/talk
    Socket socket;
    ObjectInputStream input;
    ObjectOutputStream output;
    // my unique id (easier for deconnection)
    int id;
    // the User of the Client
    User user;
    // the only type of message a will receive
    Message m;
    // the date I connect
    String date;

    public ClientConnection(Socket socket, int index) throws IOException
    {
        this.id = index;
        this.socket = socket;
        this.output = new ObjectOutputStream(socket.getOutputStream());
        this.input = new ObjectInputStream(socket.getInputStream());
    }

    @Override
    public void run()
    {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        Receiver receiver = new Receiver(input);
        Sender sender = new Sender(output);
        executor.execute(receiver);
        executor.execute(sender);
    }
}

class Receiver implements Runnable
{

    ObjectInputStream receiver;
    
    public Receiver(ObjectInputStream receiver)
    {
        this.receiver = receiver;
    }

    @Override
    public void run()
    {

    }

}

class Sender implements Runnable
{
    ObjectOutputStream sender;
    
    public Sender(ObjectOutputStream sender)
    {
        this.sender = sender;
    }

    @Override
    public void run()
    {

    }

}
