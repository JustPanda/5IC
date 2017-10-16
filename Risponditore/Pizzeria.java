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
public class Pizzeria implements Runnable
{
    private Socket client;
    private BufferedReader fromClient;
    private PrintWriter toClient;
    private Graph automaton;

    public Pizzeria(Socket client)
    {
	this.client = client;
	automaton = new Graph();

    }

    @Override
    public void run()
    {
	String s = null;
	try
	{
	    fromClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
	    toClient = new PrintWriter(client.getOutputStream(), true);
	    int i = 0;

	    while (i < 26)
	    {
		s = automaton.getNode().get(i).toString();
		toClient.println(s);
		System.out.println(s);
		String action = fromClient.readLine().toUpperCase();
		switch (action)
		{
		    case "CARRY ON":
			i += 1;
			break;
		    case "YES":
			i = 2;
			break;
		    case "NO":
			i = 12;
			break;
		    case "NO PROBLEM":
			i++;
			break;
		    case "HOUSE SPECIALITY":
			i++;
			break;
		    case "YES OF COURSE":
			i++;
			break;
		    case "ASK":
			i++;
			break;
		    case "INTERESTING":
			i++;
			break;
		    case "OBVIOUSLY SEARCH HIM":
			i++;
			break;
		    case "GET IN HIS WAY":
			i++;
			break;
		    case "KEEP EATING AND WAIT":
			i = 11;
			break;
		    case "LOOK AROUND":
			i = 10;
			break;
		    case "NO PROBLEM AT ALL":
			i = 14;
			break;
		    case "YES I KNOW":
			i++;
			break;
		    case "NO IT DOESN'T":
			i = 17;
			break;
		    case "TO BE HONEST...":
			i = 20;
			break;
		    case "SAY THANK YOU":
			i=18;
			break;
		    case "YES I'D LOVE TO":
			i++;
			break;
		    case "HEAD BACK HOME":
			i = 23;
			break;
		    case "NO I'M SORRY":
			i = 21;
			break;
		    case "YOU'RE FINE":
			i = 22;
			break;
		    case "YOU'RE NOT BORING":
			i = 24;
			break;
		    case "TALK ABOUT YOURSELF":
		    if(i==13)
			{
			    i=16;
			}else
			{
			    i++;
			}
			break;
		    case "":
			if (i < 25)
			{
			    i = 25;
			} else
			{
			    i++;
			}
			break;
		}
	    }
	    toClient.print(s);
	    client.close();
	} catch (IOException ex)
	{
	    Logger.getLogger(Pizzeria.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

}
