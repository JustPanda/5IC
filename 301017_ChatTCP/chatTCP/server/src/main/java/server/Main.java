package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Main
{
	public static void main(String[] args)
	{
		final int PORT=6844;
		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
		ServerSocket server=null;
		SQLiteJDBC database=new SQLiteJDBC();
		Room room=new Room();
		try{
			server=new ServerSocket(PORT);
			System.out.println("Server online");
			while(true)
			{
				executor.execute(new User(server.accept(), database, room));
			}
		}catch(IOException e){
			e.printStackTrace();
		}

	}
}
