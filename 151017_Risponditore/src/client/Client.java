package client;

import server.Type;

import java.util.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client implements Type
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
			boolean notExit=true;
			String input;
			server=new Socket(IP, PORT);
			in=new BufferedReader(new InputStreamReader(server.getInputStream()));
			out=new PrintWriter(server.getOutputStream(), true);
			System.out.print("Client connected. Insert name: ");
			out.println(sc.nextLine());
			do{
				String type=in.readLine();
				switch(type)
				{
					case CHOOSE_TYPE: {
						boolean first=true, notSend;
						int position;
						String question=in.readLine();
						String answers=in.readLine();
						List<String> msg=new ArrayList<>(Arrays.asList(answers.split(",")));
						System.out.println(merge(question, msg));
						do{
							if(!first){
								System.out.println("No option available");
							}else{
								first=false;
							}
							System.out.print(">");
							input=sc.nextLine();
							try{
								position=Integer.parseInt(input);
								notSend=position!=0&&position<=msg.size();
							}catch(NumberFormatException e){
								notSend=msg.contains(input);
							}
						}while(!notSend);
						out.println(input);
					} break;
					case INSERT_TYPE:
						System.out.println(in.readLine());
						break;
					case ITEM_TYPE:
						System.out.println(in.readLine());
						out.println("");
						break;
					case SHOW_TYPE:
						System.out.println(in.readLine().replace("|", "\n"));
						out.println("");
					case EXIT_TYPE:
						notExit=false;
						break;
				}
			}while(notExit);
			in.close();
			out.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	private static String merge(String question, List<String> answers)
	{
		for(int i=0;i<answers.size();i++)
		{
			question+="\n"+(i+1)+". "+answers.get(i).trim();
		}
		return question;
	}
}
