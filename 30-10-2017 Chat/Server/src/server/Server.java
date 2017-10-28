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
        ServerSocket s = new ServerSocket(8080);

        /*Socket socket = (Socket) s.accept();
        System.out.println("Connesso"); */
 /*   PrintWriter out = new PrintWriter(socket.getOutputStream());
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        while(true)
        {
            //out.println("GESUUUUUUU");
            System.out.println("Ho ricevuto: " + in.readLine()); 
        } */
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
    BufferedReader input;
    PrintWriter output;
    // my unique id (easier for deconnection)
    int id;
    // the User of the Client
    User user;
    // the only type of message a will receive
    ArrayList<Message> messages = new ArrayList<Message>();
    // the date I connect
    String date;

    public ClientConnection(Socket socket, int index) throws IOException
    {
        this.id = index;
        this.socket = socket;
        this.output = new PrintWriter(socket.getOutputStream());
        this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    @Override
    public void run()
    {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        Receiver receiver = new Receiver(this, input);
        Sender sender = new Sender(this, output);
        executor.execute(receiver);
        executor.execute(sender);
    }
}

class Receiver implements Runnable
{
ClientConnection connection;
    BufferedReader receiver;

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
                System.out.println("Ho ricevuto: " + receiver.readLine());
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

    public Sender(ClientConnection connection, PrintWriter sender)
    {
        this.sender = sender;
    }

    @Override
    public void run()
    {
        while (true)
        {
            String date = cal.get(Calendar.HOUR) + ":" + cal.get(Calendar.MINUTE) + "," + cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.MONTH) + "/" + cal.get(Calendar.YEAR);
            String text = scanner.nextLine();
            String message = "{" + "Type:" + "\"" + type + "\"" + ",Text:" + "\"" + text + "\"" + ",Date:" + "\"" + date + "\"" + "}";
            sender.println(message + "\n");
            System.out.println("Ho inviato: " + message);
        }

    }

}
