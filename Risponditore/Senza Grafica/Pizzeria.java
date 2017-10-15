package pizzeria;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import static java.lang.Integer.parseInt;
import static java.lang.System.in;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import pizzeria.Server;

public class Pizzeria<E extends Comparable<E>> 
{
    
    BTNode<E> root = null;
    static int cont = 0;
    String[][] listino = {{"margherita", "4"},{"capricciosa", "5"},{"prosciutto e funghi","6"}, {"diavola", "5"}, {"quattro stagioni", "6"}, {"viennese", "4"}};
    List<String> ordinate = new ArrayList<>();
    List<String> bibiteOrdinate = new ArrayList<>();

      
String[][][] listinoBibite = {
{ { "coca-cola"," piccola", "2" },{ "coca-cola"," media", "3" },{ "coca-cola"," grande", "4" } },
{ { "aranciata"," piccola", "2" },{ "aranciata"," media", "3" },{ "aranciata"," grande", "4" } },
{ { "birra"," piccola", "2" },{ "birra"," media", "3" },{ "birra"," grande", "4" } },
};
    public Pizzeria()
    {
        root = new BTNode<E>((E)"Che pizza vuoi? (digita STOP per smettere)");     
        BTNode<E> node = root;
        
        node.leftChild = new BTNode<E>((E)"Che bibita vuoi? (digita STOP per smettere)");
        node.rightChild = root;
        
        node = node.leftChild;
        
        node.leftChild = new BTNode<E>((E)"Grazie e arrivederci! ");
        node.rightChild = node;
        
        
        
     
    }
    
    public List<String> getPizze(PrintWriter out, String nome, BufferedReader in, int controllo) throws IOException
    {
       /* if(controllo == 1)
        {
            root = root.parent;
        }*/
        Integer prezzo= 0;
      
       /* ArrayList<String>[] a;
        a=new ArrayList[2];
        a[0]=new ArrayList<>();
        a[1]=new ArrayList<>();*/
        out.println(root.element);
        String answer = "oleo";
       
        while(!answer.equals("STOP"))
        {
            answer = in.readLine();
            if(!answer.equals("STOP"))
            {            
                System.out.println(nome+" vuole una "+answer);
                boolean vai = false;
                for(int i = 0; i<listino.length; i++)
                {
                    if(answer.equals(listino[i][0]))
                    {
                        prezzo = prezzo+ parseInt(listino[i][1]);
                        vai = true;
                        break;	
                    }
                }
                if(vai)
                {     
                    ordinate.add(answer);
                }
                else
                {
                    System.out.println("ma è stupido, perche la "+answer+ " non esiste");
                }        
                root = root.rightChild;
                out.println(root.element);
            }
  
        }      
        ordinate.add(0,prezzo+"€");
        root = root.leftChild;
        System.out.println(nome+ " non vuole piu niente");
        out.println(root.element);   
        return ordinate;
    }
    
    public List<String> getBibite(PrintWriter out, String nome, BufferedReader in, int controllo) throws IOException
    {
        Integer prezzo= 0;
        String answer = "oleo"; 
        String dimensione = "oleo";
        while(!answer.equals("STOP"))
        {
            answer = in.readLine();
            if(!answer.equals("STOP"))
            {            
                
                boolean vai = false;
                for(int i = 0; i<listinoBibite.length; i++)
                {
                    if(answer.equals(listinoBibite[i][0][0]))
                    {
                        out.println("piccola, media o grande?");
                        dimensione = in.readLine();
                        System.out.println(nome+" vuole una "+answer+" "+dimensione);
                        
                        switch(dimensione)
                        {
                            case "piccola" : prezzo = prezzo + parseInt(listinoBibite[i][0][2]); break;
                            case "media" :  prezzo = prezzo + parseInt(listinoBibite[i][1][2]); break;
                            case "grande" : prezzo = prezzo + parseInt(listinoBibite[i][2][2]); break;
                               
                        }
                        vai = true;
                        break;	
                    }
                }
                if(vai)
                {     
                    bibiteOrdinate.add(answer+" "+dimensione);
                }
                else
                {
                    System.out.println("ma è stupido, perche la "+answer+ " non esiste");
                }        
                root = root.rightChild;
                out.println(root.element);
            }
  
        }      
        bibiteOrdinate.add(0,prezzo+"€");
        root = root.leftChild;
        System.out.println(nome+ " non vuole piu niente");
        
        
        out.println("Hai: "+ordinate+" da mangiare, e: "+bibiteOrdinate+" da bere. "+root.element );   
        
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
