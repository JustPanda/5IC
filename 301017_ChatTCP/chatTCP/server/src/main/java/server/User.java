package server;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.Socket;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.function.Function;

class User implements Runnable
{

	private final String OUT_SIGNAL="out", LOGIN_SIGNAL="l", REGISTER_SIGNAL="r", CHAT_SIGNAL="c";
	private Socket client;
	private BufferedReader in;
	private PrintWriter out;
	private SQLiteJDBC database;
	private Room room;
	private HashMap<String, Function<Void, Void>> phases=new HashMap<>();

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
		phases.put(LOGIN_SIGNAL, (Void) -> {
			try{
				String data;
				JSONParser parser=new JSONParser();
				while(!(data=in.readLine()).equals(OUT_SIGNAL))
				{
					String password;
					JSONObject msg=(JSONObject) parser.parse(data), outJson=new JSONObject();
					password=database.getPassword((String) msg.get("username"));
					outJson.put("section", LOGIN_SIGNAL);
					outJson.put("message", password==null?"ne":(password.equals(msg.get("password"))?"ok":"wp"));
					out.println(outJson.toJSONString());
				}
			}catch(IOException|SQLException|ParseException e){
				e.printStackTrace();
			}
			return null;
		});
		phases.put(REGISTER_SIGNAL, (Void) -> {
			try{
				String data;
				JSONParser parser=new JSONParser();
				while(!(data=in.readLine()).equals(OUT_SIGNAL))
				{
					JSONObject msg=(JSONObject) parser.parse(data), outJson=new JSONObject();
					outJson.put("section", REGISTER_SIGNAL);
					outJson.put("message", database.existUser((String) msg.get("username"))?"ae":"ok");
					database.addUser((String) msg.get("username"), (String) msg.get("password"));
					out.println(outJson.toJSONString());
				}
			}catch(IOException|SQLException|ParseException e){
				e.printStackTrace();
			}
			return null;
		});
		phases.put(CHAT_SIGNAL, (Void) -> {
			try{
				String data;
				JSONParser parser=new JSONParser();
				while(!(data=in.readLine()).equals(OUT_SIGNAL))
				{
					JSONObject msg=(JSONObject) parser.parse(data);
				}
			}catch(IOException|ParseException e){
				e.printStackTrace();
			}
			return null;
		});
	}

	public void run()
	{
		try{
			boolean notExit=true;
			while(notExit)
			{
				String msg=in.readLine();
				phases.get(msg).apply(null);
			}
			in.close();
			out.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
