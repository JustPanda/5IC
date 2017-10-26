package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class User implements Runnable
{
	private Socket client;
	private Room room;

	User(Socket client, Room room)
	{
		this.client=client;
		this.room=room;
	}

	public void run()
	{
		BufferedReader in;
		PrintWriter out;
		try{
			boolean notExit=true;
			in=new BufferedReader(new InputStreamReader(client.getInputStream()));
			out=new PrintWriter(client.getOutputStream(), true);
			while(notExit)
			{
				String msg=in.readLine();

			}
			in.close();
			out.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
