package server;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server extends Thread
{
    public static int Unique;
    public static ArrayList<Clients> connections = new ArrayList<>();
    public static HashMap<String, PrintWriter> map = new HashMap<>();
    static public int port;
    static BufferedReader in;
    static PrintWriter out;
    
    public Server(int port)
    {
        this.port = port;
    }
    
    public static boolean Login(String email, String password, String confirm) throws ClassNotFoundException, SQLException
    {
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\samue\\Desktop\\Chat\\Database\\data.db");
        System.out.println("Database opened succefully");
        Statement stat = conn.createStatement();
        ResultSet rs = stat.executeQuery("SELECT email, password, confirm FROM Credentials");
        ArrayList<String> emails = new ArrayList<>();
        ArrayList<String> passwords = new ArrayList<>();
        ArrayList<String> confirms = new ArrayList<>();
        int index = 0;
        while(rs.next())
        {
            emails.add(rs.getString("email"));
            passwords.add(rs.getString("password"));
            confirms.add(rs.getString("confirm"));
        }
        for(int i=0; i<emails.size(); i++)
        {
            if(email.equals(emails.get(i)))
            {
                index = i;
            }
        }
        if(index == 0)
        {
            System.out.println("Utente non registrato");
            conn.close();
            return false;
        }
        else
        {
            if(password.equals(passwords.get(index)) && confirm.equals(confirms.get(index)))
            {
                System.out.println("Login Effettuato");
                return true;
            }
            else
            {
                System.out.println("Utente non registrato");
                conn.close();
                return false; 
            }
        }
    }
    
    public static boolean Register(String email, String password, String confirm) throws ClassNotFoundException, SQLException
    {
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\samue\\Desktop\\Chat\\Database\\data.db");
        try
        {
            System.out.println("Database opened succefully");
            Statement stat = conn.createStatement();
            ResultSet rs = stat.executeQuery("SELECT email, password, confirm FROM Credentials");
            ArrayList<String> emails = new ArrayList<>();
            ArrayList<String> passwords = new ArrayList<>();
            ArrayList<String> confirms = new ArrayList<>();
            int index = 0;
            while(rs.next())
            {
                emails.add(rs.getString("email"));
                passwords.add(rs.getString("password"));
                confirms.add(rs.getString("confirm"));
            }
            for(int i=0; i<emails.size(); i++)
            {
                if(email.equals(emails.get(i)))
                {
                    index = i;
                }
            }
            if(index == 0)
            {
                PreparedStatement state = conn.prepareStatement("INSERT INTO Credentials (email, password, confirm) VALUES (?, ?, ?)");
                state.setString(1, email);
                state.setString(2, password);
                state.setString(3, confirm);
                state.executeUpdate();
                System.out.println("Utente registrato correttamente");
                conn.close();
                return true;
            }
            else
            {
                if(password.equals(passwords.get(index)) && confirm.equals(confirms.get(index)))
                {
                    System.out.println("Utente già registrato");
                    return false;
                }
                else
                {
                    PreparedStatement state = conn.prepareStatement("INSERT INTO Credentials (email, password, confirm) VALUES (?, ?, ?)");
                    state.setString(1, email);
                    state.setString(2, password);
                    state.setString(3, confirm);
                    state.executeUpdate();
                    System.out.println("Utente registrato correttamente");
                    conn.close();
                    return true;
                }
            }
        }
        catch(Throwable e)
        {
            System.out.println("Impossibile registrare l'utente");
            conn.close();
            return false;
        }
    }
    
    public static void Display(String msg) 
    {
        System.out.println(msg);
    }
    
    public static void main(String[] args) throws FileNotFoundException 
    {
        try
        {
            ServerSocket server = new ServerSocket(9090);
            ExecutorService executor = Executors.newCachedThreadPool();
            while(true)
            {
                Socket socket = server.accept();
                Clients client = new Clients(socket);
                connections.add(client);
                executor.execute(client);
            }
        }
        catch(Throwable ex)
        {
            ex.printStackTrace();
        }
    }
    
    static class Clients implements Runnable
    {
        Socket socket;
        BufferedReader in ;
        PrintWriter out;
        int Id;
        String User;
        String m;
        int index = 0;
        int send = 0;
        PrintWriter toOut;
        
        public Clients(Socket socket)
        {
            this.socket = socket;
            Id = ++Unique;
            try
            {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
            }
            catch(Throwable e)
            {
                
            }
        }
        
        @Override
        public void run()
        {
            System.out.println("eccomi");
            try
            {
                String check = in.readLine();
                System.out.println(check);

                if(check.equals("login"))
                {
                    boolean register = false;
                    while(!register)
                    {
                        String credentials = in.readLine();
                        String[] fields = credentials.split(",");
                        User = fields[1];
                        
                        //toOut = 
                        
                        index = 0;
                        send = 0;
                  
                        System.out.println(index);
                        
                        Display(User + " just connect");
                        if(fields[fields.length - 1].toLowerCase().equals("login".toLowerCase()))
                        {
                            register = Login(fields[2], fields[3], fields[4]);
                            if(register)
                            {
                                out.println("login success" + '\n');
                            }
                        }
                        else
                        {
                            if(fields[fields.length - 1].toLowerCase().equals("register".toLowerCase()))
                            {
                                register = Register(fields[2], fields[3], fields[4]);
                                if(register)
                                {
                                    out.println("register success" + '\n');
                                }
                            }
                        }
                    }
                }
                else
                {
                    if(check.equals("chat")) 
                    {
                        String ToUser = in.readLine();
                        String[] Users = ToUser.split(",");
                        map.put(Users[1], out);
                        System.out.println("to user " + ToUser);
                        while(true)
                        {
                            String s1 = in.readLine();
                            System.out.println("recived: " + Users[0] + " " + s1);
                            map.get(Users[0]).println(s1);
                        }
                    }
                }
            }
            catch(Throwable e)
            {
                
            }
        }
    }
}
