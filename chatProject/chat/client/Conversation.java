package chat.client;

import javafx.scene.layout.*;
import javafx.geometry.*;
import javafx.scene.shape.*;
import javafx.scene.control.*;
import javafx.scene.paint.*;
import javafx.collections.*;
import javafx.scene.*;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.text.*;
import javafx.beans.binding.*;
import com.jfoenix.controls.*;
import javafx.scene.image.*;
import javafx.scene.input.*;

class Conversation extends VBox
{
    private final static String SEND_IMAGE_PATH="chat/client/views/images/send.png";

    private String otherPerson;
    private ObservableList<Node> messages=FXCollections.observableArrayList();

    private Label contactHeader;
    private ScrollPane messageScroller;
    private VBox messageContainer;
    private HBox inputContainer;

    public Conversation(String otherPerson)
	{
        super(5);
        this.otherPerson=otherPerson;
        setupElements();
    }

    private void setupElements()
	{
        setupContactHeader();
        setupMessageDisplay();
        setupInputDisplay();
        getChildren().setAll(contactHeader, messageScroller, inputContainer);
        setPadding(new Insets(5));
        setSpacing(13);
    }

    private void setupContactHeader()
	{
        contactHeader=new Label(otherPerson);
        contactHeader.setAlignment(Pos.CENTER);
        contactHeader.setFont(Font.font("Comic Sans MS", 14));
    }

    private void setupMessageDisplay()
	{
        messageContainer=new VBox(5);
        messageContainer.setPrefHeight(200);
        Bindings.bindContentBidirectional(messages, messageContainer.getChildren());

        messageScroller=new ScrollPane(messageContainer);
        messageScroller.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
        messageScroller.setHbarPolicy(ScrollBarPolicy.NEVER);
        messageScroller.vvalueProperty().bind(messageContainer.heightProperty());
        messageScroller.setPrefHeight(300);
        messageScroller.setPrefWidth(395);
        messageScroller.setFitToWidth(true);
        messageScroller.setFitToHeight(true);
    }

    private void setupInputDisplay()
	{
        inputContainer=new HBox(5);

        JFXTextField userInput=new JFXTextField();
        userInput.setPrefWidth(350);
        userInput.setPromptText("Enter message");
        userInput.setLabelFloat(true);
        userInput.setOnKeyReleased(e->
        {
            if(e.getCode().equals(KeyCode.ENTER))
            {
                if(!userInput.getText().isEmpty())
                {
                    sendMessage(userInput.getText());
                    userInput.setText("");
                }
            }
            if(userInput.getText().equals(""))
            {
                userInput.setPromptText("Type here the message");
            }
            else
            {
                userInput.setPromptText("Press enter to send");
            }
        });

        ImageView sendImage=new ImageView(new Image(SEND_IMAGE_PATH));
        JFXButton sendMessageButton=new JFXButton("",sendImage);
        sendMessageButton.disableProperty().bind(userInput.lengthProperty().isEqualTo(0));
        sendMessageButton.setOnAction(event->
		{
            if(!userInput.equals(""))
            {
                sendMessage(userInput.getText());
                userInput.setText("");
            }
        });

        inputContainer.getChildren().setAll(userInput, sendMessageButton);
    }

    public void sendMessage(String message)
	{
        messages.add(new Message(message, true));
        Client.sendMessage(message, otherPerson);
    }

    public void receiveMessage(String message)
	{
        messages.add(new Message(message, false));
    }
}

class Message extends HBox
{
    private Color SENT_COLOR=Color.GOLD;
    private Color RECEIVED_COLOR=Color.LIMEGREEN;

    private String message;
    private boolean sent;

    private Label displayedText;
    private SVGPath directionIndicator;

    Message(String message, boolean sent)
	{
        this.message=message;
        this.sent=sent;

		displayedText=new Label(message);
        displayedText.setPadding(new Insets(5));
        displayedText.setWrapText(true);
        directionIndicator=new SVGPath();

		displayedText.setBackground(sent?new Background(new BackgroundFill(SENT_COLOR, new CornerRadii(5,0,5,5,false), Insets.EMPTY)):new Background(new BackgroundFill(RECEIVED_COLOR, new CornerRadii(0,5,5,5,false), Insets.EMPTY)));
        displayedText.setAlignment(sent?Pos.CENTER_RIGHT:Pos.CENTER_LEFT);
        directionIndicator.setContent(sent?"M10 0 L0 10 L0 0 Z":"M0 0 L10 0 L10 10 Z");
        directionIndicator.setFill(sent?SENT_COLOR:RECEIVED_COLOR);

        HBox container=sent?new HBox(displayedText, directionIndicator):new HBox(directionIndicator, displayedText);

        container.maxWidthProperty().bind(widthProperty().multiply(0.60));
        getChildren().setAll(container);
        setAlignment(sent?Pos.CENTER_RIGHT:Pos.CENTER_LEFT);
    }
}
