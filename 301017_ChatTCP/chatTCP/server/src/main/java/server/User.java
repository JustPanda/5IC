package server;

import jdk.nashorn.internal.parser.JSONParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.function.Function;

class User implements Runnable
{
	private Socket client;
	private BufferedReader in;
	private PrintWriter out;
	private SQLiteJDBC database;
	private Room room;
	private HashMap<String, Function<Room, Void>> phases=new HashMap<>();

	User(Socket client, SQLiteJDBC database, Room room)
	{
		this.client=client;
		this.database=database;
		this.room=room;
		try{
			this.in=new BufferedReader(new InputStreamReader(client.getInputStream()));
			this.out=new PrintWriter(client.getOutputStream(), true);
		}catch(IOException e){
			e.printStackTrace();
		}
		phases.put("l", (room) => {
			try{

			}catch(IOException e){

			}
			return ;
		});
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
				JSONParser parser=new JSONParser();
				parser.parse();
			}
			in.close();
			out.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
