package pizzeria;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author gioele
 */
public class Pizzeria {
    static ArrayList <Prodotto> menuP=new ArrayList();
    static ArrayList <Prodotto> menuB=new ArrayList();
    public static void main(String[] args) {
        Scanner input=new Scanner(System.in);
        Prodotto prd;
        String str;
        System.out.println("server acceso");
        System.out.println("Vuoi aggiungere una o più pizze al menù? [si/no]");
        str=input.nextLine();
        if(str.equals("si")){
            while(true){
                prd=new Prodotto("",0);
                System.out.println("Inserire nome pizza");
                str=input.nextLine();
                prd.nomeP=str;
                System.out.println("Inserire prezzo pizza");
                str=input.nextLine();
                prd.prezzo=Double.parseDouble(str);
                Pizzeria.getMp().add(prd);
                System.out.println("Premi invio per continuare o x per chiudere");
                str=input.nextLine();
                if(str.equals("\n"))
                    continue;
                else{
                    if(str.equals("x"))
                        break;
                }
            }
        }
        System.out.println("Vuoi aggiungere una o più bibite? [si/no]");
        str=input.nextLine();
        if(str.equals("si")){
            while(true){
                prd=new Prodotto("",0);
                System.out.println("Inserire nome bibita");
                str=input.nextLine();
                prd.nomeP=str;
                System.out.println("Inserire prezzo bibita");
                str=input.nextLine();
                prd.prezzo=Double.parseDouble(str);
                menuB.add(prd);
                System.out.println("Premi invio per continuare o x per chiudere");
                str=input.nextLine();
                if(str.equals("\n"))
                    continue;
                else{
                    if(str.equals("x"))
                        break;
                }
            }
        }
        System.out.println("Premere invio per aprire la pizzeria");
        if((input.nextLine()).equals("\n")){
        }
            Server server=new Server();
            server.acceptance();
    }
    
    public static ArrayList <Prodotto> getMp(){
        return menuP;
    }
    
    public static ArrayList <Prodotto> getMb(){
        return menuB;
    }
    
}
