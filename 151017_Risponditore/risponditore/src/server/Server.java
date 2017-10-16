package server;

import java.io.IOException;
import java.io.File;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Server
{
	public static void main(String[] args)
	{
		final int PORT=6844, N_THREADS=10;
		final String QUESTIONS_FILE="file/pizzeria/Questions.txt", PRODUCTS_FILE="file/pizzeria/Products.txt";
		Scanner questionSc, productsSc;
		ServerSocket server;
		ExecutorService executor=Executors.newFixedThreadPool(N_THREADS);
		Operations operations=new Operations("Orders.txt");
		System.out.println("Server online");
		try{
			String position;
			HashMap<String, Node> allNode = new HashMap<>();
			HashMap<String, Product> products=new HashMap<>();
			server=new ServerSocket(PORT);
			questionSc=new Scanner(new File(QUESTIONS_FILE));
			productsSc=new Scanner(new File(PRODUCTS_FILE)).useDelimiter(",|\\n");
			while(productsSc.hasNextLine())
			{
				String name=productsSc.next();
				products.put(name, new Product(name, Double.parseDouble(productsSc.next())));
			}
			position=questionSc.nextLine();
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
			while(true)
			{
				executor.execute(new Service(server.accept(), operations, allNode, products, position));
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
