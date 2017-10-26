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
		try{
			server=new ServerSocket(PORT);
			while(true)
			{
				executor.execute(new User(server.accept(), ));
			}
		}catch(IOException e){
			e.printStackTrace();
		}

	}
}
