package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

class Service implements Runnable, Type
{
	private String position;
	private HashMap<String, Node> allNode;
	private HashMap<String, Product> products;
	private Socket client;
	private Node actual;
	private Receipt receipt;
	private Operations operations;

	Service(Socket client, Operations operations, HashMap<String, Node> allNode, HashMap<String, Product> products, String position)
	{
		this.client=client;
		this.operations=operations;
		this.allNode=allNode;
		this.products=products;
		this.position=position;
		this.receipt=new Receipt();
	}

	@Override
	public void run()
	{
		BufferedReader in;
		PrintWriter out;
		try{
			boolean buySomething;
			String msg="start", keyProduct=null, name;
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
				keyProduct=actual.printToClient(new Object[]{in, out, actual, products, receipt, new String[]{name, position}}, operations);
				msg=(keyProduct.equals(EXIT_TYPE)?null:in.readLine());
			}while(msg!=null);
			out.println((buySomething=receipt.getPrice()!=0));
			if(buySomething)
			{
				boolean first=true;
				String mapsData, address;
				operations.writeOrder(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date())+"\n\n"+name+" ha ordinato: \n"+receipt.printElements("\n")+"Al prezzo di: "+String.format("%.2f", receipt.getPrice())+"$\n");
				out.println("Prego, scriva il suo indirizzo di casa: ");
				do{
					if(first){
						first=false;
					}else{
						out.println(true);
						out.println("Errore con il calcolo del tempo, prego reinserisca di nuovo i dati: ");
					}
					address=in.readLine();
					mapsData=operations.getDistanceAndTime(position.replace(" ", "+").toLowerCase(), address.replace(" ", "+").toLowerCase());
				}while(mapsData==null);
				out.println(false);
				out.println("Il tempo di arrivo del suo ordine e' di circa: "+mapsData);
				operations.writeOrder(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date())+"\n\n"+name+" ha ordinato: \n"+receipt.printElements("\n")+"Al prezzo di: "+String.format("%.2f", receipt.getPrice())+"$\nIndirizzo: "+address+"\n");
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
		actual=allNode.get(key);
	}
}
