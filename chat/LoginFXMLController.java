/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication2;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafxapplication2.database;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class LoginFXMLController 
{
    boolean caca=true;
    double aa;
    double height;
    ArrayList <Rectangle> rectangles;
    ArrayList <Text> texts;
    String help;
    double mexY=10;
    double mexSxX=30;
    double mexDxX=316;
    Insets paddSuo;
    Insets paddMio;
    HBox hBox;
    
    
    int e= 0;
    ObservableList<String> l;
    String copia;
    
    @FXML
    private Button reload;
    
    @FXML
    private VBox vbox;
    
    @FXML
    private ListView<String> listaNomi;

    @FXML
    private ScrollPane scrollpane;
    
    @FXML
    private Label nomeChat;
    
    @FXML
    private Button invia;
    
    @FXML
    private TextArea testo;
    
    @FXML
    private Button accedi;
    
    @FXML
    private TextField utente;

    @FXML
    private TextField password;

    @FXML
    public void accediClick(ActionEvent event) throws IOException
    {
        String[] nome = new String[2];
        nome[0] = utente.getText();
        nome[1] = password.getText();
        
        Client.out.println(nome[0]);
        Client.out.println(nome[1]);
        
        Client.out.println("accedi");
        
        String fai = Client.input.readLine();
        System.out.println("fai: "+fai);
        if(fai.equals("acceduto"))
        {
            Client.na = nome[0];
           /// copia = l.get(0);
            Stage stage = (Stage) accedi.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("Chat.fxml"));
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();          
            popolaLista();
            stage.setTitle(nome[0]);
        }
    }

    
    @FXML
    void registerClick(ActionEvent event) throws SQLException, Exception 
    {
        String[] nome = new String[2];
        nome[0] = utente.getText();
        nome[1] = password.getText();
        System.out.println("va sul register");
        
        Client.out.println(nome[0]);
        Client.out.println(nome[1]);
        Client.out.println("registra");
        
        System.out.println("client : ho spedito le robe al server");
        //
        String registrato = Client.input.readLine();
        System.out.println(registrato);
        
        if(registrato.equals("BEA"))
        {
            Client.na = nome[0];
            Stage stage = (Stage) accedi.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("Chat.fxml"));
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();          
            popolaLista();
            stage.setTitle(nome[0]);
        }
        
    }
    @FXML
    private  void inviaMessaggio(ActionEvent event) throws IOException 
    {      
        String chat = listaNomi.getSelectionModel().getSelectedItem();
        nomeChat.setText("Chat con: "+chat);
         
        Client.out.println (chat+"##"+testo.getText());
        if(chat.compareTo(Client.na)<0)
        {
            Client.out.println(chat+"$$"+Client.na);
        }
        else
        {
            Client.out.println(Client.na+"$$"+chat);
        }
        String terrona = testo.getText();
        testo.setText("");
          if(caca)
            {
               caca=false;
               aa=mexDxX;
               rectangles=new ArrayList();
               texts=new ArrayList();
               paddSuo=new Insets(10,300,10,10);
               paddMio=new Insets(10,30,10,10);
            }
            else 
            {
                mexY+=height+10;
            }
            help = terrona;
            int righe = help.length() / 15;
            if (help.length() % 15 != 0)righe += 1;
            height = righe * 25;
            if (height == 0)height = 25;
            for(int i=1;i<righe;i++)
            {
                help = help.substring(0, i*15) + "\n" + help.substring(i*15, help.length());
            }
            Label l=new Label(help);
            l.setStyle("-fx-border-color: Blue; -fx-border-width:3; -fx-background-color:Transparent; -fx-padding: 0 10 0 10;");
            l.setFont(Font.font("Segoe Print Bold"));
            l.setTextFill(Color.BLACK);
            hBox=new HBox();
            hBox.getChildren().add(l);
            hBox.setAlignment(Pos.BASELINE_RIGHT);
            System.out.println("dovrebbe scrivere: "+terrona+" a destra");
            hBox.setPadding(paddSuo);
            vbox.getChildren().add(hBox);
            scrollpane.vvalueProperty().bind(vbox.heightProperty());
      
        
    }

    public void popolaLista() 
    {
        System.out.println("aspetta di ricevere");            
        Client.out.println("CHIEDILISTA");
        Lettore l = new Lettore(Client.s, this);
    }
    
    @FXML
    void reload(ActionEvent event) 
    {
        System.out.println("le liste: "+Client.lp);
        for(int i = 0;i<Client.lp.size(); i++)
        {
            System.out.println("i: "+i+", lista: "+Client.lp.get(i));
            if(Client.lp.get(i).equals(Client.na))
            {
                System.out.println("rimosso: "+Client.lp.get(i));
                Client.lp.remove(i);
            }
        }
        listaNomi.setItems(Client.lp);
        listaNomi.getSelectionModel().select(0);
        nomeChat.setText(listaNomi.getSelectionModel().getSelectedItem());
    }
    
    

    List<Label> mess= new ArrayList<>();
    
    int rimuovi=0;
    @FXML
    void onmouseclicked(MouseEvent event) throws IOException 
    {
      
        String chat = listaNomi.getSelectionModel().getSelectedItem();
        nomeChat.setText(chat);
         
        Client.out.println("MESSAGGI");
        if(chat.compareTo(Client.na)<0)
        {
            Client.out.println(chat+"$$"+Client.na);
        }
        else
        {
            Client.out.println(Client.na+"$$"+chat);
        }
        
        ///////////////////////////////////ù
        for(int j = 0; j<Client.messaggiR.size(); j++)
        {
            scrivi(Client.messaggiR.get(j));  
        }
        rimuovi=Client.messaggiR.size()-1;
    }
    
    public void scrivi(String s)
    {
        boolean seh = false;
        int ind = 0;
        boolean perForza = false;
        String rice = "";
        for(int i = 0; i<s.length(); i++)
        {
            if(s.charAt(i) == '#')
            {
                seh = true;
                ind = i+1;
                break;
            }
        }
        if(seh)
        {
            if(s.charAt(ind)=='#')
            {
                
                seh = false;
                perForza = true;
                rice = s.substring(0, ind-1);
            }
        }
        if(perForza)
        {
            if(caca)
            {
               caca=false;
               aa=mexDxX;
               rectangles=new ArrayList();
               texts=new ArrayList();
               paddSuo=new Insets(10,300,10,10);
               paddMio=new Insets(10,30,10,10);
            }
            else 
            {
                mexY+=height+10;
            }
            help = s.substring(ind+1);
            int righe = help.length() / 15;
            if (help.length() % 15 != 0)righe += 1;
            height = righe * 25;
            if (height == 0)height = 25;
            for(int i=1;i<righe;i++)
            {
                help = help.substring(0, i*15) + "\n" + help.substring(i*15, help.length());
            }
            Label l=new Label(help);
            l.setStyle("-fx-border-color: Blue; -fx-border-width:3; -fx-background-color:Transparent; -fx-padding: 0 10 0 10;");
            l.setFont(Font.font("Segoe Print Bold"));
            l.setTextFill(Color.BLACK);
            hBox=new HBox();
            hBox.getChildren().add(l);
            hBox.setAlignment(Pos.BASELINE_RIGHT);
           
            if(rice.equals(Client.na))
            {
                System.out.println("dovrebbe scrivere: "+s.substring(ind+1)+" a destra");
                hBox.setPadding(paddMio);
            }
            else
            {
                System.out.println("dovrebbe scrivere: "+s.substring(ind+1)+" a sinistra");
                hBox.setPadding(paddSuo);
            }
            vbox.getChildren().add(hBox);
            scrollpane.vvalueProperty().bind(vbox.heightProperty());

        }
        


    }
    String x = "";
    public  void aggiungiMessaggio(String messaggio)
    {
        
         x = messaggio;
        Platform.runLater(new Runnable()
        {
        @Override
        public void run()
        {
        System.out.println("da messaggio di "+Client.na+" riceve: " +x);
        scrivi(x);
        }
        /*   vbox.getChildren().add(hBox);
        scrollpane.vvalueProperty().bind(vbox.heightProperty());
     }
});
/*System.out.println("da messaggio di "+Client.na+" riceve: " +messaggio);
scrivi(messaggio);*/
    });
                }
}