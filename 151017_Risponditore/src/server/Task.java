package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class Task implements Runnable
{
	private String name;
	private Socket client;

	Task(Socket client)
	{
		this.client=client;
	}

	@Override
	public void run()
	{
		BufferedReader in;
		PrintWriter out;
		try{
			in=new BufferedReader(new InputStreamReader(client.getInputStream()));
			out=new PrintWriter(client.getOutputStream(), true);
			name=in.readLine();
			out.println("Hi "+name);
			String msg;
			while(!((msg=in.readLine()).equals("EXIT")))
			{
				System.out.println(name+": "+msg);
				out.println(msg);
			}
			in.close();
			out.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
