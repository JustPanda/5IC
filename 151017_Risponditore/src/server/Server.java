package server;

import java.io.IOException;
import java.io.File;
import java.net.ServerSocket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Server
{
	public static void main(String[] args)
	{
		final int PORT=80, N_THREADS=10;
		String service="pizzeria";
		Scanner questionSc, productsSc;
		ServerSocket server;
		ExecutorService executor=Executors.newFixedThreadPool(N_THREADS);
		System.out.println("Server online");
		try{
			server=new ServerSocket(PORT);
			questionSc=new Scanner(new File("file/"+service+"/Questions.txt"));
			productsSc=new Scanner(new File("file/"+service+"/Products.txt"));
			while(true)
			{
				executor.execute(new Service(server.accept(), questionSc, productsSc));
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
