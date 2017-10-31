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
/**
 *
 * @author manue
 */
public class Server
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException
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

    public void Start() throws IOException
    {
        ServerSocket s = new ServerSocket(8080);
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        int clientIndex = 0;
        while (clientIndex <= 1000)
        {
            clientIndex++;
            Socket socket = (Socket) s.accept();
            ClientConnection client = new ClientConnection(socket, this);
            System.out.println("Connesso al client n°" + clientIndex);
            connections.add(client);
            executor.execute(client);
        }
        executor.shutdown();
        s.close();
    }

    public void UpdateMessages(Message message)
    {
        this.messages.add(message);
        for (int i = 0; i < connections.size(); i++)
        {
            ClientConnection c = connections.get(i);
            System.out.println("Client connection esiste?" + !c.equals(null));
            System.out.println("Message user" + message.User);
            System.out.println("Cc user" + c.user.Username);
            if (!message.User.equals(c.user.Username))
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

    public ClientConnection(Socket socket, Mixer mixer) throws IOException
    {
        //this.id = index;
        this.mixer = mixer;
        this.socket = socket;
        this.output = new PrintWriter(socket.getOutputStream(), true);
        this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.user = new User();
        user.Username = "cesare";
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
                System.out.println("Inizio UpdateMessage");
                connection.mixer.UpdateMessages(message);
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
            String output = "{" + "\"" + "User" + "\"" + ":" + "\"" + message.User + "\"" + "," + "\"" + "Text" + "\"" + ":" + "\"" + message.Text + "\"" + "," + "\"" + "Date" + "\"" + ":" + "\"" + message.Date + "\"" + "}";
            sender.println(output);
            // sender.print("Ciao");
            System.out.println("Ho inviato: " + output);
        }

    }

}
