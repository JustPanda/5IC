package echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class ClientEcho
{
	public static void main(String[] args)
	{
		final int PORT=9999;
		final String IP="127.0.0.1";
		Socket server;
		BufferedReader in, stdin;
		PrintWriter out;
		try {
			boolean notExit=true;
			String msg;
			server=new Socket(IP, PORT);
			in=new BufferedReader(new InputStreamReader(server.getInputStream()));
			stdin=new BufferedReader(new InputStreamReader(System.in));
			out=new PrintWriter(server.getOutputStream(), true);
			System.out.println("Connection to the successful server\nWrite \"EXIT\" to exit");
			while(notExit)
			{
				System.out.print(">");
				msg=stdin.readLine();
				if(msg.trim().isEmpty())
				{
					System.out.println("No empty string");
				}else{
					out.println(msg);
					if((notExit=!msg.equals("EXIT")))
					{
						System.out.println(in.readLine());
					}
				}
			}
		}catch(IOException ex){
			ex.printStackTrace();
		}
	}
}
