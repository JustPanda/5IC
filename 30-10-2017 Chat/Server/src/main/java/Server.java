 
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author manue
 */
public class Server
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException
    {
        Mixer mixer = new Mixer();
        mixer.Start();
        /*  ServerSocket s = new ServerSocket(8080);
        
        Socket socket = s.accept();
        PrintWriter p = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader r = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        p.println("DIO CAN");
        System.out.println("Ho inviato");
        r.readLine(); */

    }

}

class Mixer
{

    ArrayList<ClientConnection> connections = new ArrayList<ClientConnection>();
    private ArrayList<Message> messages = new ArrayList<Message>();
    SQLiteManager sql;

    public void Start() throws IOException, ClassNotFoundException, SQLException
    {
        ServerSocket s = new ServerSocket(9090);
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        int clientIndex = 0;
        while (clientIndex <= 1000)
        {
            clientIndex++;
            Socket socket = (Socket) s.accept();
            ClientConnection client = new ClientConnection(socket, this);
            sql = new SQLiteManager();
            System.out.println("Connesso al client n°" + clientIndex);
            client.output = new PrintWriter(socket.getOutputStream(), true);
            client.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            boolean HasLogin = false;
            while (!HasLogin)
            {
                String loginInfo = client.input.readLine();
                System.out.println("Ho ricevuto per il login/signup " + loginInfo);
                User user;
                try
                {
                    Gson gson = new GsonBuilder().create();
                    user = gson.fromJson(loginInfo, User.class);

                    if (user.Action.equals("Registration"))
                    {
                        boolean success = sql.Register(user);
                        if (!success) //se esiste nel databse
                        {
                            client.output.println("RegistrationFail\n");
                            System.out.println("Registration Failed");
                        } else if (success)
                        {
                            client.output.println("RegistrationSuccess\n");
                            client.user = user;
                            connections.add(client);
                            SendPreviousMessages(client);
                            executor.execute(client);
                            HasLogin = true;
                            System.out.println("Registration success");
                        }
                    } else if (user.Action.equals("Login"))
                    {
                        boolean success = sql.Login(user);
                        if (!success)
                        {
                            client.output.println("LoginFail\n");
                            System.out.println("Login Fail");
                        } else if (success)
                        {
                            client.output.println("LoginSuccess\n");
                            client.user = user;
                            System.out.println("Login riuscito");
                            connections.add(client);
                            Thread.sleep(100);
                            SendPreviousMessages(client);
                            executor.execute(client);
                            HasLogin = true;
                            System.out.println("Login success");
                        }
                    }
                } catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }

        }
        executor.shutdown();
        s.close();
    }

    private void SendPreviousMessages(ClientConnection conn) throws SQLException, ClassNotFoundException
    {
        List<Message> l = sql.GetMessages(conn.user.Username, conn.toUser);
        Message[] m = l.toArray(new Message[l.size()]);
        
        MessageGroup group = new MessageGroup(m);
        List<String> users = sql.GetUsersExceptOne(conn.user.Username);
        group.Users = users.toArray(new String[users.size()]);
        for(int i=0; i<m.length; i++)
        {
            System.out.println("ZIO KHEN " + m[i].Username);
        }

        Gson gson = new GsonBuilder().create();
        String toBeOut = gson.toJson(group);
        System.out.println(toBeOut);

        conn.output.println(toBeOut);
    }

    public void UpdateMessages(Message message) throws SQLException, ClassNotFoundException
    {
        this.messages.add(message);
        this.sql.AddMessage(message);
        System.out.println("Numero di connessioni " + connections.size());
        for (int i = 0; i < connections.size(); i++)
        {
            ClientConnection c = connections.get(i);
            System.out.println("Client connection esiste? " + !c.equals(null));
            System.out.println("Message user " + message.Username);
            System.out.println("Cc user " + c.user.Username);
            if (!message.Username.equals(c.user.Username))
            {
                System.out.println("Eseguo il sender");
                c.executor.execute(new Sender(c, c.output, message));
            }
        }
    }
}

class ClientConnection implements Runnable
{

    Mixer mixer;
    Socket socket;
    ExecutorService executor;
    BufferedReader input;
    PrintWriter output;
    //  int id;

    public User user;
    public String toUser = "group";

    public ClientConnection(Socket socket, Mixer mixer) throws IOException
    {
        //this.id = index;
        this.mixer = mixer;
        this.socket = socket;
        this.user = new User();
        //user.Username = "cesare";
    }

    @Override
    public void run()
    {
        executor = Executors.newFixedThreadPool(2);
        Receiver receiver = new Receiver(this, input);
        executor.execute(receiver);
        System.out.println("Sto eseguendo il receiver");
    }
}

class Receiver implements Runnable
{

    ClientConnection connection;
    BufferedReader receiver;

    GregorianCalendar cal = new GregorianCalendar();

    public Receiver(ClientConnection connection, BufferedReader receiver)
    {
        this.receiver = receiver;
        this.connection = connection;
    }

    @Override
    public void run()
    {
        try
        {
            while (true)
            {
                System.out.println("Sto aspettando");
                String s = receiver.readLine();
                System.out.println("Ho ricevuto: " + s);

                //parse
                Message message;

                Gson gson = new GsonBuilder().create();
                message = gson.fromJson(s, Message.class);

                /*  message.User = "manuelelucchi";
                message.Date = cal.get(Calendar.HOUR) + ":" + cal.get(Calendar.MINUTE) + "," + cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.MONTH) + "/" + cal.get(Calendar.YEAR);
                message.Text = "Testo"; */
                if (message.Username != null)
                {
                    System.out.println("Inizio UpdateMessage");
                    try
                    {
                        connection.mixer.UpdateMessages(message);
                    } catch (SQLException ex)
                    {
                    } catch (ClassNotFoundException ex)
                    {
                    }
                }

            }

        } catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }

}

class Sender implements Runnable
{

    ClientConnection connection;
    PrintWriter sender;
    GregorianCalendar cal = new GregorianCalendar();
    Scanner scanner = new Scanner(System.in);
    String type = "server";
    Message message;

    public Sender(ClientConnection connection, PrintWriter sender, Message message)
    {
        this.sender = sender;
        this.message = message;
    }

    @Override
    public void run()
    {
        // for(int i= 0; i<1000; i++)
        {
            String output = "{" + "\"" + "Username" + "\"" + ":" + "\"" + message.Username + "\"" + "," + "\"" + "Text" + "\"" + ":" + "\"" + message.Text + "\"" + "," + "\"" + "Date" + "\"" + ":" + "\"" + message.Date + "\"" + "," + "\"" +  "ToUser" + "\"" + ":" + "\"" + message.ToUser + "\"" +"}";
            sender.println(output);
            // sender.print("Ciao");
            System.out.println("Ho inviato: " + output);
        }

    }

}
