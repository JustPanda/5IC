package date;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.DataOutputStream;
import java.net.Socket;

public class DateClient
{
    private final static int PORT=1723;
    private final static String ADDRESS="127.0.0.1";

    public static void main(String[] args) throws IOException
    {
        Socket s=new Socket(ADDRESS, PORT);
        BufferedReader in=new BufferedReader(new InputStreamReader(s.getInputStream()));
        String str="DATA RECEIVED: ";
        str+=in.readLine();
        System.out.println(str);
        System.out.println("\nConnection closed by server");
    }
}
