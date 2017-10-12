package client;

import sun.awt.image.ImageWatched;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
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
			String input;
			ArrayList<String> msg;
			server=new Socket(IP, PORT);
			in=new BufferedReader(new InputStreamReader(server.getInputStream()));
			out=new PrintWriter(server.getOutputStream(), true);
			System.out.print("Client connected. Insert name: ");
			out.println(sc.nextLine());
			do{
				boolean first=true;
				msg=new ArrayList<>(elaborate(in.readLine()));
				System.out.println(msg.get(0));
				msg.remove(0);
				do{
					if(!first){
						System.out.println("No option available");
					}else{
						first=false;
					}
					System.out.print(">");
					input=sc.nextLine();
				}while(!msg.contains(input));
				out.println(input);
//				input=in.readLine();
//				if(input!=null)
//				{
//					System.out.println(input.replace("*", "\n"));
//				}
			}while(!msg.equals("EXIT")&&input!=null);
			in.close();
			out.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	static LinkedList<String> elaborate(String toElaborate)
	{
		int limit=toElaborate.indexOf("->");
		System.out.println(toElaborate);
		LinkedList<String> toReturn=new LinkedList<>();
		toReturn.add(toElaborate.substring(0, limit));
		{
			String[] tmp=toElaborate.substring(limit+2).split(",");
			for (int i=0;i<tmp.length;i++)
			{
				toReturn.set(0, toReturn.get(0)+"\n"+(i+1)+". "+tmp[i]);
				toReturn.add(tmp[i]);
			}
		}
		return toReturn;
	}
}
