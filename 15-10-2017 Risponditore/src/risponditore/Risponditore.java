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
        if(input.toUpperCase().contains("login"))
        {
            Login();
            UserSetup();
        }
        if(input.toUpperCase().contains("signup"))
        {
            Signup();
            UserSetup();
        }
        if(input.toUpperCase().contains("elimina"))
        {
            DeleteAccount();
        }
        if(input.toUpperCase().contains("blocca"))
        {
            BlockAccount();
        }
        if(input.toUpperCase().contains("registro"))
        {
            GetLog();
        }
        if(input.toUpperCase().contains("aggiungi"))
        {
            AddMoney();
        }
        if(input.toUpperCase().contains("quanti"))
        {
            GetMoney();
        }
        if(input.toUpperCase().contains("sportello"))
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
        writer.write("Ciao, sono il Bot Banchiere.\nScrivi Login per entrare o Signup per iscriverti");
    }

    public void Signup() throws IOException
    {

        boolean exist = false;
        writer.write("Inserisci la l'username");
        String username = reader.readLine();
        //Controlla se esiste
        if (!exist)
        {
            writer.write("Inserisci la password");
            String password = reader.readLine();
            //Inserisci la password e dai lo stato di login

            this.user = new User(username, password);
        } else
        {
            writer.write("L'account esiste già");
            Login();
        }

    }

    public void Login() throws IOException
    {
        boolean exist = true;
        writer.write("Inserisci la l'username");
        String username = reader.readLine();
        //Controlla se esiste
        if (exist)
        {
            boolean correct =true;
            writer.write("Inserisci la password");
            String password = reader.readLine();
            //controlla la password

            if(correct)
            {
                this.user = new User(username, password);
            }
            else
            {
                writer.write("Pssword errata, riprova");
                Login();
            }
                        
        }
        else
        {
            writer.write("L'account non esiste");
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
            writer.write("Account eliminato");
        }
        else
        {
            writer.write("Non sono riuscito a eliminare l'account. Errore non specificato");
        }
        
    }

    public void BlockAccount() throws IOException
    {
        this.user.IsBlocked = true;
        writer.write("L'account è stato bloccato");
    }

    public void NearerStation() throws IOException
    {
        writer.write("Inserisci la posizione");
        String position = reader.readLine();
        //get position
        writer.write("Lo sportello più vicino a te è a 1km a nord");
    }

    public void GetMoney() throws IOException
    {
        writer.write("Attualmente hai " + user.Money + "€");
    }

    public void AddMoney() throws IOException
    {
        writer.write("Inserisci quanti soldi");
        String input = reader.readLine();
        int money= Integer.parseInt(input);
        user.Money+=money;
        writer.write("Ho aggiunto " + money + "€");
    }

    public void GetLog() throws IOException
    {
        writer.write("Log");
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
