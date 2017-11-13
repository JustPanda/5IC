package server;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.Socket;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.function.Function;

class User implements Runnable, Signal
{

	private String username;
	private Socket client;
	private BufferedReader in;
	private PrintWriter out;
	private Database database;
	private Room room;
	private HashMap<String, Function<Void, Boolean>> phases=new HashMap<>();

	User(Socket client, Database database, Room room)
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
					JSONObject msg=(JSONObject) parser.parse(data);
					tmpUsername=(String) msg.get("username");
					password=database.getPassword(tmpUsername);
					toSend=password==null?"ne":(!password.equals(msg.get("password"))?"wp":(room.isLogged(tmpUsername)?"al":"ok"));
					if(toSend.equals("ok"))
					{
						username=tmpUsername;
						database.loginAndLogoutManaged(username, true);
						room.addUser(username, out);
					}
					out.println(room.createLoginJSON(toSend));
				}
			}catch(SQLException|ParseException e){
				e.printStackTrace();
			}catch(IOException e){
				return false;
			}
			return true;
		});
		phases.put(REGISTER_SIGNAL, (Void) -> {
			try{
				String data;
				JSONParser parser=new JSONParser();
				while(!(data=in.readLine()).equals(OUT_SIGNAL))
				{
					String toSend, tmpUsername;
					JSONObject msg=(JSONObject) parser.parse(data);
					tmpUsername=(String) msg.get("username");
					toSend=database.existUser(tmpUsername)?"ae":(room.isLogged(tmpUsername)?"al":"ok");
					if(toSend.equals("ok"))
					{
						username=tmpUsername;
						database.addUser(username, (String) msg.get("password"));
						room.addUser(username, out);
					}
					out.println(room.createRegisterJSON(toSend));
				}
			}catch(SQLException|ParseException e){
				e.printStackTrace();
			}catch(IOException e){
				return false;
			}
			return true;
		});
		phases.put(CHAT_SIGNAL, (Void) -> {
			try{
				String data;
				JSONParser parser=new JSONParser();
				room.initRegister(username);
				while(!(data=in.readLine()).equals(OUT_SIGNAL))
				{
					JSONObject msg=(JSONObject) parser.parse(data);
					room.sendMessage(msg, username);
				}
				exitUser();
			}catch(ParseException|SQLException e){
				e.printStackTrace();
			}catch(IOException e){
				return false;
			}
			return true;
		});
	}

	public void run()
	{
		try{
			boolean notExit=true;
			while(notExit)
			{
				String msg=in.readLine();
				notExit=phases.get(msg).apply(null);
			}
			in.close();
			out.close();
		}catch(IOException e){
		}
		finally {
			if(username!=null)
				exitUser();
		}
	}

	private void exitUser()
	{
		database.loginAndLogoutManaged(username, false);
		room.removedUser(username);
		username=null;
	}
}
