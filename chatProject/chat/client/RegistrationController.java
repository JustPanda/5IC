package chat.client;

import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.input.*;
import javafx.event.*;
import javafx.stage.*;
import javafx.fxml.*;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.animation.*;
import javafx.util.*;
import javafx.application.*;
import java.net.*;
import com.jfoenix.controls.*;
import java.io.*;
import java.util.*;
import java.util.regex.Pattern;
import java.lang.reflect.Field;
import javafx.geometry.*;

public class RegistrationController
{
    private final static String START_PATH="chat/client/views/Start.fxml";
    private final static String CHAT_PATH="chat/client/views/Chat.fxml";

    private final static HashMap<String,String> errors=createErrorsMap();

    private final static Pattern[] PASSWORD_PATTERNS={
        Pattern.compile(".*\\d+.*"),
        Pattern.compile(".*[A-Z]+.*"),
        Pattern.compile(".*[a-z]+.*")
    };

    @FXML
    private JFXTextField usernameField;

    @FXML
    private JFXTextField confirmUsernameField;

    @FXML
    private Label confirmUsernameTooltip;

    @FXML
    private JFXPasswordField passwordField;

    @FXML
    private JFXPasswordField confirmPasswordField;

    @FXML
    private Label passwordTooltip;

    @FXML
    private Label confirmPasswordTooltip;

    @FXML
    private Tooltip tooltip;

    @FXML
    private JFXSpinner loading;

    @FXML
    private Label errorLabel;

    @FXML
    private AnchorPane pane;

    public RegistrationController(){}

    @FXML
    private void initialize()
    {
        animate(pane);
        errorLabel.setAlignment(Pos.CENTER);
        usernameField.setLabelFloat(true);
        confirmUsernameField.setLabelFloat(true);
        passwordField.setLabelFloat(true);
        confirmPasswordField.setLabelFloat(true);
        changeTooltipsShowDelay();

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
        HashMap<String,String> m=new HashMap<>();

        m.put("empty", "You're missing something");
        m.put("names", "Usernames do not match");
        m.put("bad", "Password is not accepted. Check the tip in the password field");
        m.put("passwords", "Passwords do not match");
        m.put("exist", "An account with this username already exists");

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

    private static boolean checkPassword(String s)
    {
        boolean isAccepted=true;
        if(s.length()<6)
            return false;
        for(Pattern p:PASSWORD_PATTERNS)
        {
            if(!p.matcher(s).matches())
                return false;
        }
        return true;
    }

    private void changeTooltipsShowDelay()
    {
        try
        {
            Field fieldBehavior = tooltip.getClass().getDeclaredField("BEHAVIOR");
            fieldBehavior.setAccessible(true);
            Object objBehavior = fieldBehavior.get(tooltip);

            Field fieldTimer = objBehavior.getClass().getDeclaredField("activationTimer");
            fieldTimer.setAccessible(true);
            Timeline objTimer = (Timeline) fieldTimer.get(objBehavior);

            objTimer.getKeyFrames().clear();
            objTimer.getKeyFrames().add(new KeyFrame(new Duration(0)));
        } catch (Exception e)
        {
            e.printStackTrace();
        }
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
    public void controlConfirmUsernameField(KeyEvent e)
    {
        boolean matches=usernameField.getText().equals(confirmUsernameField.getText());
        confirmUsernameField.setFocusColor(matches?Paint.valueOf("green"):Paint.valueOf("red"));
        confirmUsernameField.setUnFocusColor(matches?Paint.valueOf("green"):Paint.valueOf("red"));
        confirmUsernameTooltip.setVisible(!matches);
    }

    @FXML
    public void controlPasswordField(KeyEvent e)
    {
        boolean isAccepted=checkPassword(passwordField.getText());

        passwordField.setFocusColor(isAccepted?Paint.valueOf("green"):Paint.valueOf("red"));
        passwordField.setUnFocusColor(isAccepted?Paint.valueOf("green"):Paint.valueOf("red"));
        passwordTooltip.setVisible(!isAccepted);
    }

    @FXML
    public void controlConfirmPasswordField(KeyEvent e)
    {
        String confirmPassword=confirmPasswordField.getText();
        boolean isAccepted=checkPassword(confirmPassword) && confirmPassword.equals(passwordField.getText());
        confirmPasswordField.setFocusColor(isAccepted?Paint.valueOf("green"):Paint.valueOf("red"));
        confirmPasswordField.setUnFocusColor(isAccepted?Paint.valueOf("green"):Paint.valueOf("red"));
        confirmPasswordTooltip.setVisible(!isAccepted);
    }

    @FXML
    public void closeErrorLabel(MouseEvent e)
    {
        errorLabel.setVisible(false);
    }

    @FXML
    public void handleConfirmButton(ActionEvent e)
    {
        boolean allFieldsFilled=!usernameField.getText().equals("") && !confirmUsernameField.getText().equals("") && !passwordField.getText().equals("") && !confirmPasswordField.getText().equals("");

        boolean passwordOk=checkPassword(passwordField.getText());
        boolean usernamesMatch=confirmUsernameField.getText().equals(usernameField.getText());
        boolean passwordsMatch=confirmPasswordField.getText().equals(passwordField.getText());

        JFXButton b=(JFXButton)e.getSource();

        if(allFieldsFilled && passwordOk && usernamesMatch && passwordsMatch)
        {
            errorLabel.setVisible(false);
            loading.setVisible(true);
            String username=usernameField.getText();
            String res=Client.registration(username, passwordField.getText());
            if(res.equals("ok"))
            {
                Client.setName(username);
                Stage stage;
                Parent root=null;

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
        else if(!allFieldsFilled)
        {
            errorLabel.setText(errors.get("empty"));
            errorLabel.setVisible(true);
            animate(errorLabel);
        }
        else if(!passwordOk)
        {
            errorLabel.setText(errors.get("bad"));
            errorLabel.setVisible(true);
            animate(errorLabel);
        }
        else if(!usernamesMatch)
        {
            errorLabel.setText(errors.get("names"));
            errorLabel.setVisible(true);
            animate(errorLabel);
        }
        else
        {
            errorLabel.setText(errors.get("passwords"));
            errorLabel.setVisible(true);
            animate(errorLabel);
        }
    }
}
