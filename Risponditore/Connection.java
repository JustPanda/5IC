package pizzeria;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author gioele
 */
public class Connection extends Thread{
    Socket client;
    BufferedReader in;
    PrintWriter out;
    PizzAlb alb=new PizzAlb();
    PizzNode node=new PizzNode(null,"");
    ArrayList <Prodotto> ordinazione=new ArrayList();

    public Connection(Socket client) throws IOException {
        this.client = client;
        in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())), true);
    }
    
    @Override
    public void run(){
        String entrata;
        String send="";
        String send1="";
        boolean invio_menu=false;
        Prodotto prodotto;
        boolean ordP=false;
        boolean ordB=false;
        boolean nessuna=false;
        boolean help=false;
        while(true){
            try {
                if(invio_menu==false){
                    entrata=in.readLine();
                    for(int i=0;i<Pizzeria.getMp().size();i++){
                        send+="° "+Pizzeria.getMp().get(i).nomeP+" -> "+Pizzeria.getMp().get(i).prezzo+"€";
                    }
                    if(Pizzeria.getMp().isEmpty()){
                        send="non sono presenti pizze";
                    }
                    out.println("menu pizze : "+send);
                    for(int i=0;i<Pizzeria.getMb().size();i++){
                        send1+="° "+Pizzeria.getMb().get(i).nomeP+" -> "+Pizzeria.getMb().get(i).prezzo+"€";
                    }
                    entrata=in.readLine();
                    if(Pizzeria.getMb().isEmpty())
                        send1="non ci sono bibite";
                    out.println("menu bibite : "+send1);
                    send="";
                    send1="";
                    entrata=in.readLine();
                }
                //ordinazione da parte del cliente
                node=alb.node;
                String err="";
                while(true){
                    out.println(err+""+node.domanda);
                    entrata=in.readLine();
                    err="";
                    
                    //ordinazione pizza
                    if(presenteP(entrata) && ordP==false && !(entrata).equals("si") && !(entrata).equals("no") && !(entrata).equals("nessuna") && help==false){
                        ordinazione.add(new Prodotto(prendiPizz(entrata),prendiPrezzP(entrata)));
                        node=alb.node1;
                        continue;
                    }
                    if(!(presenteP(entrata)) && ordP==false && !(entrata).equals("si") && !(entrata).equals("no") && !(entrata).equals("nessuna") && help==false){
                        err="non presente.";
                        continue;
                    }
                    if(entrata.equals("si") && ordP==false && help==false){
                        node=alb.node1.parent;
                        continue;
                    }
                    if(entrata.equals("no") && ordP==false && help==false){
                        node=alb.node2;
                        ordP=true;
                        continue;
                    }
                    //fine ordinazione pizza
                    
                    //ordinazione bibita
                    if(presenteB(entrata) && ordB==false && !(entrata).equals("si") && !(entrata).equals("no") && !(entrata).equals("nessuna") && help==false){
                        ordinazione.add(new Prodotto(prendiBib(entrata),prendiPrezzB(entrata)));
                        node=alb.node3;
                        continue;
                    }
                    if(!(presenteB(entrata)) && ordB==false && !(entrata).equals("si") && !(entrata).equals("no") && !(entrata).equals("nessuna") && help==false){
                        err="non presente.";
                        continue;
                    }
                    if(entrata.equals("nessuna") && ordB==false && help==false && !(nessuna)==false){
                        out.println("Ordinazione effettuata da parte di? ");
                        entrata=in.readLine();
                        System.out.println("Il cliente "+entrata+" ha effettuato la seguente ordinazione: "+ordine(ordinazione));
                        System.out.println("Per un totale di "+pagamento(ordinazione).toString());
                        out.println("Ordinazione effettuata per un totale di "+ pagamento(ordinazione).toString());
                        break;
                    }
                    if(entrata.equals("si") && ordB==false && help==false){
                        node=alb.node3.parent;
                        continue;
                    }
                    if(entrata.equals("no") && ordB==false && help==false){
                        ordB=true;
                        out.println("Ordinazione effettuata da parte di? ");
                        entrata=in.readLine();
                        System.out.println("Il cliente "+entrata+" ha effettuato la seguente ordinazione: "+ordine(ordinazione));
                        System.out.println("Per un totale di "+pagamento(ordinazione).toString());
                        out.println("Ordinazione effettuata per un totale di "+ pagamento(ordinazione).toString());
                        break;
                    }
                    
                    //ordinazione solo bibita
                    
                    if(entrata.equals("nessuna") && nessuna==false){
                        node=alb.node4;
                        System.out.println(node.domanda);
                        nessuna=true;
                        ordB=true;
                        help=true;
                        continue;
                    }
                    
                    if(presenteB(entrata) && ordB==true && !(entrata).equals("si") && !(entrata).equals("no") && nessuna==true){
                        ordinazione.add(new Prodotto(prendiBib(entrata),prendiPrezzB(entrata)));
                        node=alb.node5;
                        continue;
                    }
                    
                    if(!(presenteB(entrata)) && ordB==true && !(entrata).equals("si") && !(entrata).equals("no") && !(entrata).equals("nessuna") && nessuna==true){
                        err="non presente.";
                        continue;
                    }
                    
                    if(entrata.equals("si") && ordB==true  && nessuna==true){
                        node=alb.node5.parent;
                        continue;
                    }
                    
                    if(entrata.equals("no") && ordB==true  && nessuna==true){
                        out.println("Ordinazione effettuata da parte di? ");
                        entrata=in.readLine();
                        System.out.println("Il cliente "+entrata+" ha effettuato la seguente ordinazione: "+ordine(ordinazione));
                        System.out.println("Per un totale di "+pagamento(ordinazione).toString());
                        out.println("Ordinazione effettuata per un totale di "+ pagamento(ordinazione).toString());
                        break;
                    }
                    //fine ordinazione solo bibita
                }
            } catch (NullPointerException ex) {
                System.out.println("un utente si è disconnesso");
                try {
                    in.close();
                    out.close();
                } catch (IOException ex1) {
                }
            } catch (IOException ex) {
            }
            break;
        }
        try {
            in.close();
            out.close();
        } catch (IOException ex) {
        }
    }
    
    public Double pagamento(ArrayList <Prodotto> list){
        double pagamento=0;
        for(int i=0;i<list.size();i++){
            pagamento+=list.get(i).prezzo;
        }
        return pagamento;
    }
    
    public boolean presenteP(String str){
        boolean pres=false;
        for(int i=0;i<Pizzeria.getMp().size();i++){
            if(str.equals(Pizzeria.getMp().get(i).nomeP)){
                pres=true;
                break;
            }
        }
        return pres;
    }
    
    public String prendiPizz(String str){
        for(int i=0;i<Pizzeria.getMp().size();i++){
            if(str.equals(Pizzeria.getMp().get(i).nomeP)){
                break;
            }
        }
        return str;
    }
    
    public Double prendiPrezzP(String str){
        double price=0;
        for(int i=0;i<Pizzeria.getMp().size();i++){
            if(str.equals(Pizzeria.getMp().get(i).nomeP)){
                price=Pizzeria.getMp().get(i).prezzo;
                break;
            }
        }
        return price;
    }
    
    public boolean presenteB(String str){
        boolean pres=false;
        for(int i=0;i<Pizzeria.getMb().size();i++){
            if(str.equals(Pizzeria.getMb().get(i).nomeP)){
                pres=true;
                break;
            }
        }
        return pres;
    }
    
    public String prendiBib(String str){
        for(int i=0;i<Pizzeria.getMb().size();i++){
            if(str.equals(Pizzeria.getMb().get(i).nomeP)){
                break;
            }
        }
        return str;
    }
    
    public Double prendiPrezzB(String str){
        double price=0;
        for(int i=0;i<Pizzeria.getMb().size();i++){
            if(str.equals(Pizzeria.getMb().get(i).nomeP)){
                price=Pizzeria.getMb().get(i).prezzo;
                break;
            }
        }
        return price;
    }
    
    public String ordine(ArrayList <Prodotto> list){
        String str="";
        for(int i=0;i<list.size();i++){
            str+="° "+list.get(i).nomeP+" -> "+list.get(i).prezzo+"€";
        }
        return str;
    }
}
