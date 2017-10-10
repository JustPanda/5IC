package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server
{
	public static void main(String[] args)
	{
		final int PORT=80, N_THREADS=10;
		ServerSocket server;
		ExecutorService executor=Executors.newFixedThreadPool(N_THREADS);
		System.out.println("Server online");
		try{
			server=new ServerSocket(PORT);
			while(true)
			{
				executor.execute(new Task(server.accept()));
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
