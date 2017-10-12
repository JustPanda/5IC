package server;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.net.ServerSocket;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Server
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
				executor.execute(new Task(server.accept(), new Pizzeria(getArray())));
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	static String[] getArray()
	{
		String[] toReturn=null;
		LinkedList<String> tmp=new LinkedList<>();
		Scanner sc=null;
		try{
			sc=new Scanner(new File("file/pizzeria.txt"));
			while(sc.hasNextLine())
			{
				tmp.add(sc.nextLine());
			}
			toReturn=new String[tmp.size()];
			for(int i=0;i<toReturn.length;i++)
			{
				toReturn[i]=tmp.get(i);
			}
		}catch (FileNotFoundException e){
			e.printStackTrace();
		}
		return toReturn;
	}
}
