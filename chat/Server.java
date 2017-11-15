package javafxapplication2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {

    private static final int NTHREDS = 10;
    static ArrayList<PrintWriter> clientList;
    static ArrayList<String> nameList;
    public static void main(String[] args) throws InterruptedException, IOException 
    {
        clientList = new ArrayList<PrintWriter>();
        nameList = new ArrayList<String>();

        System.out.println("Server attivo..");
        int clientNumber = 0;

        ServerSocket listener = new ServerSocket(9898);

        ExecutorService executor = Executors.newFixedThreadPool(NTHREDS);
        for (int i = 0; i < 500; i++) {
            System.out.println("for " + i + " del executor");
            Runnable worker = new MyThread(listener.accept(), clientNumber++);
            executor.execute(worker);
        }
        executor.shutdown();
    }
}

class MyThread implements Runnable 
{

    database d = new database();
    String[] dati;
    private Socket socket;
    private int clientNumber;
    PrintWriter out;

    public MyThread(Socket socket, int clientNumber) {
        this.socket = socket;
        this.clientNumber = clientNumber;
    }

    @Override
    public void run() 
    {
        try 
        {
        
            Server.clientList.add( new PrintWriter(socket.getOutputStream(), true));
     
         //   System.out.println(Server.clientList.get(clientNumber).toString());
          //  Server.clientList.get(0).println("ZIOCHEN");
            System.out.println("inizia il run");
            System.out.println("si è connesso: " + clientNumber);
      
            out = new PrintWriter(socket.getOutputStream(), true);
       
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            dati = new String[2];

            dati[0] = in.readLine();
            dati[1] = in.readLine();
            String fare = in.readLine();

            System.out.println("server: riceve le robe e le manda al database");
            if (fare.equals("registra")) {
                d.registrati(dati, out, in);
            } else {
                if (fare.equals("accedi")) {
                    d.accedi(dati, out, in);
                }
            }
            System.out.println("dovrebbe rivìcevere qualcosa");
            out.flush();
            String gne;
            gne = in.readLine();

            System.out.println("qualcosa?");  
            System.out.println(gne);

            if (gne.equals("CHIEDILISTA")) 
            {
                System.out.println("sul server, chiama il metodo");
                d.mandaLista(out, in);
            }
            int index = 0;
            while(true)
            {
                gne = in.readLine();
                if(gne.equals("MESSAGGI"))
                {
                    out.println("CHIEDIMESSAGGI");
                    System.out.println("gne: "+gne);
                    d.messaggia(socket, out, in);
                }
                else
                {
                    for(int i = 0; i<gne.length(); i++)
                    {
                        if(gne.charAt(i) == '$')
                        {
                            index = i;
                        }
                    }
                    if(gne.charAt(index)!='$')
                    {
                        System.out.println("gne: "+gne);
                        d.scrivi(socket, out, in, gne);
                    }
                    gne="MESSAGGI";
                }
            }
            
            
            
            
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(MyThread.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(MyThread.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (ClassNotFoundException ex) 
        {
            Logger.getLogger(MyThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
}
