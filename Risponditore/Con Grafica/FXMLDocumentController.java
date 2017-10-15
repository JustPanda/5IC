/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.collections.FXCollections;

import javafx.collections.ObservableList;

import javafx.fxml.FXML;

import javafx.scene.control.Button;

import javafx.scene.control.CheckBox;

import javafx.scene.control.ComboBox;

import javafx.scene.control.Hyperlink;

import javafx.scene.control.ListCell;

import javafx.scene.control.ListView;

import javafx.scene.control.Slider;

import javafx.scene.control.TextArea;

import javafx.scene.control.TextField;

import javafx.util.StringConverter;

/**
 *
 * @author marck
 */
public class FXMLDocumentController implements Initializable {

    Client client;
    
    public FXMLDocumentController() throws IOException
    {
        client = new Client();
    }
    
    @FXML
    private Label label;

    @FXML
    private Button confermaNome;

    @FXML
    private TextField nome;
 
    @FXML
    private void confermaNomeClick(ActionEvent event) throws IOException {
        
        String testo = nome.getText();
        System.out.println("ad invia mando "+testo);
        client.invia(testo);
        System.out.println("ho inviato "+testo);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
     /* public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);
        String serverAddress = "localhost"; // server string
        Socket s = new Socket(serverAddress, 9898);
        BufferedReader input
                = new BufferedReader(new InputStreamReader(s.getInputStream()));
        //
        PrintWriter out = new PrintWriter(s.getOutputStream(), true);
        //

        String str = "ciao cliente, digita il tuo nome";
        System.out.println(str);
        do 
        {
            System.out.println(input.readLine());

            chiediPizze(in, out, input);
            chiediBibite(in, out, input);
        } while (str != null);

    }

    public static void chiediPizze(Scanner in, PrintWriter out, BufferedReader input) throws IOException {
        String pizz = "cdcd";
        while (!pizz.equals("STOP")) {
            pizz = in.nextLine();
            out.println(pizz);
            System.out.println(input.readLine());
        }
    }

    public static void chiediBibite(Scanner in, PrintWriter out, BufferedReader input) throws IOException {
        String bibite = "cdcd";
        while (!bibite.equals("STOP")) {
            bibite = in.nextLine();
            out.println(bibite);
            System.out.println(input.readLine());
            out.println(in.nextLine());
            System.out.println(input.readLine());
        }
    }*/
}

    
   
