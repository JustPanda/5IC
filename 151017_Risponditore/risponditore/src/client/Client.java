package client;

import server.Type;

import java.util.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;

public class Client implements Type
{
	public static void main(String[] args)
	{
		final int PORT=6844;
		final String IP="127.0.0.1";
		Scanner sc=new Scanner(System.in);
		Socket server;
		BufferedReader in;
		PrintWriter out;
		try{
			boolean notExit=true;
			String input, exitWords=null;
			server=new Socket(IP, PORT);
			in=new BufferedReader(new InputStreamReader(server.getInputStream()));
			out=new PrintWriter(server.getOutputStream(), true);
			System.out.print("Buongiorno, prego inserisca il suo nome: ");
			out.println(sc.nextLine());
			do{
				String type=in.readLine();
				switch(type)
				{
					case CHOOSE_TYPE: {
						boolean first=true, notSend;
						int position;
						final StringBuilder question=new StringBuilder(in.readLine());
						List<String> answers=new ArrayList<>(Arrays.asList(in.readLine().split(",")));
						List<String> prices=new ArrayList<>(Arrays.asList(in.readLine().split(",")));
						{
							final AtomicInteger index=new AtomicInteger(0);
							answers.forEach((s) -> {
								String priceTmp=prices.get(index.get());
								question.append("\n"+(index.addAndGet(1))+". "+s);
								if(!priceTmp.equals("null"))
								{
									question.append(" - "+priceTmp+"$");
								}
							});
						}
						System.out.println(question.toString());
						do{
							if(!first){
								System.out.println("L'opzione inserita non è valida");
							}else{
								first=false;
							}
							System.out.print(">");
							input=sc.nextLine();
							try{
								position=Integer.parseInt(input);
								notSend=position!=0&&position<=answers.size();
							}catch(NumberFormatException e){
								notSend=answers.contains(input);
							}
						}while(!notSend);
						out.println(input);
					} break;
					case INSERT_TYPE:
						System.out.print(in.readLine());
						do{
							out.println(sc.nextLine());
						}while(!Boolean.valueOf(in.readLine()));
						System.out.println("Elemento rimosso\n");
						out.println("");
						break;
					case ITEM_TYPE:
						System.out.println(in.readLine()+"\n");
						out.println("");
						break;
					case SHOW_TYPE: {
							final StringBuilder question=new StringBuilder(in.readLine());
							List<String> msg=new ArrayList<>(Arrays.asList(in.readLine().split(",")));
							msg.forEach((s) -> {question.append("\n"+s);});
							System.out.println(question.toString()+"\n");
							out.println("");
						} break;
					case EXIT_TYPE:
						notExit=false;
						exitWords=in.readLine();
						break;
				}
			}while(notExit);
			if(Boolean.valueOf(in.readLine()))
			{
				boolean first=false;
				System.out.print(in.readLine());
				do{
					if(first)
					{
						System.out.print(in.readLine());
					}else{
						first=true;
					}
					out.println(sc.nextLine());
				}while(Boolean.valueOf(in.readLine()));
				System.out.println(in.readLine());
			}
			System.out.println(exitWords);
			out.close();
			in.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
