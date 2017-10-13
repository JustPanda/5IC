/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package risponditore;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author manue
 */
public class Risponditore extends Listino
{

    int Prezzo = 0;
    String Oggetti="";
    
    Node Root = null;
    Node CopyRoot = null;
    
    String[] cat = null;
    String[][][] prod = null;
    Listino l = new Listino();

    public Risponditore()
    {
        Root = new Node("Il tuo carrello della spesa è al momento: [" + Oggetti + "] a " + Prezzo + "€. Cosa vuoi acquistare tra: " + l.categorie[0] + "|" + l.categorie[1] + "|" + l.categorie[2] + "?", l.categorie);

        for (int i = 0; i < 3; i++)
        {
            Root.Children.add(new Node("Che " + l.categorie[i] + " vuoi tra: " + l.prodotti[i][0][0] + "|" + l.prodotti[i][1][0] + "?", new String[]
            {
                l.prodotti[i][0][0], l.prodotti[i][1][0]
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
    
    public void AddPrice(String input)
    {
        Listino l = new Listino();
        String[][][] p = l.prodotti;
        for(int i=0; i<p.length; i++)
        {
            for(int j=0; j<p[i].length; j++)
            {
                if(p[i][j][0].equals(input))
                {
                    Prezzo+=Integer.parseInt(p[i][j][1]);
                    Oggetti+=p[i][j][0] + "|";
                    CopyRoot.Question = "La tua spesa è al momento: [" + Oggetti + "] a " + Prezzo + "€.  Cosa vuoi acquistare tra: " + l.categorie[0] + "|" + l.categorie[1] + "|" + l.categorie[2] + "?";
                }
            }
        }
    }
    
    public void OpenJson() throws IOException
    {
        Scanner f = new Scanner(new File("Listino.json"));
        URL url = new URL("Listino.json");
        try(InputStream is = url.openStream())
        {
            //JsonReader rdr = Json.createReader(is);
        }
    }
}

class Node
{

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


class Listino
{

    public String[] categorie =
    {
        "mobili",
        "tappezzeria",
        "lampadari"
    };

    public String[][][] prodotti =
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
