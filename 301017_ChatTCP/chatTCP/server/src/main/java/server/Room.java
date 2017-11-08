package server;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.*;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

class Room
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

	void sendListOfMessages(String username, ResultSet rs) throws SQLException
	{
		PrintWriter outUser=nameToSocket.get(username);
		while(rs.next())
		{
			System.out.println(username+"\n"+rs.getString("destination")+"\n"+createMessageJson(rs.getString("message"), username, rs.getString("destination"),false));
//			outUser.println(createMessageJson(rs.getString("message"), username,false).toJSONString());
		}
	}

	void sendMessage(JSONObject msg, String fromUser, SQLiteJDBC database) throws SQLException
	{
		boolean isGlobal;
		String destination=(String) msg.get("destination"), destinationJSON, text=(String) msg.get("text");
		JSONObject outJson;
		isGlobal=destination.equals("Global");
		outJson=createMessageJson(text, fromUser, isGlobal?"Global":fromUser, isGlobal);
		if(isGlobal)
		{
			broadcastMessage(fromUser, outJson.toJSONString());
		}else{
			nameToSocket.get(destination).println(outJson.toJSONString());
		}
		database.addMessage(text, destination, fromUser);
	}

	boolean isLogged(String username)
	{
		return nameToSocket.get(username)!=null;
	}

	private void broadcastListOfUsers(String toAvoid)
	{
		nameToSocket.forEach((key, value) -> {
			if(!key.equals(toAvoid))
			{
				value.println(luManaged(key));
			}
		});
	}

	private void broadcastMessage(String toAvoid, String msg)
	{
		nameToSocket.forEach((key, value) -> {
			if(!key.equals(toAvoid))
			{
				value.println(msg);
			}
		});
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

	private JSONObject createMessageJson(String text, String fromUser, String destination, boolean isGlobal)
	{
		JSONObject toReturn=new JSONObject(), message, info;
		toReturn.put("section", "c");
		toReturn.put("message", new JSONObject());
		message=(JSONObject) toReturn.get("message");
		message.put("key", "msg");
		message.put("destination", destination);
		message.put("info", new JSONObject());
		info=(JSONObject) message.get("info");
		info.put("text", text);
		if(isGlobal)
		{
			info.put("name", fromUser);
		}
		return toReturn;
	}
}
