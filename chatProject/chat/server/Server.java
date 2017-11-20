package chat.server;

import java.net.*;
import java.io.*;
import java.util.concurrent.*;
import java.util.*;

class Server
{
    private final static int PORT=1723;
    private final static int MAX_CLIENTS=100;
    private static HashMap<String, User> onlineClients=new HashMap<>();

    public static void main(String[] args)
    {
        SQLiteHelper db=new SQLiteHelper();

        ServerSocket server=null;
        try
        {
            server=new ServerSocket(PORT);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        System.out.printf("\n\nServer is running...\n\nListening on port: %d.\n\nPress CTRL+C in any moment to shutdown the server.\n\n", PORT);

        ExecutorService executor=Executors.newFixedThreadPool(MAX_CLIENTS);

        int clientNumber=0;
        try
        {
            while(true)
            {
                executor.execute(new User(server.accept(), db, ++clientNumber));
                System.out.printf("\nConnection established with client #%d.", clientNumber);
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    synchronized static LinkedList<String> getContactsList()
    {
        LinkedList<String> l=new LinkedList<>();
        onlineClients.forEach((k, v)->{
            l.add(k);
        });
        return l;
    }

    synchronized static void addClient(String s, User u)
    {
        System.out.printf("\nAdding %s to online clients.", s);
        onlineClients.put(s, u);
        updateClientsContacts(true, s);
    }

    synchronized static void removeClient(String s)
    {
        System.out.printf("\nRemoving %s from online clients.", s);
        onlineClients.remove(s);
        updateClientsContacts(false, s);
    }

    synchronized static void updateClientsContacts(boolean add, String s)
    {
        if(add)
        {
            onlineClients.forEach((k,v)->
            {
                v.addContact(s);
            });
        }
        else
        {
            onlineClients.forEach((k,v)->
            {
                v.removeContact(s);
            });
        }
    }

    synchronized static boolean isOnline(String s)
    {
        return onlineClients.containsKey(s);
    }

    synchronized static int getClientsNumber()
    {
        return onlineClients.size();
    }

    static void sendTo(String from, String to, String message)
    {
        onlineClients.get(to).receive(from, message);
    }
}
