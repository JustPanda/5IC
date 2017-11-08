package server;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.sql.*;
import java.util.HashMap;

public class SQLiteJDBC
{
	private Connection register=null, messages=null;
	private Statement stmtRegister=null, stmtMessages=null;
	private HashMap<String, JSONObject> users=new HashMap<>();
	private JSONArray listOfUsers=new JSONArray();

	SQLiteJDBC()
	{
		try{
			Class.forName("org.sqlite.JDBC");
			this.register=DriverManager.getConnection("jdbc:sqlite:register.db");
			this.messages=DriverManager.getConnection("jdbc:sqlite:messages.db");
			this.stmtRegister=register.createStatement();
			this.stmtMessages=messages.createStatement();
			register.setAutoCommit(false);
			messages.setAutoCommit(false);
			stmtRegister.executeUpdate("create table register(id integer primary key autoincrement,username text not null,password text not null)");
			stmtMessages.executeUpdate("CREATE TABLE Global(id INTEGER PRIMARY KEY AUTOINCREMENT,message TEXT NOT NULL,destination TEXT NOT NULL)");
			register.commit();
			messages.commit();
			initRegister();
		}catch(ClassNotFoundException|SQLException e){
			e.printStackTrace();
		}
	}

	void addUser(String username, String password) throws SQLException
	{
		String toRegister="INSERT INTO REGISTER (username,password) VALUES ('"+username+"','"+password+"');",
				addTableMessages="CREATE TABLE "+username+"(id INTEGER PRIMARY KEY AUTOINCREMENT,message TEXT NOT NULL,destination TEXT NOT NULL)";
		JSONObject tmp=new JSONObject();
		tmp.put("username", username);
		tmp.put("online", true);
		users.put(username, tmp);
		listOfUsers.add(tmp);
		stmtRegister.executeUpdate(toRegister);
		stmtMessages.executeUpdate(addTableMessages);
		register.commit();
		messages.commit();
	}

	void addMessage(String message, String destination, String fromUser) throws SQLException
	{
		String toAdd="INSERT INTO "+destination+"(message,destination) VALUES ('"+message+"','"+fromUser+"')";
		stmtMessages.executeUpdate(toAdd);
		messages.commit();
	}

	void loginAndLogoutManaged(String username, boolean state)
	{
		users.get(username).put("online", state);
	}

	boolean existUser(String username) throws SQLException
	{
		ResultSet rs=stmtRegister.executeQuery("SELECT (COUNT(*) > 0) FROM register WHERE username='"+username+"'");
		rs.next();
		return rs.getBoolean(1);
	}

	JSONArray getAllUsers()
	{
		return listOfUsers;
	}

	String getPassword(String username) throws SQLException
	{
		String password=null;
		if(existUser(username))
		{
			password=stmtRegister.executeQuery("SELECT * FROM register WHERE username='"+username+"'").getString("password");
		}
		return password;
	 }

	 ResultSet getMessagesOfUser(String username) throws SQLException
	 {
		return stmtMessages.executeQuery("SELECT * FROM "+username);

	 }

	 private void initRegister() throws SQLException
	 {
		 ResultSet rs=stmtRegister.executeQuery("SELECT * FROM register");
		 JSONObject tmp;
		 while(rs.next())
		 {
			 String username=rs.getString("username");
			 tmp=new JSONObject();
			 tmp.put("username", username);
			 tmp.put("online", false);
			 users.put(username, tmp);
			 listOfUsers.add(tmp);
		 }
	 }
}
