/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rispoinditore2;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author davide
 */
public class Server
{
    public static void main(String[] args)
    {
	try
	{
	    ServerSocket server = new ServerSocket(8080);
	    ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
	    while (true)
	    {
		executor.execute(new Pizzeria(server.accept()));
	    }
	} catch (IOException ex)
	{
	    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
	}
    }
}
