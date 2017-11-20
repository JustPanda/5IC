package chat.server;

import java.sql.*;

class SQLiteHelper
{
    private Connection connection;
    private Statement statement;

    private final static String DATABASE_PATH="jdbc:sqlite:chat/server/users.db";

    SQLiteHelper()
    {
        try
        {
            Class.forName("org.sqlite.JDBC");
            connection=DriverManager.getConnection(DATABASE_PATH);
            statement=connection.createStatement();
            connection.setAutoCommit(false);
            createUsersTableIfNotExist();
        }
        catch(ClassNotFoundException | SQLException e)
        {
            e.printStackTrace();
        }
    }

    public boolean addUser(String name, String password)
    {
        boolean r=true;
        String s=String.format("INSERT INTO users (name, password) VALUES (\'%s\', \'%s\')", name, password);
        try
        {
            statement.executeUpdate(s);
            connection.commit();
        }
        catch(SQLException e)
        {
            r=false;
        }
        return r;
    }

    public void removeUser(String name)
    {
        String s=String.format("DELETE FROM users WHERE name=\'%s\'", name);

        try
        {
            statement.executeUpdate(s);
            connection.commit();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    public int authUser(String name, String password)
    {
        int returnCode=2;
        String s=String.format("SELECT name, password FROM users WHERE name=\'%s\'", name);
        try
        {
            ResultSet res=statement.executeQuery(s);
            if(res.next())
            {
                returnCode=password.equals(res.getString("password"))?0:1;
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return returnCode;
    }

    private void createTable()
    {
        String s="CREATE TABLE users (name TEXT PRIMARY KEY NOT NULL, password TEXT NOT NULL)";
        try
        {
            statement.executeUpdate(s);
            connection.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Something went wrong while creating the table...");
            e.printStackTrace();
        }
    }

    private void createUsersTableIfNotExist()
    {
        String s="SELECT * FROM users";
        try
        {
            statement.executeQuery(s);
        }
        catch(SQLException e)
        {
            createTable();
        }
    }
}
