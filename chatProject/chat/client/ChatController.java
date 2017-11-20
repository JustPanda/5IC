package chat.client;

import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.event.*;
import javafx.stage.*;
import javafx.fxml.*;
import java.net.*;
import com.jfoenix.controls.*;
import javafx.animation.*;
import java.io.*;
import javafx.util.*;
import javafx.scene.input.*;
import javafx.scene.image.*;
import java.util.*;
import javafx.collections.*;
import javafx.application.*;

public class ChatController
{
    private final static String LOGIN_PATH="chat/client/views/Login.fxml";

    public ChatController(){}

    @FXML
    private JFXButton logoutButton;

    @FXML
    private JFXButton deleteAccountButton;

    @FXML
    private Label notificationLabel;

    @FXML
    private SplitPane pane;

    @FXML
    private AnchorPane topPane;

    @FXML
    private AnchorPane leftPane;

    @FXML
    private JFXListView usersList;

    @FXML
    private AnchorPane conversation;

    @FXML
    private Label noSelection;

    private LinkedList<String> contacts=new LinkedList<>();

    private HashMap<String, Conversation> conversations=new HashMap<>();

    private String otherPerson;

    @FXML
    private void initialize()
    {
        animate(pane);
        leftPane.maxWidthProperty().bind(pane.widthProperty().multiply(0.30));
        leftPane.minWidthProperty().bind(pane.widthProperty().multiply(0.30));
        topPane.maxHeightProperty().bind(leftPane.heightProperty().multiply(0.1591));
        topPane.minHeightProperty().bind(leftPane.heightProperty().multiply(0.1591));

        Client.initChat(this);
    }

    @FXML
    public void changeChat(MouseEvent e)
    {
        closeNotificationLabel(e);
        otherPerson=usersList.getSelectionModel().getSelectedItem().toString();

        ((AnchorPane) conversation).getChildren().setAll(conversations.get(otherPerson));
    }

    public void setContacts(LinkedList<String> newContacts)
    {
        contacts=newContacts;
        for(String s:contacts)
        {
            if(!conversations.containsKey(s))
                conversations.put(s, new Conversation(s));
        }
        usersList.setItems(FXCollections.observableList(contacts));
    }

    public void addContact(String s)
    {
        contacts.add(s);
        usersList.setItems(FXCollections.observableList(contacts));
        if(!conversations.containsKey(s))
            conversations.put(s, new Conversation(s));
    }

    public void removeContact(String s)
    {
        contacts.remove(s);
        usersList.setItems(FXCollections.observableList(contacts));
        conversations.remove(s);
        Platform.runLater(new Runnable()
        {
            @Override
            public void run()
            {
                if(s.equals(otherPerson))
                {
                    ((AnchorPane) conversation).getChildren().setAll(noSelection);
                    animate(noSelection);
                }
            }
        });
    }

    public void newMessage(String from, String message)
    {
        Platform.runLater(new Runnable()
        {
            @Override
            public void run()
            {
                conversations.get(from).receiveMessage(message);
                if(!from.equals(otherPerson))
                {
                    notificationLabel.setText("New Message from "+from);
                    notificationLabel.setVisible(true);
                    logoutButton.setVisible(false);
                    deleteAccountButton.setVisible(false);
                    animate(notificationLabel);
                }
            }
        });
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
    private void closeNotificationLabel(MouseEvent e)
    {
        notificationLabel.setVisible(false);
        if(!logoutButton.isVisible())
        {
            logoutButton.setVisible(true);
            deleteAccountButton.setVisible(true);
            animate(logoutButton);
            animate(deleteAccountButton);
        }
    }

    @FXML
    private void handleDeleteAccountButton(ActionEvent e)
    {
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION, "PERMANENTLY delete your account?");
        alert.showAndWait().filter(response -> response == ButtonType.OK).ifPresent(response ->
        {
            Client.deleteAccount();
            handleLogoutButton(e);
        });

    }
    
    @FXML
    private void handleLogoutButton(ActionEvent e)
    {
        Client.disconnect();
        JFXButton b=(JFXButton)e.getSource();
        Stage stage=(Stage)b.getScene().getWindow();
        Parent root=null;
        try
        {
            root=FXMLLoader.load(getClass().getClassLoader().getResource(LOGIN_PATH));
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }
        stage.setScene(new Scene(root));
    }
}
