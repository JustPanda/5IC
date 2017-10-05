package echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class ServerListener extends Thread
{
	private int clientIndex;
	private Socket client;

	ServerListener(Socket client, int clientIndex)
	{
		this.client=client;
		this.clientIndex=clientIndex;
		start();
	}

	@Override
	public void run()
	{
		BufferedReader in;
		PrintStream out;
		try{
			String msgToEcho;
			in=new BufferedReader(new InputStreamReader(client.getInputStream()));
			out=new PrintStream(client.getOutputStream());
			System.out.println("Client #"+clientIndex+" connected");
			while(!(msgToEcho=in.readLine()).equals("EXIT"))
			{
				System.out.println("Message to echo \""+msgToEcho+"\"");
				out.println("ECHO "+msgToEcho);
			}
			System.out.println("Connection end with client #"+clientIndex);
		}catch(IOException ex){
			ex.printStackTrace();
		}
	}
}
