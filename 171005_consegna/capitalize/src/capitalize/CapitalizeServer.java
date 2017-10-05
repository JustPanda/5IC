package capitalize;

import java.io.*;
import java.net.Socket;

public class CapitalizeServer extends Thread
{
	private int clientNumber;
	private Socket client;
	private Log log;

	CapitalizeServer(Socket client, Log log, int clientNumber)
	{
		this.client=client;
		this.log=log;
		this.clientNumber=clientNumber;
		start();
	}

	@Override
	public void run()
	{
		BufferedReader in;
		PrintWriter out;
		try{
			String input;
			in=new BufferedReader(new InputStreamReader(client.getInputStream()));
			out=new PrintWriter(client.getOutputStream(), true);
			log.write("Client #"+clientNumber+" connected");
			out.println("Hello, you are client #"+clientNumber+".");
			out.println("Enter a line with only a period to quit\n");
			while((input=in.readLine())!=null&&!input.equals("."))
			{
				log.write("Client #"+clientNumber+" asked to upper case: "+input);
				out.println(input.toUpperCase());
			}
		}catch(IOException ex) {
			ex.printStackTrace();
		}finally{
			try{
				client.close();
			}catch(IOException ex){
				ex.printStackTrace();
			}
			log.write("Close connection for client #"+clientNumber+".");
		}
	}
}
