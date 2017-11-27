/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author davide
 */
public class SQLHelper
{

    private final String url = "user.sqlite";

    public void createDatabase()
    {
	try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + url))
	{

	    if (conn != null)
	    {
		DatabaseMetaData meta = conn.getMetaData();
		System.out.println("The driver name is " + meta.getDriverName());
	    }
	}
	catch (SQLException ex)
	{
	    Logger.getLogger(SQLHelper.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

    public void addNewUser(String username, String password)
    {
	String credentials = "INSERT INTO dati VALUES(?,?);";

	try (Connection conn = this.connect();
		PreparedStatement pstmt = conn.prepareStatement(credentials))
	{
	    pstmt.setString(1, username);
	    pstmt.setString(2, password);
	    pstmt.executeUpdate();
	    conn.close();
	}
	catch (SQLException ex)
	{
	}

    }

    public void createNewTable()
    {

	Connection conn = null;
	Statement stmt = null;

	try
	{
	    Class.forName("org.sqlite.JDBC");
	    conn = DriverManager.getConnection("jdbc:sqlite:" + url);
	    System.out.println("Opened database successfully");

	    stmt = conn.createStatement();
	    String sql = "CREATE TABLE IF NOT EXISTS dati(\n "
		    + "username      TEXT        NOT NULL   UNIQUE,\n"
		    + "password      TEXT        NOT NULL\n"
		    + ");";
	    stmt.executeUpdate(sql);

	}
	catch (SQLException | ClassNotFoundException ex)
	{
	    Logger.getLogger(SQLHelper.class.getName()).log(Level.SEVERE, null, ex);
	}
	System.out.println("Table created successufully");
    }

    //create a connection between program and database
    public Connection connect()
    {
	//SQLite connection string 
	Connection conn = null;
	try
	{
	    conn = DriverManager.getConnection("jdbc:sqlite:" + url);
	}
	catch (SQLException ex)
	{
	}
	return conn;
    }
}
