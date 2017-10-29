/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        Mixer mixer = new Mixer();
        mixer.Start();      

    }

}

class Mixer 
{
    ArrayList<ClientConnection> connections = new ArrayList<ClientConnection>();
    private ArrayList<Message> messages = new ArrayList<Message>();
    
    public void Start() throws IOException
    {
        ServerSocket s = new ServerSocket(8080);
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        int clientIndex = 0;
        while (clientIndex <= 1000)
        {
            clientIndex++;
            Socket socket = (Socket) s.accept();
            ClientConnection client = new ClientConnection(socket, this);
            System.out.println("Connesso al client n°" + clientIndex);
            connections.add(client);
            executor.execute(client);
        }
        executor.shutdown();
        s.close();
    }
    
    public void UpdateMessages(Message message)
    {
        this.messages.add(message);
        for(int i=0; i<connections.size(); i++)
        {
            ClientConnection c = connections.get(i);
            if(!message.User.equals(c.user.Username))
            {
                c.executor.execute(new Sender(c, c.output, message));
            }
        }
    }
}

class ClientConnection implements Runnable
{
    Mixer mixer;
    Socket socket;
    ExecutorService executor;
    BufferedReader input;
    PrintWriter output;
  //  int id;

    public User user;


    public ClientConnection(Socket socket, Mixer mixer) throws IOException
    {
        //this.id = index;
        this.mixer = mixer;
        this.socket = socket;
        this.output = new PrintWriter(socket.getOutputStream());
        this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    @Override
    public void run()
    {
        executor = Executors.newFixedThreadPool(2);
        Receiver receiver = new Receiver(this, input);
        executor.execute(receiver);
    }
}

class Receiver implements Runnable
{
    ClientConnection connection;
    BufferedReader receiver;
    
    GregorianCalendar cal = new GregorianCalendar();

    public Receiver(ClientConnection connection, BufferedReader receiver)
    {
        this.receiver = receiver;
    }

    @Override
    public void run()
    {
        try
        {
            while (true)
            {
                String s = receiver.readLine();
                System.out.println("Ho ricevuto: " + receiver.readLine());
                
                //parse
                Message message = new Message();
                message.User= "manuelelucchi";
                message.Date = cal.get(Calendar.HOUR) + ":" + cal.get(Calendar.MINUTE) + "," + cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.MONTH) + "/" + cal.get(Calendar.YEAR);
                message.Text = "CIaooooooooo";
                connection.mixer.UpdateMessages(message);
            }

        } catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }

}

class Sender implements Runnable
{

    ClientConnection connection;
    PrintWriter sender;
    GregorianCalendar cal = new GregorianCalendar();
    Scanner scanner = new Scanner(System.in);
    String type = "server";
    Message message;

    public Sender(ClientConnection connection, PrintWriter sender, Message message)
    {
        this.sender = sender;
        this.message = message;
    }

    @Override
    public void run()
    {
        while (true)
        {
 
            String output = "{" + "User:" + "\"" + message.User + "\"" + ",Text:" + "\"" + message.Text + "\"" + ",Date:" + "\"" + message.Date + "\"" + "}";
            sender.println(output + "\n");
            System.out.println("Ho inviato: " + output);
        }

    }

}
