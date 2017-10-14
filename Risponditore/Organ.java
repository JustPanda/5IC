package pizzeria;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.lang.Object;

public class Organ 
{
    public static void main(String[] args) throws Throwable
    {
        System.out.println("Server running...");
        int Client = 0;
        ExecutorService executor = Executors.newCachedThreadPool();
        ServerSocket server = new ServerSocket(9090);
        try
        {
            while(true)
            {
                Server s = new Server(server.accept(), Client++);
                executor.execute(s);
            }
        }
        catch(Throwable e)
        {
            e.printStackTrace();
        }
    }
    
    static class Server implements Runnable
    {
        Socket client;
        int ClientNumber;
        BufferedReader in;
        PrintWriter out;
        Model m;
        ArrayList<String> temp;
        String shop = "";
        String cost = "";
        public Server(Socket client, int ClientNumber)
        {
            this.ClientNumber = ClientNumber;
            this.client = client;
            m = new Model();
            temp = new ArrayList<>();
        }
        
        @Override
        public void run()
        {
            try
            {
                int i=0;
                in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                out = new PrintWriter(client.getOutputStream(), true);
                m.Setup();
                m.Build();
                m.Assign();
                temp.add(m.Intro());
                while(!temp.get(i).toLowerCase().equals("Arrivederci".toLowerCase()))
                {
                    out.println(temp.get(i));
                    String answer = in.readLine();
                    if(answer.toLowerCase().equals("cuore") || answer.toLowerCase().equals("cervello") || answer.toLowerCase().equals("reni") || answer.toLowerCase().equals("polmoni"))
                    {
                        if(answer.toLowerCase().equals("cuore"))
                        {
                            this.shop += answer + ",";
                            this.cost += "3000" + ",";
                        }
                        if(answer.toLowerCase().equals("cervello"))
                        {
                            this.shop += answer + ",";
                            this.cost += "5000" + ",";
                        }
                        if(answer.toLowerCase().equals("reni"))
                        {
                            this.shop += answer + ",";
                            this.cost += "2000" + ",";
                        }
                        if(answer.toLowerCase().equals("polmoni"))
                        {
                            this.shop += answer + ",";
                            this.cost += "1000" + ",";
                        }
                    }
                    System.out.println("lista: " + shop + " costo: " + cost);
                    String question = m.Decide(temp.get(i), answer);
                    temp.add(question);
                    i++;
                }
                String[] s2 = cost.split(",");
				if(shop != "")
				{
					int n = 0;
					for(int j=0; j<s2.length; j++)
					{
						n+=Integer.parseInt(s2[j]);
					}
					out.println("Lei ha ordinato: [" + shop + "] al costo di: " +  n + ". " + temp.get(i));
				}
                else
				{
					out.println(temp.get(i));
				}
				this.client.shutdownInput();
				this.client.shutdownOutput();
                this.client.close();
                this.in.close();
                this.out.close();
            }
            catch(Throwable e)
            {
                e.printStackTrace();
            }
        }
    }
}


