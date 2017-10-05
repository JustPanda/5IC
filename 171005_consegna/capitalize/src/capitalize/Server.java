package capitalize;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Date;

public class Server
{
	public static void main(String[] args)
	{
		final int PORT=6844;
		final String PATH_FILE="log.txt";
		int indexClient=0;
		ServerSocket server;
		Log log=new Log(PATH_FILE);
		try{
			server=new ServerSocket(PORT);
			System.out.println("Server capitalize online on: 127.0.0.1:"+PORT+"\nWaiting for client...");
			log.writeOnlyInFile("------"+new Date().toString()+"------");
			while(true)
			{
				new CapitalizeServer(server.accept(), log, indexClient++);
			}
		}catch(IOException ex){
			ex.printStackTrace();
			log.write("Error with server: "+ex);
		}
	}
}
