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
	private String username;
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
		}catch(IOException e) {
			e.printStackTrace();
		}
		initFunction();
	}

	private void initFunction()
	{
		phases.put(LOGIN_SIGNAL, (Void) -> {
			try{
				String data;
				JSONParser parser=new JSONParser();
				while(!(data=in.readLine()).equals(OUT_SIGNAL))
				{
					String password, tmpUsername, toSend;
					JSONObject msg=(JSONObject) parser.parse(data), outJson=new JSONObject();
					tmpUsername=(String) msg.get("username");
					password=database.getPassword(tmpUsername);
					toSend=password==null?"ne":(!password.equals(msg.get("password"))?"wp":(room.isLogged(tmpUsername)?"al":"ok"));
					outJson.put("section", LOGIN_SIGNAL);
					outJson.put("message", toSend);
					if(toSend.equals("ok"))
					{
						username=tmpUsername;
						room.addUser(username, out);
					}
					out.println(outJson.toJSONString());
				}
			}catch(SQLException|ParseException e){
				e.printStackTrace();
			}catch(IOException e){}
			return null;
		});
		phases.put(REGISTER_SIGNAL, (Void) -> {
			try{
				String data;
				JSONParser parser=new JSONParser();
				while(!(data=in.readLine()).equals(OUT_SIGNAL))
				{
					String toSend, tmpUsername;
					JSONObject msg=(JSONObject) parser.parse(data), outJson=new JSONObject();
					tmpUsername=(String) msg.get("username");
					toSend=database.existUser(tmpUsername)?"ae":(room.isLogged(tmpUsername)?"al":"ok");
					outJson.put("section", REGISTER_SIGNAL);
					outJson.put("message", toSend);
					if(toSend.equals("ok"))
					{
						username=tmpUsername;
						database.addUser(username, (String) msg.get("password"));
						room.addUser(username, out);
					}
					out.println(outJson.toJSONString());
				}
			}catch(SQLException|ParseException e){
				e.printStackTrace();
			}catch(IOException e){}
			return null;
		});
		phases.put(CHAT_SIGNAL, (Void) -> {
			try{
				String data;
				JSONParser parser=new JSONParser();
				room.sendListOfUsers(username, database.getJSONArrayOfUsers(username));
				while(!(data=in.readLine()).equals(OUT_SIGNAL))
				{
					JSONObject msg=(JSONObject) parser.parse(data);
					room.sendMessage(msg, username);
				}
			}catch(ParseException|SQLException e){
				e.printStackTrace();
			}catch(IOException e){}
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
			if(username!=null)
				room.removedUser(username);
		}
	}
}
