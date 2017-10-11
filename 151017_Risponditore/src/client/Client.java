package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client
{
	public static void main(String[] args)
	{
		final int PORT=80;
		final String IP="127.0.0.1";
		Scanner sc=new Scanner(System.in);
		Socket server;
		BufferedReader in;
		PrintWriter out;
		try{
			String msg, input="";
			server=new Socket(IP, PORT);
			in=new BufferedReader(new InputStreamReader(server.getInputStream()));
			out=new PrintWriter(server.getOutputStream(), true);
			System.out.print("Client connected. Insert name: ");
			out.println(sc.nextLine());
			System.out.println(in.readLine().replace("*", "\n"));
			do{
				System.out.print(">");
				msg=sc.nextLine();
				out.println(msg);
				input=in.readLine();
				if(input!=null)
				{
					System.out.println(input.replace("*", "\n"));
				}
			}while(!msg.equals("EXIT")&&input!=null);
			in.close();
			out.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
