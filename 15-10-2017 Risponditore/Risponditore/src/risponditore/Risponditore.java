/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package risponditore;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Manuele Lucchi
 */

//Classe Risponditore che rappresenta l'automa a stati finiti
public class Risponditore
{

    int Prezzo = 0;
    String Oggetti="";
    
    Node Root = null;
    Node CopyRoot = null;
    
    String[] cat = null;
    String[][][] prod = null;
    Listino l = new Listino();

    //Costruttore del Risponditore con creazione statica dell'albero
    public Risponditore()
    {
        Root = new Node("La tua spesa è al momento: [" + Oggetti + "] a " + Prezzo + "€. Cosa vuoi acquistare tra: " + l.Categorie[0] + "|" + l.Categorie[1] + "|" + l.Categorie[2] + "?", l.Categorie);

        for (int i = 0; i < 3; i++)
        {
            Root.Children.add(new Node("Che " + l.Categorie[i] + " vuoi tra: " + l.Prodotti[i][0][0] + "|" + l.Prodotti[i][1][0] + "?", new String[]
            {
                l.Prodotti[i][0][0], l.Prodotti[i][1][0]
            }));
        }

        for (int i = 0; i < Root.Children.size(); i++)
        {

            for (int j = 0; j < Root.Children.get(i).Answers.length; j++)
            {
                Root.Children.get(i).Children.add(new Node("Vuoi altro?", new String[]
                {
                    "si", "no"
                }));
            }
        }
        CopyRoot = Root;
    }

    //Metodo per la navigazione dell'albero
    public String Exe(String input)
    {
        input = input.toLowerCase();
        if (Root.Question.equals("Vuoi altro?") && input.equals("si"))
        {
            Root = CopyRoot;
        }
        else if(Root.Question.equals("Vuoi altro?") && input.equals("no"))
        {
            return "Ti verranno spediti: [ " + Oggetti + " ] al prezzo di " + Prezzo + "€";
        }
        for (int i = 0; i < Root.Answers.length; i++)
        {
            if (Root.Answers[i].equals(input))
            {
                AddPrice(input);
                Root = Root.Children.get(i);
            }
        }

        String output = Root.Question;
        System.out.println("Output " + output);
        return output;
    }
    
    //Metodo per la conta del prezzo totale
    public void AddPrice(String input)
    {
        Listino l = new Listino();
        String[][][] p = l.Prodotti;
        for(int i=0; i<p.length; i++)
        {
            for(int j=0; j<p[i].length; j++)
            {
                if(p[i][j][0].equals(input))
                {
                    Prezzo+=Integer.parseInt(p[i][j][1]);
                    Oggetti+=p[i][j][0] + "|";
                    CopyRoot.Question = "La tua spesa è al momento: [" + Oggetti + "] a " + Prezzo + "€.  Cosa vuoi acquistare tra: " + l.Categorie[0] + "|" + l.Categorie[1] + "|" + l.Categorie[2] + "?";
                }
            }
        }
    }

}

//Classe Nodo per i nodi dell'albero
class Node
{
    //Costruttore della classe nodo
    public Node(String Question, String[] Answers)
    {
        this.Question = Question;
        this.Answers = Answers;
        this.Children = new ArrayList<Node>();
    }
    public String Question;
    public String[] Answers;
    public List<Node> Children;
    
}


//Classe Listino che contiene prodotti e categorie. L'idea iniziale era di usare un Json ma Java non collabora
class Listino
{

    public String[] Categorie =
    {
        "mobili",
        "tappezzeria",
        "lampadari"
    };

    public String[][][] Prodotti =
    {
        {
            {
                "divano", "500"
            },
            {
                "letto", "1000"
            }
        },
        {
            {
                "tappeto", "100"
            },
            {
                "moquette", "300"
            }
        },
        {
            {
                "lampadario", "700"
            },
            {
                "abatjoure", "30"
            }
        }
    };

}
