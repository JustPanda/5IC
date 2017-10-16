package pizzeria;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author gioele
 */
public class Client {

    public static void main(String[] args) throws IOException {
        Scanner input=new Scanner(System.in);
        String serverAddress="localhost";
        final int port=3333;
        boolean h=false;
        Socket serv = new Socket(serverAddress,port);
        BufferedReader in = new BufferedReader(new InputStreamReader(serv.getInputStream()));
        PrintWriter out = new PrintWriter(serv.getOutputStream(), true);
        String str="";
        while(true){
            if(h==false){
                System.out.println("Premere due volte invio per visualizzare il menu delle pizze e delle bibite");
                h=true;
            }
            str=input.nextLine();
            out.println(str);
            System.out.println(in.readLine());
        }

    }
}
