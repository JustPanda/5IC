/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package risponditore;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author manue
 */
public class Risponditore
{

    int Prezzo = 0;
    Node Root = null;
    Node CopyRoot = null;
    Listino l = new Listino();

    public Risponditore()
    {
        Root = new Node("Ciao, benvenuto al NAC, cosa vuoi acquistare tra: " + l.categorie[0] + "|" + l.categorie[1] + "|" + l.categorie[2] + "?", l.categorie);

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

            /*    Root.Children.get(i).Children.get(j).Children.add(new Node("Vuoi altro?", new String[]
                {
                    "si", "no"
                })); */

           /*     Root.Children.get(i).Children.get(j).Children.get(j).Children.add(new Node("Uscita...", new String[]
                {
                    ""
                })); */

            }

        }

        /*
        for (int i = 0; i < Root.Children.size(); i++)
        {
            Root.Children.get(0).Children.get(0).Children.add(new Node("Vuoi altro?", new String[]
            {
                "si", "no"
            }));
        }

        for (int i = 0; i < Root.Children.size(); i++)
        {
            Root.Children.get(0).Children.get(0).Children.get(0).Children.add(new Node("Uscita...", new String[]
            {
                ""
            }));
        } */
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
            return null;
        }
        for (int i = 0; i < Root.Answers.length; i++)
        {
            if (Root.Answers[i].equals(input))
            {
                Root = Root.Children.get(i);
            }
        }

        
       /* if (Root.Question.equals("Quanti ne vuoi?"))
        {
            Root = Root.Children.get(Integer.parseInt(input));
        } */

        String output = Root.Question;
        System.out.println("Output " + output);
        return output;
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
    
    public void ChangeInput(String input)
    {
        Listino l = new Listino();
        String[][][] p = l.prodotti;
        for(int i=0; i<p.length; i++)
        {
            
        }
    }
    
    public String Input;
}


class Listino
{

    String[] categorie =
    {
        "mobili",
        "tappezzeria",
        "lampadari"
    };

    String[][][] prodotti =
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
                "lamapadario", "700"
            },
            {
                "abatjoure", "30"
            }
        }
    };

}
