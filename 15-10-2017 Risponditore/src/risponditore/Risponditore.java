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
import java.util.*;

/**
 *
 * @author manue
 */
class Risponditore
{

    PrintWriter writer;
    BufferedReader reader;
    User user;
    
    /*Node root = new Node(new String[]
    {
        ""
    }, Scelte.Idle); */

    public Risponditore(PrintWriter writer, BufferedReader reader) throws IOException
    {
        this.writer = writer;
        this.reader = reader;
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
        if(input.toLowerCase().contains("login"))
        {
            Login();
            UserSetup();
        }
        else if(input.toLowerCase().contains("signup"))
        {
            Signup();
            UserSetup();
        }
        else if(input.toLowerCase().contains("elimina"))
        {
            DeleteAccount();
        }
        else if(input.toLowerCase().contains("blocca"))
        {
            BlockAccount();
        }
        else if(input.toLowerCase().contains("registro"))
        {
            GetLog();
        }
        else if(input.toLowerCase().contains("aggiungi"))
        {
            AddMoney();
        }
        else if(input.toLowerCase().contains("quanti"))
        {
            GetMoney();
        }
        else if(input.toLowerCase().contains("sportello"))
        {
            NearerStation();
        }
        else
        {
            writer.println("Non ho capito");
        }
    }

    public void Idle() throws IOException
    {
        writer.println("Ciao, sono il Bot Banchiere");
        //writer.println("Puoi usare i seguenti comandi:\nLogin\" per entrare con il tuo account \n\"Signup\" per iscriverti\n\"Aggiungi soldi\" per aggiungere soldi\n\"Quanti soldi ci sono?\" per sapere quanti soldi hai nel conto\n\"Mostrami il registro delle operazioni\" per vedere i comandi dati fin'ora\n\"Elimina account\" per eliminare l'account\n\nBlocca account\" per bloccare l'account\n\"Dov'è lo sportello più vicino?\" per sapere dov'è lo sportello più vicino a te");
    }

    public void Signup() throws IOException
    {

        boolean exist = false;
        writer.println("Inserisci la l'username");
        String username = reader.readLine();
        //Controlla se esiste
        if (!exist)
        {
            writer.println("Inserisci la password");
            String password = reader.readLine();
            //Inserisci la password e dai lo stato di login

            this.user = new User(username, password);
            writer.println("Accesso eseguito con successo");
        } else
        {
            writer.println("L'account esiste già");
            Login();
        }

    }

    public void Login() throws IOException
    {
        boolean exist = true;
        writer.println("Inserisci la l'username");
        String username = reader.readLine();
        //Controlla se esiste
        if (exist)
        {
            boolean correct =true;
            writer.println("Inserisci la password");
            String password = reader.readLine();
            //controlla la password

            if(correct)
            {
                this.user = new User(username, password);
                writer.println("Accesso eseguito con successo");
            }
            else
            {
                writer.println("Pssword errata, riprova");
                Login();
            }
                        
        }
        else
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
        boolean deleted=true;
        //elimina user
        if(deleted)
        {
            writer.println("Account eliminato");
        }
        else
        {
            writer.println("Non sono riuscito a eliminare l'account. Errore non specificato");
        }
        
    }

    public void BlockAccount() throws IOException
    {
        this.user.IsBlocked = true;
        writer.println("L'account è stato bloccato");
    }

    public void NearerStation() throws IOException
    {
        writer.println("Inserisci la posizione");
        String position = reader.readLine();
        //get position
        writer.println("Lo sportello più vicino a te è a 1km a nord");
    }

    public void GetMoney() throws IOException
    {
        writer.println("Attualmente hai " + user.Money + "€");
    }

    public void AddMoney() throws IOException
    {
        writer.println("Inserisci quanti soldi");
        String input = reader.readLine();
        int money= Integer.parseInt(input);
        user.Money+=money;
        writer.println("Ho aggiunto " + money + "€");
    }

    public void GetLog() throws IOException
    {
        writer.println("Log");
        //ottieni log
        //invia log
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
