/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package risponditore;

import java.net.*;
import java.util.*;
import java.io.*;

/**
 *
 * @author manue
 */
public class Client
{

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
        System.exit(2);
    }
}
