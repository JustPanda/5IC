package chat.client;

import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.event.*;
import javafx.stage.*;
import javafx.fxml.*;
import java.net.*;
import com.jfoenix.controls.*;
import javafx.animation.*;
import java.io.*;
import javafx.util.*;

public class StartController
{
    private final static String LOGIN_PATH="chat/client/views/Login.fxml";
    private final static String REGISTRATION_PATH="chat/client/views/Registration.fxml";

    public StartController(){}

    @FXML
    private AnchorPane pane;

    @FXML
    private void initialize()
    {
        animate();
    }

    private void animate()
    {
        FadeTransition x=new FadeTransition(new Duration(1000), pane);
        x.setFromValue(0);
        x.setToValue(100);
        x.setCycleCount(1);
        x.setInterpolator(Interpolator.LINEAR);
        x.play();
    }

    @FXML
    public void handleButtons(ActionEvent e)
    {
        Stage stage;
        Parent root=null;

        JFXButton b=(JFXButton)e.getSource();
        stage=(Stage)b.getScene().getWindow();
        String path=b.getId().equals("loginButton")?LOGIN_PATH:REGISTRATION_PATH;
        try
        {
            root=FXMLLoader.load(getClass().getClassLoader().getResource(path));
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }
        stage.setScene(new Scene(root));
    }
}
