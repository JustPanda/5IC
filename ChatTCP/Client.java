
/* To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author davide
 */
public class Client implements Runnable
{

    private BufferedReader fromKeyboard;
    private BufferedReader fromServer;
    private PrintWriter toServer;
    private Socket mySocket;
    String nameServer = "localhost";
    int portServer = 8080;
    private String username;
    private char[] password;
    private boolean online = true;
    private static Thread listener;//thread che ascolta il server se arriva un messaggio lo mette a video

    public static void main(String[] args)
    {
	Client client = new Client();
    }

    public Client()
    {
	System.out.print("Inserire lo username: ");
	Scanner input = new Scanner(System.in);
	username = input.nextLine();
	this.connect();
	toServer.println(username);
	listener = new Thread(this);
	listener.start();
	this.sendMessage();
    }

    //initizialize the socket
    synchronized private Socket connect()
    {
	try
	{
	    fromKeyboard = new BufferedReader(new InputStreamReader(System.in));
	    mySocket = new Socket(nameServer, portServer);
	    toServer = new PrintWriter(mySocket.getOutputStream(), true);
	    fromServer = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));

	}
	catch (IOException ex)
	{
	    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
	}
	return mySocket;
    }

    @Override
    public void run()
    {
	try
	{
	    while (online)
	    {
		String message = fromServer.readLine();
		if (message != null)
		{
		    System.out.println(message);
		}

	    }
	}
	catch (IOException ex)
	{
	    try
	    {
		toServer.close();
		fromServer.close();
		listener = null;
	    }
	    catch (IOException ex1)
	    {
		Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex1);
	    }
	}

    }

    synchronized private void sendMessage()
    {
	String message;
	try
	{
	    while (online)
	    {
		message = fromKeyboard.readLine();
		if (message != null && username != null)
		{
		    toServer.print(username + ": ");
		    toServer.println(message);
		}

	    }
	}
	catch (IOException ex)
	{
	    try
	    {
		username = null;
		fromServer.close();
		toServer.close();
		fromKeyboard.close();
		mySocket.close();
	    }
	    catch (IOException ex1)
	    {
		Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex1);
	    }
	}
    }

    public Socket getSocket()
    {
	return this.mySocket;
    }

    public String getUsername()
    {
	return this.username;
    }

}
