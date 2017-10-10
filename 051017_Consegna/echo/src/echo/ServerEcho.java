package echo;

import java.io.*;
import java.net.*;

public class ServerEcho
{
	public static void main(String args[])
	{
		final int PORT=9999;
		int clientIndex=0;
		ServerSocket echoServer = null;
		String line;
		BufferedReader is;
		PrintStream os;
		Socket clientSocket = null;
		System.out.println("Server online on 127.0.0.1:"+PORT+"\nWaiting for client...");
		try {
			echoServer = new ServerSocket(PORT);
			while(true)
			{
				new ServerListener(echoServer.accept(), clientIndex++);
			}
		}catch(IOException ex){
			ex.printStackTrace();
		}
	}
}
