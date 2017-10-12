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
	private Pizzeria pizzeria;

	Task(Socket client, Pizzeria pizzeria)
	{
		this.client=client;
		this.pizzeria=pizzeria;
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
			out.println(pizzeria.getAnswers("introduzione", name));
			String msg;
			while(!((msg=in.readLine()).equals("EXIT")))
			{
				String s=pizzeria.getAnswers(msg, name);
				out.println(s);
				out.flush();
			}
			in.close();
			out.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
