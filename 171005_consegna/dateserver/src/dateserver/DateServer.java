package dateserver;

import java.io.IOException;
import java.net.ServerSocket;

public class DateServer
{
	public static void main(String[] args)
	{
		final int PORT=9090;
		ServerSocket listener;
		System.out.println("Server online on: 127.0.0.1:"+PORT+"\nWaiting for client...");
		try {
			listener=new ServerSocket(PORT);
			while(true)
			{
				new ServerListener(listener.accept());
			}
		}catch(IOException ex){
			ex.printStackTrace();
		}
	}
}
