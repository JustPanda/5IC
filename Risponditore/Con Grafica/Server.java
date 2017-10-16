package javafxapplication1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {
    private static final int NTHREDS = 10;

    public static void main(String[] args) throws InterruptedException, IOException {
        System.out.println("La pizzeria è aperta..");
        int clientNumber = 0;

        ServerSocket listener = new ServerSocket(9898);

        ExecutorService executor = Executors.newFixedThreadPool(NTHREDS);
        for (int i = 0; i < 500; i++) {
            Runnable worker = new MyThread( listener.accept(), clientNumber++);
            executor.execute(worker);
        }
        executor.shutdown();
    }
}




class MyThread implements Runnable {

    private Socket socket;
    private int clientNumber;
    private String[][] cibo = new String[10][10];
    private String[][] bevande = new String[10][10];
    List<String> pizze;
    List<String> bibite;

    public MyThread(Socket socket, int clientNumber) {
        this.socket = socket;
        this.clientNumber = clientNumber;
    }

    @Override
    public void run() 
    {
        try 
        {
            
            Pizzeria<String> albero = new Pizzeria<>();

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);


            System.out.println("si è connesso "+clientNumber);

            cibo[clientNumber][0] = in.readLine();

            System.out.println(cibo[clientNumber][0]+" è il nome del fra");

            bevande[clientNumber][0] = cibo[clientNumber][0];
            String q = in.readLine();
            if(q.equals("chiediPizze"))
            {
                
                String [][] listino = albero.getListinoPizze();
                for(int i = 0; i<listino.length; i++)
                {
                    System.out.println(listino[i][0]);
                    out.println(listino[i][0]);
                }
                out.println("STOP");

                pizze = new ArrayList<String>();
                pizze.add(0, cibo[clientNumber][0]);
 
                pizze = albero.getPizze( out, cibo[clientNumber][0], in, 0);
                System.out.println(pizze);
            }

            if(in.readLine().equals("chiediBibite"))
            {
                //////////////
                
                for(int i = 1; i<pizze.size(); i++)
                {
                    out.println(pizze.get(i));
                }
                out.println(" per un totale di: "+pizze.get(0)+"€");
                out.println("STOP");
                
                ///////////////
                String [][][] listinoBibite = albero.getlistinoBibite();
                for(int  i = 0; i<listinoBibite.length; i++)
                {
                    out.println(listinoBibite[i][0][0]);
                }                
                out.println("STOP");
                
                bibite = albero.getBibite(out, bevande[clientNumber][0], in, 0);
                bibite.add(0, bevande[clientNumber][0]);
                System.out.println(bibite);
            }
            
            if(in.readLine().equals("chiediTutto"))
            {
                for(int i = 2; i<bibite.size(); i++)
                {
                    out.println(bibite.get(i));
                }
                out.println(" per un totale di: "+bibite.get(1)+"€");
                out.println("STOP");
                
            }
            
        } catch (IOException ex)
        {
            Logger.getLogger(MyThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
