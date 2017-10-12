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
		Root = new Node("Ciao, benvenuto al NAC", {"",""});
	}
}

class Node
{

	public Node(String Question, String[] Answers)
	{
		this.Question = Question;
		this.Answers = Answers;
	}
	public String Question;
	public String[] Answers;
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
	public Listino(String[][] Oggetti, String Categoria)
	{
		this.Oggetti = Oggetti;
		this.Categoria = Categoria;
	}
	public String[][] Oggetti;
	public String Categoria;
}

class ListinoTotale
{

	String[][] _mobili =
	{
		{
			"Divano", "500"
		},
		{
			"Letto", "1000"
		}
	};
	Listino mobili = new Listino(_mobili, "Mobili");

	String[][] _tappezzeria =
	{
		{
			"Tappeto", "100"
		},
		{
			"Moquette", "300"
		}
	};
	Listino tappezzeria = new Listino(_mobili, "Tappezzeria");
	
	String[][] _lampadari =
	{
		{
			"Lamapadario", "700"
		},
		{
			"Abatjoure", "30"
		}
	};
	Listino lampadari = new Listino(_mobili, "Lampadari");

}
