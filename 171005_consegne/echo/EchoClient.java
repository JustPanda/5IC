package echo;

import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Scanner;
import java.net.Socket;
import java.net.UnknownHostException;

class EchoClient
{
    private final static String ADDRESS="127.0.0.1";
    private final static int PORT=1723;

    public static void main(String[] args)
    {
        Scanner input=new Scanner(System.in);
        Socket s=null;
        BufferedReader in=null;
        PrintWriter out=null;
        try
        {
            s=new Socket(ADDRESS, PORT);
            in=new BufferedReader(new InputStreamReader(s.getInputStream()));
            out=new PrintWriter(s.getOutputStream(), true);
        }
        catch(UnknownHostException e)
        {
            e.printStackTrace();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        String str=null;

        while(true)
        {
            System.out.print("\nWrite something, EXIT to quit: ");
            out.println(input.nextLine());
            try
            {
                str=in.readLine();
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
            if(str==null)
                break;
            System.out.println(str);
        }
        System.out.println("\nConnection closed.");
    }
}
