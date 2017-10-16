package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

class Service implements Runnable, Type
{
	private String name;
	private HashMap<String, Node> allNode = new HashMap<>();
	private HashMap<String, Product> products=new HashMap<>();
	private Socket client;
	private Node before, actual;
	private Receipt receipt=new Receipt();
	private Operations operations=new Operations();

	Service(Socket client, Scanner questionSc, Scanner productsSc)
	{
		this.client=client;
		productsSc=productsSc.useDelimiter(",|\\n");
		while(productsSc.hasNextLine())
		{
			String name=productsSc.next();
			products.put(name, new Product(name, Double.parseDouble(productsSc.next())));
		}
		while(questionSc.hasNextLine())
		{
			String question, answer;
			String[] first=questionSc.nextLine().split(":");
			LinkedList<String> answers=new LinkedList<>();
			question=questionSc.nextLine().trim();
			while(questionSc.hasNextLine()&&!(answer=questionSc.nextLine()).isEmpty())
			{
				answers.add(answer.trim());
			}
			allNode.put(first[0], new Node(first[0], first[1], question, answers.toArray(new String[answers.size()])));
		}
	}

	@Override
	public void run()
	{
		BufferedReader in;
		PrintWriter out;
		try{
			String msg="start", keyProduct=null;
			in=new BufferedReader(new InputStreamReader(client.getInputStream()));
			out=new PrintWriter(client.getOutputStream(), true);
			name=in.readLine();
			do{
				if(actual!=null) {
					actual.exec(products.get(keyProduct), receipt);
				}
				changePosition(msg);
				out.println(actual.type);
				switch(actual.type)
				{
					case CHOOSE_TYPE:
						String[] prices=new String[actual.answers.length];
						for(int i=0;i<prices.length;i++)
						{
							Product product=products.get(actual.answers[i]);
							if(product!=null)
							{
								prices[i]=String.valueOf(product.getPrice());
							}
						}
						out.println(String.format(actual.question, name));
						out.println(listToString(actual.answers));
						out.println(listToString(prices));
						break;
					case ITEM_TYPE:
						out.println(String.format(actual.question, products.get(actual.key).getPrice()));
						keyProduct=actual.key;
						break;
					case INSERT_TYPE:
						out.println(actual.question);
						while(products.get((keyProduct=in.readLine()))==null)
						{
							out.println(false);
						}
						out.println(true);
						break;
					case SHOW_TYPE:
						out.println(String.format(actual.question, receipt.getPrice()));
						out.println(receipt.printElements());
						break;
					case EXIT_TYPE:
						out.println(actual.question);
						break;
				}
			}while((msg=in.readLine())!=null);
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
			key=actual.answers[0];
		}else{
			if(actual==null)
			{
				key=msg;
			}else{
				try{
					key=actual.answers[Integer.parseInt(msg)-1];
				}catch(NumberFormatException e){
					key=msg;
				}
			}
		}
		before=actual;
		actual=allNode.get(key);
	}

	private String listToString(String[] elements)
	{
		StringBuilder stringElements=new StringBuilder("");
		for(String element: elements) stringElements.append(element+",");
		return stringElements.toString();
	}
}
