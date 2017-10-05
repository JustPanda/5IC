package capitalize;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class CapitalizeClient
{
	private BufferedReader in;
	private PrintWriter out;
	private BufferedReader stdIn;

	public void connectToServer()
	{
		final int PORT=6844;
		String userInput;
		final String SERVER_ADDRESS = "127.0.0.1";

		Socket socket;
		try{
			boolean notExit=true;
			String msg=null;
			socket=new Socket(SERVER_ADDRESS, PORT);
			in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out=new PrintWriter(socket.getOutputStream(), true);
			stdIn=new BufferedReader(new InputStreamReader(System.in));
			for(int i=0;i<3;i++)
			{
				System.out.println(in.readLine());
			}
			System.out.println("Write \".\" to exit");
			while(notExit)
			{
				System.out.print(">");
				if((userInput=stdIn.readLine()).trim().isEmpty())
				{
					System.out.println("No empty string");
				}else{
					out.println(userInput);
					if(notExit=((msg=in.readLine())!=null&&!msg.equals(".")))
					{
						System.out.println("server responds: "+msg);
					}
				}
			}
		}catch(IOException ex)
		{
			ex.printStackTrace();
		}
	}
}
