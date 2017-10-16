package risponditore;

/*java.util*/
import java.util.Scanner;

/*java.io*/
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.IOException;

/*java.net*/
import java.net.Socket;

class Client
{
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    private final static String ADDRESS="127.0.0.1";
    private final static int PORT=1723;

    Client(Socket socket)
    {
        this.socket=socket;
        try
        {
            in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out=new PrintWriter(socket.getOutputStream(), true);
            startServerCommunication();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    private void startServerCommunication() throws IOException
    {
        Scanner input=new Scanner(System.in);

        boolean exit=false;
        while(!exit)
        {
            switch(in.readLine())
            {
                case "receive": System.out.println("\n"+in.readLine()); break;
                case "send": out.println(input.nextLine()); break;
                case "exit": exit=true;
            }
        }

        System.out.println("\nSei stato disconnesso.");
    }

    public static void main(String[] args)
    {
        try
        {
            System.out.println("Attendere la connessione senza premere alcun tasto");
            Thread.sleep(3000);
            new Client(new Socket(ADDRESS, PORT)); //effettua la connessione
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}
