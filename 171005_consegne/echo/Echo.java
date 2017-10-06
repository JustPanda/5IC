package echo;

import java.io.*;
import java.net.*;

class Echo extends Thread
{
    private final static int PORT=1723;
    private final static String STRING_FOR_QUIT="EXIT";
    private Socket client;
    private int clientNumber;
    private BufferedReader in;
    private PrintWriter out;

    Echo(Socket client, int clientNumber)
    {
        this.client=client;
        this.clientNumber=clientNumber;
        System.out.println("\nConnecting client number: "+clientNumber);
        try
        {
            in=new BufferedReader(new InputStreamReader(client.getInputStream()));
            out=new PrintWriter(client.getOutputStream(), true);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        this.start();
    }

    @Override
    public void run()
    {
        String s=null;
        while (true)
        {
            try
            {
                s=in.readLine();
                System.out.println("\nReceived: "+s+" from client number: "+clientNumber);
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
            if(s.equals(STRING_FOR_QUIT))
            {
                System.out.println("\nDisconnecting client number: "+clientNumber);
                break;
            }
            System.out.println("\nSending echo response to client "+clientNumber);
            out.println("ECHO " + s);
        }
        try
        {
            client.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String args[])
    {
        ServerSocket echoServer = null;
        int clientNumber=1;

        try
        {
            echoServer = new ServerSocket(PORT);
            System.out.println("\n\n\nRunning server...\n\nListening on port: "+PORT);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        Socket tmp=null;
        while(true)
        {
            try
            {
                tmp=echoServer.accept();

            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
            new Echo(tmp, clientNumber++);
        }
    }
}
