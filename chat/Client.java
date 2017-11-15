package javafxapplication2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author massi
 */
public class Client extends Application 
{
    static Socket s;
    static PrintWriter out;
    static BufferedReader input;
    static ObservableList<String> lp;
    static String na;
    static ArrayList<String> messaggiR = new ArrayList<>();

  /*  @FXML
    public static VBox vboxx;
    */
    
    public Client() 
    {
        try 
        {
            String serverAddress = "localhost";
            s = new Socket(serverAddress, 9898);
            out = new PrintWriter(s.getOutputStream(), true);
            input = new BufferedReader(new InputStreamReader(s.getInputStream()));
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("OleChat");
        Parent root = FXMLLoader.load(getClass().getResource("loginFXML.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) throws IOException {
        launch(args);
    }
}
