package server;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class Room
{
	private HashMap<String, PrintWriter> nameToSocket=new HashMap<>();

	void addUser(String username, PrintWriter out)
	{
		nameToSocket.put(username, out);
	}

	void removedUser(String username)
	{
		nameToSocket.remove(username);
	}

	void sendListOfUsers(String toUser, JSONArray listOfUsers)
	{
		JSONObject outJson=new JSONObject(), tmp;
		outJson.put("section", "c");
		outJson.put("message", new JSONObject());
		tmp=(JSONObject) outJson.get("message");
		tmp.put("key", "lu");
		tmp.put("users", listOfUsers);
		nameToSocket.get(toUser).println(outJson.toJSONString());
	}

	void sendMessage(JSONObject msg, String fromUser)
	{
		String destination=(String) msg.get("destination");
		JSONObject outJson=new JSONObject(), tmp;
		outJson.put("section", "c");
		outJson.put("message", new JSONObject());
		tmp=(JSONObject) outJson.get("message");
		tmp.put("key", "msg");
		tmp.put("destintion", destination);
		tmp.put("info", new JSONObject());
		tmp=(JSONObject) tmp.get("info");
		tmp.put("text", msg.get("text"));
		if(destination.equals("Global"))
		{
			tmp.put("name", fromUser);
			for(Map.Entry<String, PrintWriter> it: nameToSocket.entrySet())
			{
				if(!it.getKey().equals(fromUser))
				{
					it.getValue().println(outJson.toJSONString());
				}
			}
		}else{
			nameToSocket.get(destination).println(outJson.toJSONString());
		}
	}

	boolean isLogged(String username)
	{
		return nameToSocket.get(username)!=null;
	}
}
