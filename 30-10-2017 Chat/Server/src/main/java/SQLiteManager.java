
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

    public SQLiteManager() throws ClassNotFoundException, SQLException
    {
        Connect();
        // CreateTables();
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
        //   Connect();

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

        String add
                = "INSERT INTO USER (USERNAME,PASSWORD) "
                + "VALUES (" + "\'" + "group" + "\'" + "," + "\'" + "" + "\'" + ");";
        statement.executeUpdate(add);

        Commit();

        //  Disconnect();
    }

    public boolean Register(User user) throws ClassNotFoundException, SQLException, SQLException
    {
        //  Connect();

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
        System.out.println("Ho superato la fase di controllo dell'esistenza dell'utente");
        String id = "1";
        String add
                = "INSERT INTO USER (USERNAME,PASSWORD) "
                + "VALUES (" + "\'" + user.Username + "\'" + "," + "\'" + user.Password + "\'" + ");"; //Da sistemare gli id
        statement.executeUpdate(add);
        System.out.println("Utente registrato con successo");

        rs.close();

        Commit();

        //  Disconnect();
        return true;
    }

    public boolean Login(User user) throws ClassNotFoundException, SQLException, SQLException
    {
        boolean success = false;
        //     Connect();

        ResultSet rs = statement.executeQuery("SELECT * FROM USER");

        while (rs.next())
        {
            int id = rs.getInt("id");
            String usr = rs.getString("username");
            String psd = rs.getString("password");
            System.out.println("Ho trovato: " + usr + " ---- " + psd + "all'id:" + id);
            System.out.println("Sto cercando: " + user.Username + " ---- " + user.Password);
            if (usr.equals(user.Username) && psd.equals(user.Password))
            {
                success = true;
                System.out.println("Login riuscito");
                break;
            }
        }

        rs.close();

        Commit();
        //     Disconnect();
        return success;
    }

    public void AddMessage(Message message) throws SQLException, ClassNotFoundException
    {
        //  Connect();
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
        //   Disconnect();
    }

    public List<Message> GetMessages(String user, String toUser) throws SQLException, ClassNotFoundException
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

            if (toUser.equals("group"))
            {
                if (rs.getInt("touserid") == toUserId/* && rs.getInt("userid") == userId*/)
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
            } else
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
                    System.out.println("PORCO DIOOOOO " + messages.get(i).Username);
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

    public List<String> GetUsersExceptOne(String user) throws SQLException
    {
        List<String> users = new ArrayList<String>();
        ResultSet rs = statement.executeQuery("SELECT * FROM USER");
        while (rs.next())
        {

            String userGet = rs.getString("username");
            System.out.println("L'userget è " + userGet);
            if (!userGet.equals(user) && !userGet.contains("group"))
            {
                users.add(userGet);
            }
        }
        return users;
    }

    public void Commit() throws SQLException
    {
        connection.commit();
    }
}
