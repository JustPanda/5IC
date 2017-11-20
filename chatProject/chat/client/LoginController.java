package chat.client;

import javafx.scene.*;
import javafx.scene.control.*;
import javafx.geometry.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.event.*;
import javafx.stage.*;
import javafx.fxml.*;
import javafx.application.*;
import java.net.*;
import com.jfoenix.controls.*;
import javafx.animation.*;
import javafx.util.*;
import java.util.*;
import java.io.*;

public class LoginController
{
    private final static String START_PATH="chat/client/views/Start.fxml";
    private final static String CHAT_PATH="chat/client/views/Chat.fxml";

    private final static HashMap<String,String> errors=createErrorsMap();

    public LoginController(){}

    @FXML
    private JFXTextField usernameField;

    @FXML
    private JFXPasswordField passwordField;

    @FXML
    private AnchorPane pane;

    @FXML
    private Label errorLabel;

    @FXML
    private JFXSpinner loading;

    @FXML
    private void initialize()
    {
        animate(pane);
        errorLabel.setAlignment(Pos.CENTER);

        usernameField.setLabelFloat(true);
        passwordField.setLabelFloat(true);

        Platform.runLater(new Runnable(){
            @Override
            public void run()
            {
                usernameField.requestFocus();
            }
        });
    }

    private static HashMap<String,String> createErrorsMap()
    {
        HashMap<String, String> m=new HashMap<>();

        m.put("emptyName", "You're missing username");
        m.put("emptyPassword", "You're missing password");
        m.put("name", "There is no account with this username");
        m.put("password", "Wrong password");
        m.put("online", "This account is already in use");
        m.put("server", "Connection to server failed");

        return m;
    }

    private void animate(Node n)
    {
        FadeTransition x=new FadeTransition(new Duration(1000), n);
        x.setFromValue(0);
        x.setToValue(100);
        x.setCycleCount(1);
        x.setInterpolator(Interpolator.LINEAR);
        x.play();
    }

    @FXML
    public void handleBackButton(ActionEvent e)
    {
        JFXButton b=(JFXButton)e.getSource();
        Stage stage=(Stage)b.getScene().getWindow();
        Parent root=null;
        try
        {
            root=FXMLLoader.load(getClass().getClassLoader().getResource(START_PATH));
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }
        stage.setScene(new Scene(root));
    }

    @FXML
    public void closeErrorLabel(MouseEvent e)
    {
        errorLabel.setVisible(false);
    }

    @FXML
    public void handleLoginButton(ActionEvent e)
    {
        boolean nameNotEmpty=!usernameField.getText().equals("");
        boolean passwordNotEmpty=!passwordField.getText().equals("");

        if(nameNotEmpty && passwordNotEmpty)
        {
            errorLabel.setVisible(false);
            loading.setVisible(true);
            String username=usernameField.getText();
            String res=Client.login(username, passwordField.getText());
            if(res.equals("ok"))
            {
                Client.setName(username);
                Stage stage;
                Parent root=null;

                JFXButton b=(JFXButton)e.getSource();
                stage=(Stage)b.getScene().getWindow();
                try
                {
                    root=FXMLLoader.load(getClass().getClassLoader().getResource(CHAT_PATH));
                }
                catch(IOException ex)
                {
                    ex.printStackTrace();
                }
                stage.setScene(new Scene(root));
            }
            else
            {
                errorLabel.setText(errors.get(res));
                errorLabel.setVisible(true);
                animate(errorLabel);
            }
        }
        else if(!nameNotEmpty)
        {
            errorLabel.setText(errors.get("emptyName"));
            errorLabel.setVisible(true);
            animate(errorLabel);
        }
        else
        {
            errorLabel.setText(errors.get("emptyPassword"));
            errorLabel.setVisible(true);
            animate(errorLabel);
        }
        loading.setVisible(false);
    }
}
