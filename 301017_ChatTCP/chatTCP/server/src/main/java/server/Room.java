package server;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.*;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

class Room implements Signal
{
	private HashMap<String, PrintWriter> nameToSocket=new HashMap<>();
	private Database database;

	Room(Database database)
	{
		this.database=database;
	}

	synchronized void addUser(String username, PrintWriter out)
	{
		nameToSocket.put(username, out);
		broadcastListOfUsers(username);
	}

	synchronized void removedUser(String username)
	{
		nameToSocket.remove(username);
		broadcastListOfUsers(username);
	}

	synchronized void initRegister(String username) throws SQLException
	{
		PrintWriter outUser=nameToSocket.get(username);
		ResultSet rs=database.getResultSetMessages(username);
		JSONArray messages=new JSONArray(), listOfMessages=new JSONArray();
		JSONObject listOfUsers=createListOfUserJSON(username, true), listOfMessagesObj=new JSONObject(), outJson=new JSONObject();
		outJson.put("section", CHAT_SIGNAL);
		outJson.put("data", messages);
		while(rs.next())
		{
			boolean isGlobal;
			String fromTable=rs.getString("tablename"), destination=rs.getString("destination");
			JSONObject message=new JSONObject();
			isGlobal=fromTable.equals("Global");
			if(isGlobal)
			{
				if(destination.equals(username))
				{

					message.put("orientation", "right");
				}else{
					message.put("name", destination);
					message.put("orientation", "left");
				}
				message.put("destination", "Global");
			}else{
				message.put("orientation", rs.getBoolean("send")?"right":"left");
				message.put("destination", destination);
			}
			message.put("text", rs.getString("message"));
			listOfMessages.add(message);
		}
		listOfMessagesObj.put("key", "msg");
		listOfMessagesObj.put("messages", listOfMessages);
		messages.add(listOfUsers);
		messages.add(listOfMessagesObj);
		outUser.println(outJson);
	}

	synchronized void sendMessage(JSONObject msg, String fromUser) throws SQLException
	{
		boolean isGlobal;
		String destination=(String) msg.get("destination"), text=(String) msg.get("text");
		JSONArray message=new JSONArray();
		JSONObject outJson, tmp=new JSONObject();
		isGlobal=destination.equals("Global");
		tmp.put("text", text);
		tmp.put("orientation", "left");
		message.add(tmp);
		outJson=createMessageJSON(message);
		if(isGlobal)
		{
			tmp.put("name", fromUser);
			tmp.put("destination", "Global");
			broadcastMessage(fromUser, outJson.toJSONString());
		}else{
			tmp.put("destination", fromUser);
			PrintWriter outUser=nameToSocket.get(destination);
			if(outUser!=null)
			{
				outUser.println(outJson.toJSONString());
			}
		}
		database.addMessage(text, destination, fromUser, isGlobal);
	}

	synchronized boolean isLogged(String username)
	{
		return nameToSocket.get(username)!=null;
	}

	synchronized private void broadcastListOfUsers(String toAvoid)
	{
		nameToSocket.forEach((key, value) -> {
			if(!key.equals(toAvoid))
			{
				value.println(createListOfUserJSON(key, false));
			}
		});
	}

	synchronized private void broadcastMessage(String toAvoid, String msg)
	{
		nameToSocket.forEach((key, value) -> {
			if(!key.equals(toAvoid))
			{
				value.println(msg);
			}
		});
	}

	JSONObject createLoginJSON(String toSend)
	{
		JSONObject toReturn=new JSONObject();
		toReturn.put("section", LOGIN_SIGNAL);
		toReturn.put("data", toSend);
		return toReturn;
	}

	JSONObject createRegisterJSON(String toSend)
	{
		JSONObject toReturn=new JSONObject();
		toReturn.put("section", REGISTER_SIGNAL);
		toReturn.put("data", toSend);
		return toReturn;
	}

	private JSONObject createListOfUserJSON(String toAvoid, boolean partial)
	{
		JSONObject jsonLu, message=new JSONObject();
		JSONArray luTmp=new JSONArray(), lu=database.getAllUsers();
		if(partial)
		{
			jsonLu=message;
		}else{
			JSONArray tmp=new JSONArray();
			jsonLu=new JSONObject();
			jsonLu.put("section", CHAT_SIGNAL);
			jsonLu.put("data", tmp);
			tmp.add(message);
		}
		message.put("key", "lu");
		lu.forEach((o) -> {
			JSONObject tmpUser=(JSONObject) o;
			if(!tmpUser.get("username").equals(toAvoid))
			{
				luTmp.add(tmpUser);
			}
		});
		message.put("users", luTmp);
		return jsonLu;
	}

	private JSONObject createMessageJSON(JSONArray messages)
	{
		JSONObject toReturn=new JSONObject(), message=new JSONObject();
		JSONArray cnt=new JSONArray();
		toReturn.put("section", CHAT_SIGNAL);
		toReturn.put("data", cnt);
		message.put("key", "msg");
		message.put("only", true);
		message.put("messages", messages);
		cnt.add(message);
		return toReturn;
	}
}
