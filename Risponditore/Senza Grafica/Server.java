package pizzeria;

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
import java.util.concurrent.TimeUnit;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import pizzeria.Pizzeria;

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

    public MyThread(Socket socket, int clientNumber) {
        this.socket = socket;
        this.clientNumber = clientNumber;
    }

    public void run() 
    {
        try 
        {
            List<String> pizze;
            List<String> bibite;
            Pizzeria<String> albero = new Pizzeria<>();
            
            BufferedReader in = null;
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            
            cibo[clientNumber][0] =  in.readLine();
            bevande[clientNumber][0] = cibo[clientNumber][0];
            
            pizze = albero.getPizze( out, cibo[clientNumber][0], in, 0);    
            pizze.add(0, cibo[clientNumber][0]);
            System.out.println(pizze);
            
            bibite = albero.getBibite(out, bevande[clientNumber][0], in, 0);
            bibite.add(0, bevande[clientNumber][0]);
            System.out.println(bibite);         
        } catch (IOException ex)
        {
            Logger.getLogger(MyThread.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
}

