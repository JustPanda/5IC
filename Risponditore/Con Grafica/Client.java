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
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author marck
 */
public class Client extends Application {
     Socket s ;
    PrintWriter out;
    BufferedReader input;
    
    public Client() throws IOException
    {
        String serverAddress = "localhost"; 
        s = new Socket(serverAddress, 9898);
        out = new PrintWriter(s.getOutputStream(), true);       
        input = new BufferedReader(new InputStreamReader(s.getInputStream()));
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        	
       //stage.setTitle("Hello World");    
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));       
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    public void invia(String s)
    {
        System.out.println("dovrebbe inviare "+s);
        out.println(s);
        System.out.println("inviato "+s);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException
    {
        launch(args);
    }
    
}
