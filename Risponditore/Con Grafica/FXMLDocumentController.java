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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.input.MouseEvent;
//import java.awt.event.MouseEvent;

import javafx.util.StringConverter;

/**
 *
 * @author marck
 */
public class FXMLDocumentController implements Initializable {

    Client client;
    String bibita;
    String dimensione;
    ObservableList<String> listinoPizze;
    ObservableList<String> listinoBibite;
    
    @FXML
    private Label label;

    @FXML
    private Button confermaNome;

    @FXML
    private TextField nome;
    
    @FXML
    private ListView listPizze;
    
    @FXML
    private Button addPizza;
    
    @FXML
    private TextField entryPizze;
    
    @FXML
    private Button vaiBibite;
    
    @FXML
    private ListView listBibite;
    
    @FXML
    private Button addBibita;
    
    @FXML
    private TextField entryBibite;
    
    @FXML
    private Button vaiDimensioni;
    
    @FXML
    private ListView listDimensioni;
    
    @FXML
    private TextField entryDimensioni;
    
    @FXML
    private Button tornaBibite;
    
    @FXML
    private Button Conferma;
    
    @FXML
    private Label scontrino;
            
    
    
    public FXMLDocumentController() throws IOException
    {
        client = new Client();
 
    }
 
    @FXML
    public void confermaNomeClick(ActionEvent event) throws IOException {
      
        String testo = nome.getText();
        client.invia(testo);
        System.out.println("ho inviato "+testo);
        
        confermaNome.setDisable(true);
        nome.setDisable(true);
        
        client.invia("chiediPizze");
        listinoPizze = FXCollections.observableArrayList();
        String ad = "";
        do
        {
            ad = client.input.readLine();
            if(!ad.equals("STOP"))
            {
                listinoPizze.add(ad);          
            }
        }
        while(!ad.equals("STOP"));
        listPizze.setItems(listinoPizze);
           
        listPizze.setDisable(false);
        entryPizze.setDisable(false);
        addPizza.setDisable(false);
        vaiBibite.setDisable(false);
    }
    
    @FXML
    public void aggiungiPizza(ActionEvent event) throws IOException
    {       
        client.out.println(entryPizze.getText());
    }
    

    @FXML
    public void aggiungiDimensioni(ActionEvent event) throws IOException
    {
        dimensione = entryDimensioni.getText();
        client.out.println(dimensione);
        System.out.println("gli mando "+ dimensione);
        listBibite.setDisable(false);
        entryBibite.setDisable(false);
        vaiDimensioni.setDisable(false);
        
        listDimensioni.setDisable(true);
        entryDimensioni.setDisable(true);
       
        tornaBibite.setDisable(true);
         Conferma.setDisable(false);
    }
    
     @FXML
    public void vaiABibite(ActionEvent event) throws IOException
    {
        
        client.out.println("STOP");
        
        client.out.println("chiediBibite");
        //////////////////
        
        scontrino.setText(scontrino.getText()+"Una ");

        String le = "";
        while(!le.equals("STOP"))
        {
            le = client.input.readLine();
            if(!le.equals("STOP"))
            scontrino.setText(scontrino.getText()+le+", ");
        }
        
        
        //////////////////
        
        listinoBibite = FXCollections.observableArrayList();
        String ad = "";
        do
        {
            ad = client.input.readLine();
            if(!ad.equals("STOP"))
            {
                listinoBibite.add(ad);          
            }
        }
        while(!ad.equals("STOP"));
        System.out.println("Gli arriva il listino");
        listBibite.setItems(listinoBibite);
        
        listPizze.setDisable(true);
        entryPizze.setDisable(true);
        addPizza.setDisable(true);
        vaiBibite.setDisable(true);
        
        listBibite.setDisable(false);
        entryBibite.setDisable(false);
        vaiDimensioni.setDisable(false);
        
    }
    
     @FXML
    public void vaiADimensioni(ActionEvent event) throws IOException
    {
        bibita = entryBibite.getText();
        System.out.println("invia "+bibita);
        
        client.out.println(bibita);
        listBibite.setDisable(true);
        entryBibite.setDisable(true);
        vaiDimensioni.setDisable(true);
        
        listDimensioni.setDisable(false);
        entryDimensioni.setDisable(false);     
        tornaBibite.setDisable(false);
        
        Conferma.setDisable(true);
        
        ObservableList<String> l = FXCollections.observableArrayList(); 
        l.add("Piccola");
        l.add("Media");
        l.add("Grande");
        
        listDimensioni.setItems(l);
    }
    
    @FXML
    public void confermaOnClick(ActionEvent event) throws IOException
    {
        listBibite.setDisable(true);
        entryBibite.setDisable(true);
        vaiDimensioni.setDisable(true);
        Conferma.setDisable(true);
        
        client.out.println("STOP");
        client.out.println("chiediTutto");
        
        scontrino.setText(scontrino.getText()+"\n\r");

        String le = "";
        int z = 0;
        scontrino.setText(scontrino.getText()+"Una ");

                 
        while(!le.equals("STOP"))
        {
            le = client.input.readLine();
            if(!le.equals("STOP"))
            {
                if(z == 0)
                {
                    scontrino.setText(scontrino.getText()+le+" ");
                    z =1;
                }
                else
                {
                    scontrino.setText(scontrino.getText()+le+", ");
                    z=0;
                }
            }     
        }

        listDimensioni.setDisable(true);
        entryDimensioni.setDisable(true);
        tornaBibite.setDisable(true);
    }
    
    @FXML public void cliccatoBibite(MouseEvent arg0) 
    {
        entryBibite.setText(listBibite.getSelectionModel().getSelectedItem().toString());
    }
    
    @FXML public void cliccatoPizze(MouseEvent arg0) 
    {
        entryPizze.setText(listPizze.getSelectionModel().getSelectedItem().toString());
    }
    
    @FXML public void cliccatoDimensione(MouseEvent arg0) 
    {
        entryDimensioni.setText(listDimensioni.getSelectionModel().getSelectedItem().toString());
    }

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listPizze.setDisable(true);
        entryPizze.setDisable(true);
        addPizza.setDisable(true);
        vaiBibite.setDisable(true);
        
    
        
        listBibite.setDisable(true);
        entryBibite.setDisable(true);
        vaiDimensioni.setDisable(true);
        
        listDimensioni.setDisable(true);
        entryDimensioni.setDisable(true);
      
        tornaBibite.setDisable(true);
        
       
        
          
    }    
}

    
   
