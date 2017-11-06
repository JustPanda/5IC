package server;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.*;

public class Room
{
	private HashMap<String, PrintWriter> nameToSocket=new HashMap<>();
	private JSONArray lu=new JSONArray();

	Room(JSONArray lu)
	{
		this.lu=lu;
	}

	void addUser(String username, PrintWriter out)
	{
		nameToSocket.put(username, out);
		broadcastListOfUsers(username);
	}

	void removedUser(String username)
	{
		nameToSocket.remove(username);
		broadcastListOfUsers(username);
	}

	void sendListOfUsers(String username) throws SQLException
	{
		nameToSocket.get(username).println(luManaged(username));
	}

	void sendMessage(JSONObject msg, String fromUser)
	{
		String destination=(String) msg.get("destination");
		JSONObject outJson=new JSONObject(), info, message;
		outJson.put("section", "c");
		outJson.put("message", new JSONObject());
		message=(JSONObject) outJson.get("message");
		message.put("key", "msg");
		message.put("info", new JSONObject());
		info=(JSONObject) message.get("info");
		info.put("text", msg.get("text"));
		if(destination.equals("Global"))
		{
			info.put("name", fromUser);
			message.put("destination", "Global");
			broadcastMessage(fromUser, outJson.toJSONString());
		}else{
			message.put("destination", fromUser);
			nameToSocket.get(destination).println(outJson.toJSONString());
		}
	}

	private void broadcastListOfUsers(String toAvoid)
	{
		for(Map.Entry<String, PrintWriter> it: nameToSocket.entrySet())
		{
			String actualUser=it.getKey();
			if(!actualUser.equals(toAvoid))
			{
				it.getValue().println(luManaged(actualUser));
			}
		}
	}

	private void broadcastMessage(String toAvoid, String msg)
	{
		for(Map.Entry<String, PrintWriter> it: nameToSocket.entrySet())
		{
			if(!it.getKey().equals(toAvoid))
			{
				it.getValue().println(msg);
			}
		}
	}

	boolean isLogged(String username)
	{
		return nameToSocket.get(username)!=null;
	}

	private String luManaged(String toAvoid)
	{
		JSONObject jsonLu=new JSONObject(), tmp;
		JSONArray luTmp=new JSONArray();
		jsonLu.put("section", "c");
		jsonLu.put("message", new JSONObject());
		tmp=(JSONObject) jsonLu.get("message");
		tmp.put("key", "lu");
		lu.forEach((o) -> {
			JSONObject tmpUser=(JSONObject) o;
			if(!tmpUser.get("username").equals(toAvoid))
			{
				luTmp.add(tmpUser);
			}
		});
		tmp.put("users", luTmp);
		return jsonLu.toJSONString();
	}
}
