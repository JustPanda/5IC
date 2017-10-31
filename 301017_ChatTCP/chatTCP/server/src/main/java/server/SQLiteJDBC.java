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
//			stmt.executeUpdate("create table register(id integer primary key autoincrement,username text not null,password text not null)");
//			connection.commit();
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

	boolean existUser(String username) throws SQLException
	{
		ResultSet rs=stmt.executeQuery("SELECT (COUNT(*) > 0) FROM register WHERE username='"+username+"'");
		rs.next()
		System.out.println();
		return rs.getBoolean(1);
	}
}
