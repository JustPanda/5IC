/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import javafxapplication2.Server;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
/**
 *
 * @author massi
 */
public class database 
{
    ArrayList<String> user;
    ArrayList<String> password;
    ArrayList<String> m;

    Statement stato;
    Connection conn = null;
    String fare ="";
    String tabella = "";
    
    public void registrati(String[] registrazione, PrintWriter out, BufferedReader in) throws SQLException, ClassNotFoundException, IOException
    {           
        fare = "registra";
        user = new ArrayList<String>();
        //Legge
        Class.forName("org.sqlite.JDBC");
        String url = "jdbc:sqlite:E:\\PROGETTITPSIT\\chat\\data.db";

        boolean rifai = true;
        while(rifai && fare.equals("registra"))
        {
            //legge
            conn = DriverManager.getConnection(url);
            stato = conn.createStatement();
            ResultSet rs = stato.executeQuery("SELECT user, password FROM login");
            while (rs.next())
            {
                user.add(rs.getString("user"));
            }
            System.out.println(user);

            conn.close();

           //scrive

            String nome = registrazione[0];
            String password = registrazione[1];

            boolean controllato = true;
            for(int i = 0; i<user.size(); i++)
            {
                if(user.get(i).equals(nome))
                {
                    controllato = false;
                    break;
                }
            }

            if(controllato)
            {
                Server.nameList.add(registrazione[0]);
                user.add(nome);
                conn = DriverManager.getConnection(url);
                System.out.println("si puo aggiungere: "+nome);
                PreparedStatement pstmt = conn.prepareStatement("INSERT INTO login (user, password) VALUES (?,?)");
                pstmt.setString(1, nome);
                pstmt.setString(2, password);  
                pstmt.executeUpdate();                 
                out.println("BEA");
                rifai = false;
            }
            else
            {
                out.println("Nome utente gia usato, scegline un altro");
                System.out.println("esiste gia");

                registrazione[0] = in.readLine();
                registrazione[1] = in.readLine();
                fare = in.readLine();
            }
            conn.close();
        }
    }
    
    public int accedi(String[] registrazione, PrintWriter out, BufferedReader in) throws SQLException, ClassNotFoundException, IOException
    {
        fare = "accedi";
        user = new ArrayList<String>();
        password =new ArrayList<String>();

        int indice = 0;
        //Legge
        Class.forName("org.sqlite.JDBC");
        String url = "jdbc:sqlite:E:\\PROGETTITPSIT\\chat\\data.db";

        boolean rifai = true;
        while(rifai && fare.equals("accedi"))
        {
            conn = DriverManager.getConnection(url);
            stato = conn.createStatement();
            ResultSet rs = stato.executeQuery("SELECT user, password FROM login");
            while (rs.next())
            {
                user.add(rs.getString("user"));
                password.add(rs.getString("password"));
            }
            conn.close();

            boolean controllato = true;

            for(int i = 0; i<user.size(); i++)
            {
                if(user.get(i).equals(registrazione[0]))
                {
                    indice = i;
                    controllato = false;
                    break;
                }
            }

            System.out.println("indice: "+indice);
            System.out.println(password);
            if(password.get(indice).equals(registrazione[1]))
            {
                System.out.println("acceduto");
                rifai = false;
                out.println("acceduto");
                System.out.println("fine del lavoro sul database");
                Server.nameList.add(registrazione[0]);
            }
            else
            {
                out.println("rotto");
                System.out.println("no acceduto");
                registrazione[0] = in.readLine();
                System.out.println("riceve qualcosa");
                registrazione[1] = in.readLine();
                fare = in.readLine();
            }
        }
        System.out.println("esce del while");
        return 0;
    }

    public void mandaLista(PrintWriter out,  BufferedReader in) throws IOException
    { 
       System.out.println("nel metodo del database");
       out.println("MANDALISTA");
        for(int i = 0; i<user.size(); i++)
        {
            out.println(user.get(i));
        }
        out.println("STOP");
    }

    void messaggia(Socket s, PrintWriter out, BufferedReader in) throws IOException, ClassNotFoundException, SQLException 
    {
        m = new ArrayList<String>();
        tabella = in.readLine();
        Class.forName("org.sqlite.JDBC");
        String url = "jdbc:sqlite:E:\\PROGETTITPSIT\\chat\\data.db";
        conn = DriverManager.getConnection(url);
        Statement v = conn.createStatement();                
        v.executeUpdate("CREATE TABLE IF NOT EXISTS "+ tabella +" (  messaggi TEXT )");
        System.out.println("fatto il metodo, creato: "+tabella);
        ResultSet rs = v.executeQuery("SELECT messaggi FROM "+tabella);
        while (rs.next())
        {
            m.add(rs.getString("messaggi"));
        }
        for(int i = 0; i<m.size(); i++)
        {
             out.println(m.get(i));
        }
        out.println("STOP");
        System.out.println(m);
        conn.close();
        
    }

    void scrivi(Socket socket, PrintWriter out, BufferedReader in, String gne) throws ClassNotFoundException, SQLException 
    {
        
        System.out.println(Server.nameList);
        System.out.println("su scrivi, tabella: "+tabella);
        System.out.println("su scrivi, gne: "+gne);
        Class.forName("org.sqlite.JDBC");
        String url = "jdbc:sqlite:E:\\PROGETTITPSIT\\chat\\data.db";
        conn = DriverManager.getConnection(url);
        PreparedStatement pstmt = conn.prepareStatement("INSERT INTO "+tabella+" (messaggi) VALUES (?)");
        pstmt.setString(1, gne);
        System.out.println("sul metodo scrivi ha scritto: "+gne);
        pstmt.executeUpdate();
        conn.close();
        int index = 0;
        String ricevente = "";
        for(int i = 0; i<gne.length(); i++)
        {
            if(gne.charAt(i) == '#')
            {
                index = i+1;
                System.out.println("i: "+gne.charAt(i));
                break;
            }
        }
        if(gne.charAt(index) == '#')
        {
            System.out.println("index: "+gne.charAt(index));
            ricevente = gne.substring(0, index-1);
            System.out.println("ricevente: "+ricevente);
        }
        int u = 0;
        boolean ok = false;
        for(int i = 0; i<Server.nameList.size(); i++)
        {
            if(Server.nameList.get(i).equals(ricevente))
            {
                u = i;
                ok = true;
            }
        }
        if(ok)
        {
            System.out.println("a: "+ Server.nameList.get(u)+" scrive: "+gne);
            Server.clientList.get(u).println("INVIAMESSAGGIO");
            Server.clientList.get(u).println(gne);
            System.out.println("a: "+ Server.nameList.get(u)+" scrive: "+gne);

        }
            
        
    }
}

