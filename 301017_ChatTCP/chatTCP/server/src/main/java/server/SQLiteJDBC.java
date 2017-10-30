package server;

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
		}catch(ClassNotFoundException|SQLException e){
			e.printStackTrace();
		}
	}

	void addUser(String username, String password)
	{
		String add="INSERT INTO REGISTER (USERNAME,PASSWORD) " +
				"VALUES ('"+username+"','"+password+"');";
		try{
			stmt.executeUpdate(add);
			connection.commit();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}

	boolean isPresent()
	{
		return true;
	}
}
