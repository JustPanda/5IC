package javafxapplication1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import static java.lang.Integer.parseInt;
import static java.lang.System.in;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javafxapplication1.Server;

public class Pizzeria<E extends Comparable<E>> 
{
    BTNode<E> root = null;
    BTNode<E> pizza = null;
    int indice;
    String bibi = "";
    static int cont = 0;
    String[][] listino = {{"margherita", "4"},{"capricciosa", "5"},{"prosciutto e funghi","6"}, {"diavola", "5"}, {"quattro stagioni", "6"}, {"viennese", "4"}};
    List<String> ordinate = new ArrayList<>();
    List<String> bibiteOrdinate = new ArrayList<>();
Integer prezzo= 0;
      
String[][][] listinoBibite = {
{ { "coca-cola"," piccola", "2" },{ "coca-cola"," media", "3" },{ "coca-cola"," grande", "4" } },
{ { "aranciata"," piccola", "2" },{ "aranciata"," media", "3" },{ "aranciata"," grande", "4" } },
{ { "birra"," piccola", "2" },{ "birra"," media", "3" },{ "birra"," grande", "4" } },
};
    public Pizzeria()
    {
        
        root = new BTNode<E>((E)"Che pizza vuoi?");     
        BTNode<E> node = root;
        
        node.leftChild = new BTNode<E>((E)"Che bibita vuoi?");
        node.rightChild = root;
        
        node = node.leftChild;
        
        node.leftChild = new BTNode<E>((E)"Grazie e arrivederci!");
        node.rightChild = node;
        
        
        
     
    }
    
    public List<String> getPizze(PrintWriter out, String nome, BufferedReader in, int controllo) throws IOException
    {
        String daContr = "";
        while(!daContr.equals("STOP"))
        {
            daContr = in.readLine();
            System.out.println("riceve "+daContr);
            boolean verifica = false;
            for(int i = 0; i<listino.length; i++)
            {          
                if(listino[i][0].equals(daContr))
                {
                    prezzo = prezzo+ parseInt(listino[i][1]);
                    verifica = true;
                    break;
                }
            }

            if(verifica)
            {
                ordinate.add(daContr);
                System.out.println("in totale paga: "+prezzo);
            }

        }
        ordinate.add(0,prezzo.toString());
        return ordinate;
    }
    
    public String[][] getListinoPizze()
    {
        return listino;
    }
    
    public String[][][] getlistinoBibite()
    {
        return listinoBibite; 
    }
    
    public List<String> getBibite(PrintWriter out, String nome, BufferedReader in, int controllo) throws IOException
    {
        Integer prezzo=0;
        String daContr = "";
        while(!daContr.equals("STOP"))
        {
            daContr = in.readLine();
            System.out.println("riceve "+daContr);
            
            if(!daContr.equals("STOP"))
            {
                boolean verifica = false;
                for(int i = 0; i<listinoBibite.length; i++)
                {          
                    if(listinoBibite[i][0][0].equals(daContr))
                    {
                        indice = i;
                        verifica = true;
                        bibi = daContr;
                        break;
                    }
                }

                String dime = in.readLine();

                if(verifica)
                {    
                    bibiteOrdinate.add(daContr); 
                    System.out.println(bibiteOrdinate);

                    System.out.println(dime);
                    bibiteOrdinate.add(dime); 
                    switch(dime)
                    {
                        case "Piccola" : prezzo = prezzo + parseInt(listinoBibite[indice][0][2]); System.out.println("indice: "+indice+ " dimesione "+dime+" prezzo: "+listinoBibite[indice][1][2]); break;
                        case "Media" :  prezzo = prezzo + parseInt(listinoBibite[indice][1][2]); System.out.println("indice: "+indice+ " dimesione "+dime+" prezzo: "+listinoBibite[indice][1][2]);break;
                        case "Grande" : prezzo = prezzo + parseInt(listinoBibite[indice][2][2]); System.out.println("indice: "+indice+ " dimesione "+dime+" prezzo: "+listinoBibite[indice][1][2]);break;     
                    }
                }            
            }
        }
        
        bibiteOrdinate.add(0,prezzo.toString());
        return bibiteOrdinate;
     }
}



class BTNode <E>
{
    public E element;
    public BTNode<E> parent;
    public BTNode<E> leftChild;
    public BTNode<E> rightChild;

    public BTNode(E e){this.element=e;}
}
