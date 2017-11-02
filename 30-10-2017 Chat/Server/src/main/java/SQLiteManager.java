
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author manue
 */
public class SQLiteManager
{

    Connection connection;
    Statement statement;
    String dbName = "chat.sqlite";

    public SQLiteManager() throws ClassNotFoundException, SQLException
    {
        CreateTables();
    }

    public void Connect() throws ClassNotFoundException, SQLException
    {
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:" + dbName);
        connection.setAutoCommit(false);
        statement = connection.createStatement();
        System.out.println("Opened database successfully");
    }

    public void Disconnect() throws SQLException
    {
        statement.close();
        connection.close();
        System.out.println("Closed database successfully");
    }

    public void CreateTables() throws ClassNotFoundException, SQLException, SQLException
    {
        Connect();

        String user
                = "CREATE TABLE USER "
                + "("
                + " ID         INT PRIMARY KEY     NOT NULL,"
                + " USERNAME   TEXT                NOT NULL,"
                + " PASSWORD   TEXT                NOT NULL "
                + ")";
        statement.executeUpdate(user);
        System.out.println("Tabella User creata con successo");

        String message
                = "CREATE TABLE MESSAGE "
                + "("
                + " ID         INT PRIMARY KEY     NOT NULL,"
                + " USERID     INT                 NOT NULL,"
                + " CONTENT    TEXT                NOT NULL,"
                + " DATE       TEXT                NOT NULL "
                + ")";
        statement.executeUpdate(message);
        System.out.println("Tabella Message creata con successo");

        Disconnect();
    }

    public boolean Register(User user) throws ClassNotFoundException, SQLException, SQLException
    {
        Connect();

        boolean success=false;
        ResultSet rs = statement.executeQuery("SELECT * FROM USER");
        
        while(rs.next())
        {
            String usr = rs.getString("username");
            if(usr.equals(user.Username))
            {
                success = false;
                System.out.println("Esiste già l'account");
                return success;
            }
        }
        
        String id ="1";
        String add = 
                  "INSERT INTO USER (ID,USERNAME,PASSWORD) "
                + "VALUES (" + id + "," + user.Username + "," + user.Password + ");"; //Da sistemare gli id
        statement.executeUpdate(add);
        System.out.println("Utente registrato con successo");
        
        rs.close();
        
        Disconnect();
        
        return true;
    }

    public boolean Login(User user) throws ClassNotFoundException, SQLException, SQLException
    {
        boolean success = false;
        Connect();
        
        ResultSet rs = statement.executeQuery("SELECT * FROM USER");
        
        while(rs.next())
        {
            String usr = rs.getString("username");
            String psd = rs.getString("password");
            if(usr.equals(user.Username) && psd.equals(user.Password))
            {
                success = true;
                System.out.println("Login riuscito");
                break;
            }
        }
        
        rs.close();
        Disconnect();
        return success;
    }

    public void AddMessage(Message message) throws SQLException
    {
        String id ="1";
        String add = 
                  "INSERT INTO MESAGE (ID,USERID,CONTENT,DATE) "
                + "VALUES (" + id + "," + id + "," + message.Text + "," + message.Date + ");"; //Da sistemare gli id
        statement.executeUpdate(add);
    }

    public List<Message> GetMessages()
    {
        return null;
    }
}
