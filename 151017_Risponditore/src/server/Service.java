package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
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
	private Operations operations;

	Service(Socket client, Scanner questionSc, Scanner productsSc, Operations operations)
	{
		this.client=client;
		this.operations=operations;
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
			String[] mapsData;
			in=new BufferedReader(new InputStreamReader(client.getInputStream()));
			out=new PrintWriter(client.getOutputStream(), true);
			name=in.readLine();
			do{
				if(actual!=null)
				{
					actual.exec(operations, products.get(keyProduct), receipt);
				}
				changePosition(msg);
				out.println(actual.getType());
				keyProduct=actual.printToClient(new Object[]{in, out, actual, products, receipt, name}, operations);
				msg=(keyProduct.equals(EXIT_TYPE)?null:in.readLine());
			}while(msg!=null);
			if(receipt.getPrice()!=0)
			{
				operations.writeOrder(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date())+"\n\n"+name+" ha ordinato: \n"+receipt.printElements("\n")+"Al prezzo di: "+receipt.getPrice()+"$\n");
				operations.getDistanceAndTime(in.readLine());
			}
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
			key=actual.getAnswers()[0];
		}else{
			if(actual==null)
			{
				key=msg;
			}else{
				try{
					key=actual.getAnswers()[Integer.parseInt(msg)-1];
				}catch(NumberFormatException e){
					key=msg;
				}
			}
		}
		before=actual;
		actual=allNode.get(key);
	}
}
