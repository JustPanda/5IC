package chat.server;

import java.io.*;
import java.net.*;

class User implements Runnable
{
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private SQLiteHelper db;
    private String name;
    private int clientNumber;

    User(Socket socket, SQLiteHelper db, int clientNumber)
    {
        this.socket=socket;
        this.db=db;
        this.clientNumber=clientNumber;
        try
        {
            this.in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.out=new PrintWriter(socket.getOutputStream(), true);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    private String handleRegistration() throws IOException
    {
        System.out.printf("\nClient #%d is is trying to register.", clientNumber);
        String name=in.readLine(), password=in.readLine();
        this.name=name;
        return db.addUser(name, password)?"ok":"exist";
    }

    private void deleteAccount()
    {
        System.out.printf("\nDeleting account of: %s.", name);
        db.removeUser(name);
    }

    private String handleLogin() throws IOException
    {
        System.out.printf("\nClient #%d is trying to log in.", clientNumber);
        String name=in.readLine();
        if(Server.isOnline(name))
            return "online";
        String password=in.readLine();
        String r=null;
        switch(db.authUser(name, password))
        {
            case 0: r="ok"; break;
            case 1: r="password"; break;
            case 2: r="name";
        }
        this.name=name;
        return r;
    }

    void sendContacts()
    {
        System.out.printf("\nSending contacts to client #%d.", clientNumber);
        out.println("contacts");
        out.println(Server.getClientsNumber());
        for(String s:Server.getContactsList())
        {
            out.println(s);
        }
    }

    void addContact(String s)
    {
        if(!s.equals(name))
        {
            out.println("add");
            out.println(s);
        }
    }

    void removeContact(String s)
    {
        if(!s.equals(name))
        {
            out.println("remove");
            out.println(s);
        }
    }

    void receive(String from, String message)
    {
        out.println("message");
        out.println(from);
        out.println(message);
    }

    private void startChat() throws IOException
    {
        sendContacts();
        boolean exit=false;
        while(!exit)
        {
            switch(in.readLine())
            {
                case "exit": exit=true; break;
                case "message":
                    String to=in.readLine(), message=in.readLine();
                    Server.sendTo(name, to, message); break;
                case "deleteAccount": deleteAccount(); exit=true;
            }
        }
    }

    @Override
    public void run()
    {
        try
        {
            if(in.readLine().equals("login"))
            {
                String res=handleLogin();
                out.println(res);
                if(res.equals("ok"))
                {
                    System.out.printf("\nClient #%d logged in as %s.", clientNumber, name);
                    Server.addClient(name, this);
                    startChat();
                    System.out.printf("\n%s logged out.", name);
                    Server.removeClient(name);
                }
                else
                {
                    System.out.printf("\nClient #%d failed to authenticate.", clientNumber);
                }
            }
            else
            {
                String res=handleRegistration();
                out.println(res);
                if(res.equals("ok"))
                {
                    System.out.printf("\nClient #%d signed up as %s", clientNumber, name);
                    Server.addClient(name, this);
                    startChat();
                    System.out.printf("\n%s logged out.", name);
                    Server.removeClient(name);
                }
                else
                {
                    System.out.printf("\nClient #%d failed to sing up.", clientNumber);
                }
            }
            out.println("exit");
            socket.close();
            System.out.printf("\nClosed connection with client #%d.", clientNumber);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}
