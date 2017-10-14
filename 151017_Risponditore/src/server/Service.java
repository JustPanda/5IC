package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

class Service implements Runnable, Type
{
	private String name;
	private HashMap<String, Node> allNode = new HashMap<>();
	private HashMap<String, Double> products=new HashMap<>();
	private Socket client;
	private Node before, actual;
	private Receipt receipt=new Receipt();

	Service(Socket client, Scanner questionSc, Scanner productsSc)
	{
		this.client=client;
		productsSc.useDelimiter(",|\n");
		while(productsSc.hasNextLine())
		{
			products.put(productsSc.next(), Double.parseDouble(productsSc.next()));
		}
		products.forEach((k, v) -> System.out.println(k+" "+v));
		while(questionSc.hasNextLine())
		{
			System.out.println(questionSc.nextLine());
		}
	}

	@Override
	public void run()
	{
		BufferedReader in;
		PrintWriter out;
		try{
			in=new BufferedReader(new InputStreamReader(client.getInputStream()));
			out=new PrintWriter(client.getOutputStream(), true);
			name=in.readLine();
			String msg="introduzione";
			do{
				changePosition(msg);
				out.println(actual.type);
				actual.exec(receipt);
				switch(actual.type)
				{
					case CHOOSE_TYPE:
						out.println(String.format(actual.question, name));
						out.println(Arrays.toString(actual.answers).replace("[", "").replace("]", "").replace(", ", ","));
					break;
					case INSERT_TYPE:
						out.println(actual.question);
						break;
					case EXIT_TYPE:
						out.println(actual.question);
						break;
					case SHOW_TYPE:
						out.println(actual.print(receipt));
						break;
					case ITEM_TYPE:
						out.println(String.format(actual.question, actual.product.getPrice()));
						break;
				}
			}while(!((msg=in.readLine()).equals("EXIT")));
			in.close();
			out.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	private void changePosition(String msg)
	{
		String key;
		if(msg.equals(""))
		{
			key=actual.answersKey[0];
		}else{
			if(actual==null)
			{
				key=msg;
			}else{
				try{
					key=actual.answers[Integer.parseInt(msg)-1];
				}catch(NumberFormatException e){
					key=msg;
//					key=actual.answers[Arrays.asList(actual.answers).indexOf(msg)];
				}
			}
		}
		before=actual;
		actual=allNode.get(key);
	}
}
