
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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

    public SQLiteManager() throws ClassNotFoundException, SQLException, Throwable
    {
        Connect();
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

    public void CreateTables() throws ClassNotFoundException, SQLException, Throwable
    {

        String user
                = "CREATE TABLE IF NOT EXISTS USER "
                + "("
                + " ID         INTEGER PRIMARY KEY AUTOINCREMENT    NOT NULL,"
                + " USERNAME   TEXT                                 NOT NULL,"
                + " PASSWORD   TEXT                                 NOT NULL,"
                + " ISONLINE   INTEGER                              NULL "
                + ")";
        statement.executeUpdate(user);
        System.out.println("Tabella User creata con successo");

        String message
                = "CREATE TABLE IF NOT EXISTS MESSAGE "
                + "("
                + " ID         INTEGER PRIMARY KEY AUTOINCREMENT    NOT NULL,"
                + " USERID     INT                                  NOT NULL,"
                + " CONTENT    TEXT                                 NOT NULL,"
                + " DATE       TEXT                                 NOT NULL,"
                + " TOUSERID   INT                                  NOT NULL "
                + ")";
        statement.executeUpdate(message);
        System.out.println("Tabella Message creata con successo");
        ResultSet rs = statement.executeQuery("SELECT * FROM USER");

        boolean UserExist = false;
        while (rs.next())
        {
            if (rs.getString("username").equals("Group"))
            {
                UserExist = true;
            }
        }
        if (!UserExist)
        {
            String add
                    = "INSERT INTO USER (USERNAME,PASSWORD) "
                    + "VALUES (" + "\'" + "Group" + "\'" + "," + "\'" + "" + "\'" + ");";
            statement.executeUpdate(add);
        }

        rs.close();

        Commit();

    }

    public boolean Register(User user) throws ClassNotFoundException, SQLException, Throwable
    {

        boolean success = false;
        ResultSet rs = statement.executeQuery("SELECT * FROM USER");

        while (rs.next())
        {
            String usr = rs.getString("username");
            if (usr.equals(user.Username))
            {
                success = false;
                System.out.println("Esiste già l'account");
                return success;
            }
        }
        
        String id = "1";
        String add
                = "INSERT INTO USER (USERNAME,PASSWORD) "
                + "VALUES (" + "\'" + user.Username + "\'" + "," + "\'" + user.Password + "\'" + ");"; 
        statement.executeUpdate(add);
        System.out.println("Utente" + user.Username + " con successo");

        rs.close();

        Commit();

        return true;
    }

    public boolean Login(User user) throws ClassNotFoundException, SQLException, Throwable
    {
        boolean success = false;

        ResultSet rs = statement.executeQuery("SELECT * FROM USER");

        while (rs.next())
        {
            int id = rs.getInt("id");
            String usr = rs.getString("username");
            String psd = rs.getString("password");

            if (usr.equals(user.Username) && psd.equals(user.Password))
            {
                success = true;
                System.out.println("Login riuscito");
                break;
            }
        }

        rs.close();

        Commit();

        return success;
    }

    public void AddMessage(Message message) throws SQLException, ClassNotFoundException, Throwable
    {
        ResultSet rs = statement.executeQuery("SELECT * FROM USER;");
        int id = 0;
        int toId = 0;
        while (rs.next())
        {
            String usr = rs.getString("username");
            if (usr.equals(message.Username))
            {
                id = rs.getInt("id");
            }
            if (usr.equals(message.ToUser))
            {
                toId = rs.getInt("id");
            }

        }
        rs.close();
        System.out.println("Sto aggiungendo: " + id + "," + "\'" + message.Text + "\'" + "," + "\'" + message.Date + "\'");

        String add
                = "INSERT INTO MESSAGE (USERID,CONTENT,DATE,TOUSERID) "
                + "VALUES (" + id + "," + "\'" + message.Text + "\'" + "," + "\'" + message.Date + "\'" + "," + toId + ");"; //Da sistemare gli id
        statement.executeUpdate(add);

        Commit();

        System.out.println("Ho finito l'add message");
    }

    public List<Message> GetMessages(String user, String toUser) throws SQLException, ClassNotFoundException, Throwable
    {
        List<Message> messages = new ArrayList<Message>();
        List<Integer> userIds = new ArrayList<Integer>();
        ResultSet rs = statement.executeQuery("SELECT * FROM USER;");

        System.out.println("ToUser: " + toUser);

        int userId = 0;
        int toUserId = 0;

        while (rs.next())
        {
            if (rs.getString("username").equals(toUser))
            {
                toUserId = rs.getInt("id");
            }
            if (rs.getString("username").equals(user))
            {
                userId = rs.getInt("id");
            }

        }

        rs.close();
        rs = statement.executeQuery("SELECT * FROM MESSAGE;");

        while (rs.next())
        {

            if (toUser.equals("Group"))
            {
                if (rs.getInt("touserid") == toUserId)
                {
                    Message mes = new Message();
                    mes.Date = rs.getString("date");
                    mes.Text = rs.getString("content");
                    int id = rs.getInt("userid");
                    userIds.add(id);
                    System.out.println("Ho aggiunto l'id: " + id);
                    mes.ToUser = toUser;
                    messages.add(mes);
                    System.out.println("Ho aggiunto un messaggio");

                }
            } 
            else
            {
                System.out.println("ho avuto " + rs.getInt("touserid") + "cerco " + toUserId + " e ho avuto " + rs.getInt("userid") + " e cerco " + userId);
                if (((rs.getInt("touserid") == toUserId && rs.getInt("userid") == userId)))
                {
                    System.out.println("Sono entrato nella merda");
                    Message mes = new Message();
                    mes.Date = rs.getString("date");
                    mes.Text = rs.getString("content");
                    int id = rs.getInt("userid");
                    userIds.add(id);
                    System.out.println("Ho aggiunto l'id: " + id);
                    mes.ToUser = toUser;
                    messages.add(mes);
                    System.out.println("Ho aggiunto un messaggio");

                } else if ((rs.getInt("touserid") == userId && rs.getInt("userid") == toUserId))
                {
                    Message mes = new Message();
                    mes.Date = rs.getString("date");
                    mes.Text = rs.getString("content");
                    int id = rs.getInt("userid");
                    userIds.add(id);
                    System.out.println("Ho aggiunto l'id: " + id);
                    mes.ToUser = toUser;
                    messages.add(mes);
                    System.out.println("Ho aggiunto un messaggio");
                }
            }

        }
        rs.close();

        int j = 0;
        for (int i = 0; i < userIds.size(); i++)
        {
            ResultSet rss = statement.executeQuery("SELECT * FROM USER;");
            while (rss.next())
            {
                System.out.println("Ripetizione");
                System.out.println("Ho: " + rss.getInt("id") + " cerco: " + userIds.get(i));
                if (rss.getInt("id") == userIds.get(i))
                {
                    System.out.println("Ho aggiunto: " + rss.getString("username"));
                    messages.get(i).Username = rss.getString("username");
                    System.out.println("Ho aggiunto un username");
                }
                //    j++;
            }
            rss.close();
        }

        Commit();
        //    Disconnect();
        return messages;
    }

    public List<String> GetUsersExceptOne(String user) throws SQLException, Throwable
    {
        List<String> users = new ArrayList<String>();
        ResultSet rs = statement.executeQuery("SELECT * FROM USER");
        while (rs.next())
        {

            String userGet = rs.getString("username");
            System.out.println("L'userget è " + userGet);
            if (!userGet.equals(user) && !userGet.contains("Group"))
            {
                users.add(userGet);
            }
        }
        return users;
    }

    public void Commit() throws SQLException, Throwable
    {
        connection.commit();
    }
}
