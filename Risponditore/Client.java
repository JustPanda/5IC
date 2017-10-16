/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rispoinditore2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author davide
 */
public class Client
{
    public static void main(String[] args)
    {
	try
	{
	    BufferedReader buff;
	    PrintWriter out;
	    BufferedReader input;
	    String serverAddress = "localhost";
	    String action;
	    Socket socket = new Socket(serverAddress, 8080);
	    buff = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	    out = new PrintWriter(socket.getOutputStream(), true);
	    input = new BufferedReader(new InputStreamReader(System.in));
	    int i = 0;
	    do
	    {
		switch (i)
		{
		    case 0:

			for (int k = 0; k < 3; k++)
			{
			    System.out.println(buff.readLine());
			}
			action = input.readLine();
			out.println(action);
			if (action.toUpperCase().equals("CARRY ON"))
			{
			    i++;
			}
			break;

		    case 1:
			for (int k = 0; k < 3; k++)
			{
			    System.out.println(buff.readLine());
			}

			action = input.readLine();
			out.println(action);
			if (action.toUpperCase().equals("YES"))
			{
			    i = 2;
			}
			if (action.toUpperCase().equals("NO"))
			{
			    i = 12;
			}
			break;
		    case 2:
			for (int k = 0; k < 4; k++)
			{
			    System.out.println(buff.readLine());
			}
			action = input.readLine();
			out.println(action);
			if (action.toUpperCase().equals("NO PROBLEM"))
			{
			    i++;
			}
			break;
		    case 3:
			for (int k = 0; k < 3; k++)
			{
			    System.out.println(buff.readLine());
			}
			action = input.readLine();
			out.println(action);
			if (action.toUpperCase().equals("HOUSE SPECIALITY"))
			{
			    i++;
			}
			break;
		    case 4:
			for (int k = 0; k < 5; k++)
			{
			    System.out.println(buff.readLine());
			}

			action = input.readLine();
			out.println(action);
			if (action.toUpperCase().equals("YES OF COURSE"))
			{
			    i++;
			}
			break;
		    case 5:
			for (int k = 0; k < 3; k++)
			{
			    System.out.println(buff.readLine());
			}

			action = input.readLine();
			out.println(action);
			if (action.toUpperCase().equals("ASK"))
			{
			    i++;
			}
			break;
		    case 6:
			for (int k = 0; k < 9; k++)
			{
			    System.out.println(buff.readLine());
			}

			action = input.readLine();
			out.println(action);
			if (action.toUpperCase().equals("INTERESTING"))
			{
			    i++;
			}
			break;
		    case 7:
			for (int k = 0; k < 5; k++)
			{
			    System.out.println(buff.readLine());
			}

			action = input.readLine();
			out.println(action);
			if (action.toUpperCase().equals("OBVIOUSLY SEARCH HIM"))
			{
			    i++;
			}
			if (action.toUpperCase().equals("KEEP EATING AND WAIT"))
			{
			    i = 11;
			}
			break;
		    case 8:
			for (int k = 0; k < 6; k++)
			{
			    System.out.println(buff.readLine());
			}

			action = input.readLine();
			out.println(action);
			if (action.toUpperCase().equals("GET IN HIS WAY"))
			{
			    i++;
			}
			if (action.toUpperCase().equals("LOOK AROUND"))
			{
			    i = 10;
			}
			break;
		    case 9:
			for (int k = 0; k < 3; k++)
			{
			    System.out.println(buff.readLine());
			}
			action = input.readLine();
			out.println(action);
			i = 25;
			break;
		    case 10:
			for (int k = 0; k < 6; k++)
			{
			    System.out.println(buff.readLine());
			}

			action = input.readLine();
			out.println(action);
			i = 25;
			break;
		    case 11:
			for (int k = 0; k < 5; k++)
			{
			    System.out.println(buff.readLine());
			}

			action = input.readLine();
			out.println(action);
			i = 25;
			break;
		    case 12:
			for (int k = 0; k < 5; k++)
			{
			    System.out.println(buff.readLine());
			}

			action = input.readLine();
			out.println(action);
			if (action.toUpperCase().equals("NO PROBLEM AT ALL"))
			{
			    i = 14;
			}
			if (action.toUpperCase().equals("YES I KNOW"))
			{
			    i=13;
			}
			break;
		    case 13:
			for (int k = 0; k < 5; k++)
			{
			    System.out.println(buff.readLine());
			}

			action = input.readLine();
			out.println(action);
			i = 16;
			break;
		    case 14:
			for (int k = 0; k < 5; k++)
			{
			    System.out.println(buff.readLine());
			}

			action = input.readLine();
			out.println(action);
			i++;
			break;
		    case 15:
			for (int k = 0; k < 5; k++)
			{
			    System.out.println(buff.readLine());
			}

			action = input.readLine();
			out.println(action);
			i++;
			break;
		    case 16:
			for (int k = 0; k < 6; k++)
			{
			    System.out.println(buff.readLine());
			}

			action = input.readLine();
			out.println(action);
			if (action.toUpperCase().equals("NO IT DOESN'T"))
			{
			    i++;
			}
			if (action.toUpperCase().equals("TO BE HONEST..."))
			{
			    i = 20;
			}
			break;
		    case 17:
			for (int k = 0; k < 11; k++)
			{
			    System.out.println(buff.readLine());
			}

			action = input.readLine();
			out.println(action);
			if (action.toUpperCase().equals("SAY THANK YOU"))
			{
			    i++;
			}
			if (action.toUpperCase().equals("HEAD BACK HOME"))
			{
			    i = 23;
			}
			break;
		    case 18:
			for (int k = 0; k < 4; k++)
			{
			    System.out.println(buff.readLine());
			}

			action = input.readLine();
			out.println(action);
			if (action.toUpperCase().equals("YES I'D LOVE TO"))
			{
			    i++;
			}
			if (action.toUpperCase().equals("NO I'M SORRY"))
			{
			    i = 21;
			}
			break;
		    case 19:
			for (int k = 0; k < 3; k++)
			{
			    System.out.println(buff.readLine());
			}

			action = input.readLine();
			out.println(action);
			i=25;
			break;
		    case 20:
			for (int k = 0; k < 4; k++)
			{
			    System.out.println(buff.readLine());
			}

			action = input.readLine();
			out.println(action);
			if (action.toUpperCase().equals("YOU'RE FINE"))
			{
			    i = 22;
			}
			if (action.toUpperCase().equals("YOU'RE NOT BORING"))
			{
			    i = 24;
			}
			break;
		    case 21:
			for (int k = 0; k < 2; k++)
			{
			    System.out.println(buff.readLine());
			}

			action = input.readLine();
			out.println(action);
			break;
		    case 22:
			for (int k = 0; k < 3; k++)
			{
			    System.out.println(buff.readLine());
			}

			action = input.readLine();
			out.println(action);
			if (action.toUpperCase().equals("SAY THANK YOU"))
			{
			    i=18;
			}
			if (action.toUpperCase().equals("HEAD BACK HOME"))
			{
			    i = 23;
			}
			break;
		    case 23:
			for (int k = 0; k < 2; k++)
			{
			    System.out.println(buff.readLine());
			}

			action = input.readLine();
			out.println(action);
			i=25;
			break;
		    case 24:
			for (int k = 0; k < 4; k++)
			{
			    System.out.println(buff.readLine());
			}

			action = input.readLine();
			out.println(action);
			if (action.toUpperCase().equals("SAY THANK YOU"))
			{
			    i=18;
			}
			if (action.toUpperCase().equals("HEAD BACK HOME"))
			{
			    i = 23;
			}
			break;
		    case 25:
			System.out.println(buff.readLine());
			i++;
			break;
		}
	    } while (i < 26);
	} catch (IOException ex)
	{
	    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
	}

    }
}
