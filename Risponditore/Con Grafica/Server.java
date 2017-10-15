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
import java.util.concurrent.TimeUnit;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafxapplication1.Pizzeria;


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

    @Override
    public void run() 
    {
        try 
        {
            List<String> pizze;
            List<String> bibite;
            Pizzeria<String> albero = new Pizzeria<>();
            
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

             System.out.println("si è connesso "+clientNumber);
            //AHAHHAHAHHHHHHHHHHHHHHHHHHHHHHHHHH
            cibo[clientNumber][0] = in.readLine();
            
            System.out.println(cibo[clientNumber][0]);
            
            
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

/*
public class Server
{
	public static void main(String[] args) throws Exception 
	{
		System.out.println("La pizzeria è aperta..");
        int clientNumber = 0;
        ServerSocket listener = new ServerSocket(9898);
		String [][] pizze = {{"oleo","euro"},{"oleo","euro"}};
		
		try 
		{
            while (true) 
			{
                // crea il thread e lo lancia
                new MyThread(pizze, listener.accept(), clientNumber++).start();
            }
        } finally 
		{
            listener.close();
        }
	}
}


class MyThread extends Thread 
{
	private Socket socket;
	private int clientNumber;
	private String[][] mem;
	private String[][] pizze;

	public MyThread(String[][] pizze, Socket socket, int clientNumber) 
	{
		this.pizze = pizze;
		this.socket = socket;
		this.clientNumber = clientNumber-1;
	}

	public void run()
	{
		try 
		{
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

			out.println("il cliente: " + clientNumber + " è entrato " + socket+". Dimmi il tuo nome: ");
			mem[clientNumber][0] = in.readLine();
			
			System.out.println("Ecco le pizze e il loro costo: ");
			for(int i = 0; i<pizze.length; i++)
			{
				System.out.println(pizze[i][0].toUpperCase()+" : "+ pizze[i][1].toUpperCase()+"€");
			}
		}
		catch(Exception e)
		{
			System.out.println("uhhh something went wrong :(");
		};
	}
}

 */
