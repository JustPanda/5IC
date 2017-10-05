package dateserver;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

public class ServerListener extends Thread
{
	private Socket client;

	ServerListener(Socket client)
	{
		this.client=client;
		start();
	}

	@Override
	public void run()
	{
		PrintWriter out;
		System.out.println("Client connected");
		try{
			out=new PrintWriter(client.getOutputStream(), true);
			out.println(new Date().toString());
		}catch(IOException ex){
			ex.printStackTrace();
		}finally {
			try{
				System.out.println("Client connection end");
				client.close();
			}catch(IOException ex){
				ex.printStackTrace();
			}
		}

	}
}
