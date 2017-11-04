package server;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.sql.*;

public class SQLiteJDBC
{
	private Connection connection=null;
	private Statement stmt=null;

	SQLiteJDBC()
	{
		try{
			Class.forName("org.sqlite.JDBC");
			this.connection=DriverManager.getConnection("jdbc:sqlite:register.db");
			this.stmt=connection.createStatement();
			connection.setAutoCommit(false);
//			stmt.executeUpdate("create table register(id integer primary key autoincrement,username text not null,password text not null)");
//			connection.commit();
		}catch(ClassNotFoundException|SQLException e){
			e.printStackTrace();
		}
	}

	void addUser(String username, String password) throws SQLException
	{
		String add="INSERT INTO REGISTER (USERNAME,PASSWORD) VALUES ('"+username+"','"+password+"');";
		stmt.executeUpdate(add);
		connection.commit();
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

	boolean existUser(String username) throws SQLException
	{
		ResultSet rs=stmt.executeQuery("SELECT (COUNT(*) > 0) FROM register WHERE username='"+username+"'");
		rs.next();
		return rs.getBoolean(1);
	}

	JSONArray getJSONArrayOfUsers(String requestUsername) throws SQLException
	{
		ResultSet rs=stmt.executeQuery("SELECT * FROM register");
		JSONArray listOfUsers=new JSONArray();
		JSONObject tmp;
		while(rs.next())
		{
			String username=rs.getString("username");
			if(!username.equals(requestUsername))
			{
				tmp=new JSONObject();
				tmp.put("name", username);
				listOfUsers.add(tmp);
			}
		}
		return listOfUsers;
	}

}
