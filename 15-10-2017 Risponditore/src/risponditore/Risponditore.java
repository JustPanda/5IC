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

    public Risponditore()
    {

    }
}

class Node
{

    public Node(String Question, List<String> Answers)
    {
        this.Question = Question;
        this.Answers = Answers;
    }
    public String Question;
    public List<String> Answers;
    public List<Node> Children;
}

class Product
{

    public Product(int Number, String Name, int Price, String Category)
    {
        this.Number = Number;
        this.Name = Name;
        this.Price = Price;
    }
    int Number;
    String Name;
    int Price;
    String Category;
}

class Bills
{

    public Bills()
    {
        this.Products = new ArrayList<Product>();
    }
    List<Product> Products;
}

class Listino
{
    public Listino(){}
    public String[][] Oggetti;
    public String Categoria;
}

class ListinoTotale
{

    Listino mobili = new Listino();
    mobili.Oggetti  =
    {
        {
            ""
        },
        {
            ""
        },
        {
            ""
        },
        {
            ""
        },
        {
            ""
        }
    };
    mobili.Categoria = "Mobili";
    /*        
    {
        this.Oggetti  =
        {
            {
            }, 
            {
            }, 
            {
            }, 
            {
            }, 
            {
            }
        }
    }; */

}
