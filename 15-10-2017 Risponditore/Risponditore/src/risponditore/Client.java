package risponditore;

import java.net.*;
import java.util.*;
import java.io.*;

/**
 *
 * @author Manuele Lucchi
 */
public class Client
{
    //Metodo main che riceve e invia in continuazione dal e al server
    public static void main(String[] args) throws IOException
    {
        boolean isExit = false;

        Socket s = new Socket("localhost", 8080);

        PrintWriter writer = new PrintWriter(s.getOutputStream(), true);
        BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream()));

        Scanner scanner = new Scanner(System.in);
        System.out.println(reader.readLine());
        System.out.println(reader.readLine());
        while (!isExit)
        {
            String output;
            output = scanner.nextLine();
            if ("exit".equals(output.toLowerCase()))
            {
                writer.println(output);
                isExit = true;
            } else
            {
                writer.println(output);
                String input = reader.readLine();
                System.out.println(input);
            }

        }
        writer.close();
        reader.close();
        s.close();
        System.exit(2);
    }
}
