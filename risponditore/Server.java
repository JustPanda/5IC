package risponditore;

/*java.util*/
import java.util.Scanner;
import java.util.LinkedList;
import java.util.concurrent.*;

/*java.io*/
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/*java.net*/
import java.net.ServerSocket;
import java.net.Socket;

class Server
{
    private final static int PORT=1723;
    private final static int MAX_CLIENTS=10;

    public static void main(String[] args) throws InterruptedException
    {
        ServerSocket server=null;
        try
        {
            server=new ServerSocket(PORT);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        System.out.println("\n\nThe server is running\nListening on port: "+PORT+"\n\nPress CTRL+C in any moment to shutdown the server\n");

        ExecutorService executor=Executors.newFixedThreadPool(MAX_CLIENTS);
        try
        {
            int clientNumber=1;
            while(true)
            {
                executor.execute(new Connection(server.accept(), clientNumber++));
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            executor.shutdown();
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            System.out.println("Finished");
        }
    }
}

class Connection implements Runnable
{
    private Socket client;
    private int clientNumber;
    private BufferedReader in;
    private PrintWriter out;

    Connection(Socket client, int clientNumber)
    {
        this.client=client;
        this.clientNumber=clientNumber;
        try
        {
            in=new BufferedReader(new InputStreamReader(client.getInputStream()));
            out=new PrintWriter(client.getOutputStream(), true);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void run()
    {
        System.out.println("\nConnection established with client #"+clientNumber);
        Reception r=new Reception(clientNumber, in, out);
        out.println("receive");
        // out.println(1);
        out.println("Buongiorno, benvenuto alla nostra reception, offriamo una vasta gamma di servizi, cosa vuole prenotare?");

        LinkedList<String> receipt=r.startDialog();

        out.println("receive");
        out.println("IL SUO ORDINE: ");

        System.out.println("Client #"+clientNumber+" has completed the reservation");

        for(String s:receipt)
        {
            try
            {
                Thread.sleep(800);
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }
            out.println("receive");
            out.println(s);
        }

        boolean exit=false;
        while(!exit)
        {
            out.println("receive");
            out.println("Conferma l'ordine?(s/n)");
            out.println("send");
            try
            {
                switch(in.readLine().toLowerCase().charAt(0))
                {
                    case 's':
                        System.out.println("Client #"+clientNumber+" has confirmed the reservation for an amount of:"+receipt.peekLast().substring(7, receipt.peekLast().length()));
                        out.println("receive");
                        out.println("Ordine confermato!");
                        Thread.sleep(1000);
                        out.println("receive");
                        out.println("Grazie per averci scelto, Speriamo di servirla nuovamente presto!");
                        Thread.sleep(1000);
                        out.println("receive");
                        out.println("Arrivederci");
                        exit=true;
                        break;

                    case 'n':
                        System.out.println("Client #"+clientNumber+" has canceled the reservation");
                        out.println("receive");
                        out.println("L\'ordine è stato annullato");
                        Thread.sleep(1000);
                        out.println("receive");
                        out.println("Ora verrà automaticamente disconnesso, se vuole effettuare una nuova prenotazione, si riconnetta");
                        Thread.sleep(3000);
                        out.println("receive");
                        out.println("Arrivederci");
                        exit=true;
                        break;

                    default: out.println("receive"); out.println("Non ho capito");
                }
            }
            catch(IndexOutOfBoundsException e)
            {
                out.println("receive");
                out.println("Non ho capito");
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }
        }

        out.println("exit");

        try
        {
            client.close();
            System.out.println("Client #"+clientNumber+" has been disconnected");
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}
