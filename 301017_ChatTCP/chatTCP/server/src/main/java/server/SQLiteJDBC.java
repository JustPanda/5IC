package server;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.sql.*;
import java.util.HashMap;

public class SQLiteJDBC
{
	private Connection connection=null;
	private Statement stmt=null;
	private HashMap<String, JSONObject> users=new HashMap<>();
	private JSONArray listOfUsers=new JSONArray();

	SQLiteJDBC()
	{
		try{
			Class.forName("org.sqlite.JDBC");
			this.connection=DriverManager.getConnection("jdbc:sqlite:register.db");
			this.stmt=connection.createStatement();
			connection.setAutoCommit(false);
			{
				ResultSet rs=stmt.executeQuery("SELECT * FROM register");
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
//			stmt.executeUpdate("create table register(id integer primary key autoincrement,username text not null,password text not null)");
//			connection.commit();
		}catch(ClassNotFoundException|SQLException e){
			e.printStackTrace();
		}
	}

	void addUser(String username, String password) throws SQLException
	{
		String add="INSERT INTO REGISTER (USERNAME,PASSWORD) VALUES ('"+username+"','"+password+"');";
		JSONObject tmp=new JSONObject();
		tmp.put("username", username);
		tmp.put("online", true);
		users.put(username, tmp);
		listOfUsers.add(tmp);
		stmt.executeUpdate(add);
		connection.commit();
	}

	void loginAndLogoutManaged(String username, boolean state)
	{
		users.get(username).put("online", state);
	}

	boolean existUser(String username) throws SQLException
	{
		ResultSet rs=stmt.executeQuery("SELECT (COUNT(*) > 0) FROM register WHERE username='"+username+"'");
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
			password=stmt.executeQuery("SELECT * FROM register WHERE username='"+username+"'").getString("password"); //(trecord REGEXP '^ALA[0-9]')
		}
		return password;
	}
}
