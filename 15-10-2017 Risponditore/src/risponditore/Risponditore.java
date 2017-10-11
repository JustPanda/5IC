/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package risponditore;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 *
 * @author manue
 */
class Risponditore
{

    PrintWriter writer;
    BufferedReader reader;
    User user = null;
    
    Connection c = null;
    Statement stmt = null;

    /*Node root = new Node(new String[]
    {
        ""
    }, Scelte.Idle); */
    public Risponditore(PrintWriter writer, BufferedReader reader) throws IOException, ClassNotFoundException
    {
        this.writer = writer;
        this.reader = reader;
        
        Connection c = null;
        Statement stmt = null;

        try
        {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:test.db");
            c.setAutoCommit(false);            
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
        } catch (Exception e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        
        Idle();
        //TreeSetup();
    }

    /*public void TreeSetup()
    {
        Scelta temp;
        root.Leaves.add(new Scelta(new String[]
        {
            "signup"
        }, Scelte.Signup));
        
        temp = root.Leaves.get(0);
        temp.Parent = root;
        

        root.Leaves.add(new Scelta(new String[]
        {
            "login"
        }, Scelte.Login));
    } */
    public void Compare(String input) throws Exception
    {
        System.out.println("Il compare ha ricevuto " + input);
        if (input.toLowerCase().contains("login"))
        {
            Login();
            UserSetup();
        } else if (input.toLowerCase().contains("signup"))
        {
            Signup();
            UserSetup();
        } else if (this.user == null)
        {
            writer.println("Devi prima fare il login per poter eseguire un comando");
        } else if (input.toLowerCase().contains("elimina"))
        {
            DeleteAccount();
        } else if (input.toLowerCase().contains("blocca"))
        {
            BlockAccount();
        } else if (input.toLowerCase().contains("registro"))
        {
            GetLog();
        } else if (input.toLowerCase().contains("aggiungi"))
        {
            AddMoney();
        } else if (input.toLowerCase().contains("quanti"))
        {
            GetMoney();
        } else if (input.toLowerCase().contains("sportello"))
        {
            NearerStation();
        } else
        {
            writer.println("Non ho capito");
        }
    }

    public void Idle() throws IOException
    {
        writer.println("Ciao, sono il Bot Banchiere");
        //writer.println("Puoi usare i seguenti comandi:\nLogin\" per entrare con il tuo account \n\"Signup\" per iscriverti\n\"Aggiungi soldi\" per aggiungere soldi\n\"Quanti soldi ci sono?\" per sapere quanti soldi hai nel conto\n\"Mostrami il registro delle operazioni\" per vedere i comandi dati fin'ora\n\"Elimina account\" per eliminare l'account\n\nBlocca account\" per bloccare l'account\n\"Dov'è lo sportello più vicino?\" per sapere dov'è lo sportello più vicino a te");
    }

    public void Signup() throws IOException, SQLException
    {

        ResultSet rs = stmt.executeQuery( "SELECT * FROM USER;" );
        boolean exist = false;
        writer.println("Inserisci l'username");
        String username = reader.readLine();
        while(rs.next())
        {
            String tmp = rs.getString("username");
            if(tmp==username)
            {
                exist=true;
            }
        }
        if (!exist)
        {
            writer.println("Inserisci la password");
            String password = reader.readLine();
            //Inserisci la password e dai lo stato di login
            String sql = "INSERT INTO USER (ID,USERNAME, PSD, BLK, MONEY)"
            this.user = new User(username, password);
            writer.println("Registrazione e accesso eseguiti con successo");
        } else
        {
            writer.println("L'account esiste già");
            Login();
        }
        rs.close();

    }

    public void Login() throws IOException
    {
        boolean exist = true;
        writer.println("Inserisci l'username");
        String username = reader.readLine();
        //Controlla se esiste
        if (exist)
        {
            boolean correct = true;
            writer.println("Inserisci la password");
            String password = reader.readLine();
            //controlla la password

            if (correct)
            {
                this.user = new User(username, password);
                writer.println("Accesso eseguito con successo");
            } else
            {
                writer.println("Pssword errata, riprova");
                Login();
            }

        } else
        {
            writer.println("L'account non esiste");
            Signup();
        }
    }

    public void UserSetup()
    {

    }

    public void DeleteAccount() throws IOException
    {
        boolean deleted = true;
        //elimina user
        if (deleted)
        {
            writer.println("Account eliminato");
            this.user.Messages.add(new Message("Account eliminato", MessageOwner.Server));
        } else
        {
            writer.println("Non sono riuscito ad eliminare l'account. Errore non specificato");
            this.user.Messages.add(new Message("Non sono riuscito ad eliminare l'account. Errore non specificato", MessageOwner.Server));
        }

    }

    public void BlockAccount() throws IOException
    {
        this.user.IsBlocked = true;
        writer.println("L'account è stato bloccato");
        this.user.Messages.add(new Message("L'account è stato bloccato", MessageOwner.Server));
    }

    public void NearerStation() throws IOException
    {
        writer.println("Inserisci la posizione");
        this.user.Messages.add(new Message("Inserisci la posizione", MessageOwner.Server));
        String position = reader.readLine();
        this.user.Messages.add(new Message(position, MessageOwner.Client));
        //get position
        writer.println("Lo sportello più vicino a te è a 1km a nord");
        this.user.Messages.add(new Message("Lo sportello più vicino a te è a 1km a nord", MessageOwner.Server));
    }

    public void GetMoney() throws IOException
    {
        writer.println("Attualmente hai " + user.Money + "€");
        this.user.Messages.add(new Message("Attualmente hai " + user.Money + "€", MessageOwner.Server));
    }

    public void AddMoney() throws IOException
    {
        writer.println("Inserisci quanti soldi vuoi aggiungere");
        this.user.Messages.add(new Message("Inserisci quanti soldi vuoi aggiungere", MessageOwner.Server));
        String input = reader.readLine();
        this.user.Messages.add(new Message(input, MessageOwner.Client));
        int money = Integer.parseInt(input);
        user.Money += money;
        writer.println("Ho aggiunto " + money + "€");
        this.user.Messages.add(new Message("Ho aggiunto " + money + "€", MessageOwner.Server));
    }

    public void GetLog() throws IOException
    {
        String log = "Registro di " + user.Username + ":\n";

        String owner;
        for (int i = 0; i < user.Messages.size(); i++)
        {
            if (user.Messages.get(i).Owner == MessageOwner.Server)
            {
                owner = "Server";
            } else
            {
                owner = user.Username;
            }
            log += owner + " at " + user.Messages.get(i).Date + ":  " + user.Messages.get(i).Text + "\n";
        }

        writer.println(log);
    }
}

class Node
{

    public Node(String[] Keys, Node Valore)
    {
        this.Valore = Valore;
        this.Keys = Keys;
    }
    public Node Valore;
    public String[] Keys;
    public Node Parent;
}

enum Scelte
{
    Idle,
    Signup,
    Login,
    Block,
    Delete,
    Add,
    Get,
    Log
}
