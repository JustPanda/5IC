package dateserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class DateClient
{
	public static void main(String[] args)
	{
		final int PORT=9090;
		final String IP="127.0.0.1";
		Socket server=null;
		BufferedReader in;
		PrintStream out;
		try{
			server=new Socket(IP, PORT);
			in=new BufferedReader(new InputStreamReader(server.getInputStream()));
			System.out.println("Date is: "+in.readLine());
		}catch(IOException ex){
			ex.printStackTrace();
		}finally {
			try{
				if(server!=null)
					server.close();
			}catch(IOException ex){
				ex.printStackTrace();
			}
		}
	}
}
